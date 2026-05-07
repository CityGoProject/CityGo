package com.citygo.controller;

import com.citygo.dto.TripRequest;
import com.citygo.model.Sefer;
import com.citygo.model.Bilet;
import com.citygo.model.Kullanici;
import com.citygo.model.UlasimAraci;
import com.citygo.model.BiletDurumu;
import com.citygo.repository.BiletRepository;
import com.citygo.repository.SeferRepository;
import com.citygo.repository.UlasimAraciRepository;
import com.citygo.repository.KullaniciRepository;
import com.citygo.service.KullaniciService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/*
 * =============================================================
 * AdminController.java — Admin Paneli Controller
 * =============================================================
 * Sorumlu: Muhammed + Ömer Faruk
 *
 * Yalnızca admin kullanıcılarının erişebileceği yönetim endpoint'leri.
 *
 * Anotasyonlar:
 * - @RestController
 * - @RequestMapping("/api/admin")
 * - @CrossOrigin
 *
 * Bağımlılıklar:
 * - AramaService (sefer yönetimi için)
 * - KullaniciService (kullanıcı listesi için)
 * - BiletRepository (bilet listesi için)
 * - SeferRepository (sefer CRUD için)
 *
 * Endpoint'ler:
 *
 * GET /api/admin/seferler
 *     → Tüm seferleri listele
 *     → 200 OK + List<Sefer>
 *
 * POST /api/admin/seferler
 *     → Request Body: { aracId, kalkisNoktasi, varisNoktasi, kalkisZamani, varisZamani }
 *     → Yeni sefer oluştur + koltukları otomatik oluştur
 *     → 201 Created + Sefer nesnesi
 *
 * PUT /api/admin/seferler/{id}
 *     → Mevcut seferi güncelle
 *     → 200 OK + güncellenmiş Sefer
 *
 * DELETE /api/admin/seferler/{id}
 *     → Seferi sil (ilişkili koltuklar da silinmeli — cascade)
 *     → 204 No Content
 *
 * GET /api/admin/kullanicilar
 *     → Tüm kullanıcıları listele
 *     → 200 OK + List<Kullanici>
 *
 * GET /api/admin/biletler
 *     → Tüm biletleri listele
 *     → 200 OK + List<Bilet>
 *
 * GET /api/admin/istatistikler
 *     → Dashboard istatistikleri döndür
 *     → { toplamSefer, toplamBilet, aktifBilet, iptalBilet, toplamKullanici }
 *     → 200 OK + JSON
 *
 * Not: Admin kontrolü için basit bir yaklaşım:
 *      Request header'dan veya query'den kullanıcı ID'si alıp
 *      rolünün ADMIN olduğunu kontrol edin.
 */

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    private final KullaniciService kullaniciService;
    private final BiletRepository biletRepository;
    private final SeferRepository seferRepository;
    private final UlasimAraciRepository ulasimAraciRepository;
    private final KullaniciRepository kullaniciRepository;

    public AdminController(KullaniciService kullaniciService,
                           BiletRepository biletRepository,
                           SeferRepository seferRepository,
                           UlasimAraciRepository ulasimAraciRepository,
                           KullaniciRepository kullaniciRepository) {
        this.kullaniciService = kullaniciService;
        this.biletRepository = biletRepository;
        this.seferRepository = seferRepository;
        this.ulasimAraciRepository = ulasimAraciRepository;
        this.kullaniciRepository = kullaniciRepository;
    }

    // Tüm seferleri listele
    @GetMapping("/seferler")
    public List<Sefer> tumSeferleriGetir(@RequestHeader("X-User-Id") Long adminId) {
        requireAdmin(adminId);
        return seferRepository.findAll();
    }

    @GetMapping("/araclar")
    public List<UlasimAraci> araclariListele(@RequestHeader("X-User-Id") Long adminId) {
        // Duzeltme: Frontend artik araci ID ezberletmek yerine listeden sectirebilir.
        requireAdmin(adminId);
        return ulasimAraciRepository.findAll();
    }

    // Yeni sefer oluştur + koltukları otomatik oluştur
    @PostMapping("/seferler")
    public ResponseEntity<Sefer> seferEkle(@RequestHeader("X-User-Id") Long adminId,
                                           @Valid @RequestBody TripRequest request) {
        requireAdmin(adminId);
        validateTripRequest(request);

        UlasimAraci arac = ulasimAraciRepository.findById(request.aracId())
                .orElseThrow(() -> new IllegalArgumentException("Ulaşım aracı bulunamadı"));

        Sefer sefer = new Sefer();
        applyTripRequest(sefer, request, arac);
        sefer.koltuklariOlustur();

        Sefer kaydedilenSefer = seferRepository.save(sefer);
        return ResponseEntity.status(HttpStatus.CREATED).body(kaydedilenSefer);
    }

    // Mevcut seferi güncelle
    @PutMapping("/seferler/{id}")
    public ResponseEntity<Sefer> seferGuncelle(@RequestHeader("X-User-Id") Long adminId,
                                               @PathVariable Long id,
                                               @Valid @RequestBody TripRequest request) {
        requireAdmin(adminId);
        validateTripRequest(request);

        Sefer sefer = seferRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sefer bulunamadı"));
        UlasimAraci yeniArac = ulasimAraciRepository.findById(request.aracId())
                .orElseThrow(() -> new IllegalArgumentException("Ulaşım aracı bulunamadı"));

        boolean aracDegisiyor = sefer.getArac() == null || !sefer.getArac().getId().equals(yeniArac.getId());
        if (aracDegisiyor && !biletRepository.findBySefer_Id(id).isEmpty()) {
            // Duzeltme: Biletli seferin araci degisirse koltuk/bilet iliskileri bozulur.
            throw new IllegalArgumentException("Bileti olan seferin aracı değiştirilemez.");
        }

        applyTripRequest(sefer, request, yeniArac);
        if (aracDegisiyor) {
            sefer.koltuklariOlustur();
        }

        return ResponseEntity.ok(seferRepository.save(sefer));
    }

    // Seferi sil
    @DeleteMapping("/seferler/{id}")
    public ResponseEntity<Object> seferSil(@RequestHeader("X-User-Id") Long adminId, @PathVariable Long id) {
        requireAdmin(adminId);
        if (!seferRepository.existsById(id)) {
            throw new IllegalArgumentException("Sefer bulunamadı");
        }

        boolean aktifBiletVar = biletRepository.findBySefer_Id(id).stream()
                .anyMatch(bilet -> bilet.getDurum() == BiletDurumu.AKTIF);
        if (aktifBiletVar) {
            // Duzeltme: Aktif bileti olan sefer silinirse kullanici bileti anlamsiz kalir.
            throw new IllegalArgumentException("Aktif bileti olan sefer silinemez.");
        }

        seferRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("mesaj", "Sefer başarıyla silindi"));
    }

    // Tüm kullanıcıları listele
    @GetMapping("/kullanicilar")
    public List<Kullanici> kullanicilariListele(@RequestHeader("X-User-Id") Long adminId) {
        requireAdmin(adminId);
        return kullaniciService.tumKullanicilariGetir();
    }

    // Tüm biletleri listele
    @GetMapping("/biletler")
    public List<Bilet> biletleriListele(@RequestHeader("X-User-Id") Long adminId) {
        requireAdmin(adminId);
        return biletRepository.findAll();
    }

    // Dashboard istatistikleri
    @GetMapping("/istatistikler")
    public ResponseEntity<Object> istatistikleriGetir(@RequestHeader("X-User-Id") Long adminId) {
        requireAdmin(adminId);
        Map<String, Object> stats = new HashMap<>();
        stats.put("toplamSefer", seferRepository.count());
        stats.put("toplamBilet", biletRepository.count());
        stats.put("aktifBilet", biletRepository.countByDurum(BiletDurumu.AKTIF));
        stats.put("iptalBilet", biletRepository.countByDurum(BiletDurumu.IPTAL_EDILDI));
        stats.put("toplamKullanici", kullaniciRepository.count());

        return ResponseEntity.ok(stats);
    }

    private void requireAdmin(Long adminId) {
        kullaniciService.adminKullaniciBul(adminId);
    }

    private void validateTripRequest(TripRequest request) {
        if (request.kalkisNoktasi().equalsIgnoreCase(request.varisNoktasi())) {
            throw new IllegalArgumentException("Kalkış ve varış noktası aynı olamaz.");
        }

        if (!request.varisZamani().isAfter(request.kalkisZamani())) {
            throw new IllegalArgumentException("Varış zamanı kalkış zamanından sonra olmalıdır.");
        }
    }

    private void applyTripRequest(Sefer sefer, TripRequest request, UlasimAraci arac) {
        sefer.setArac(arac);
        sefer.setKalkisNoktasi(request.kalkisNoktasi());
        sefer.setVarisNoktasi(request.varisNoktasi());
        sefer.setKalkisZamani(request.kalkisZamani());
        sefer.setVarisZamani(request.varisZamani());
    }
}
