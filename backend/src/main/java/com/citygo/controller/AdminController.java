package com.citygo.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

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

    private static final String KEY_ARAC_ID = "aracId";
    private static final String KEY_KALKIS_NOKTASI = "kalkisNoktasi";
    private static final String KEY_VARIS_NOKTASI = "varisNoktasi";
    private static final String KEY_KALKIS_ZAMANI = "kalkisZamani";
    private static final String KEY_VARIS_ZAMANI = "varisZamani";

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
    public List<Sefer> tumSeferleriGetir() {
        return seferRepository.findAll();
    }

    // Yeni sefer oluştur + koltukları otomatik oluştur
    @PostMapping("/seferler")
    public ResponseEntity<Object> seferEkle(@RequestBody Map<String, String> body) {
        try {
            Long aracId = Long.parseLong(body.get(KEY_ARAC_ID));
            String kalkisNoktasi = body.get(KEY_KALKIS_NOKTASI);
            String varisNoktasi = body.get(KEY_VARIS_NOKTASI);
            String kalkisZamaniStr = body.get(KEY_KALKIS_ZAMANI);
            String varisZamaniStr = body.get(KEY_VARIS_ZAMANI);

            Optional<UlasimAraci> aracOpt = ulasimAraciRepository.findById(aracId);
            if (aracOpt.isPresent()) {
                UlasimAraci arac = aracOpt.get();
                Sefer sefer = new Sefer();
                sefer.setArac(arac);
                sefer.setKalkisNoktasi(kalkisNoktasi);
                sefer.setVarisNoktasi(varisNoktasi);
                sefer.setKalkisZamani(LocalDateTime.parse(kalkisZamaniStr));
                sefer.setVarisZamani(LocalDateTime.parse(varisZamaniStr));

                // Koltukları araç kapasitesine göre üret
                sefer.koltuklariOlustur();

                Sefer kaydedilenSefer = seferRepository.save(sefer);
                return ResponseEntity.status(HttpStatus.CREATED).body(kaydedilenSefer);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("hata", "Ulaşım aracı bulunamadı"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("hata", "Sefer eklenirken hata: " + e.getMessage()));
        }
    }

    // Mevcut seferi güncelle
    @PutMapping("/seferler/{id}")
    public ResponseEntity<Object> seferGuncelle(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            Optional<Sefer> seferOpt = seferRepository.findById(id);
            if (seferOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("hata", "Sefer bulunamadı"));
            }
            Sefer sefer = seferOpt.get();

            if (body.containsKey(KEY_ARAC_ID)) {
                Long aracId = Long.parseLong(body.get(KEY_ARAC_ID));
                Optional<UlasimAraci> aracOpt = ulasimAraciRepository.findById(aracId);
                aracOpt.ifPresent(sefer::setArac);
            }

            if (body.containsKey(KEY_KALKIS_NOKTASI))
                sefer.setKalkisNoktasi(body.get(KEY_KALKIS_NOKTASI));
            if (body.containsKey(KEY_VARIS_NOKTASI))
                sefer.setVarisNoktasi(body.get(KEY_VARIS_NOKTASI));
            if (body.containsKey(KEY_KALKIS_ZAMANI))
                sefer.setKalkisZamani(LocalDateTime.parse(body.get(KEY_KALKIS_ZAMANI)));
            if (body.containsKey(KEY_VARIS_ZAMANI))
                sefer.setVarisZamani(LocalDateTime.parse(body.get(KEY_VARIS_ZAMANI)));

            Sefer guncellenen = seferRepository.save(sefer);
            return ResponseEntity.ok(guncellenen);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("hata", "Güncelleme hatası: " + e.getMessage()));
        }
    }

    // Seferi sil
    @DeleteMapping("/seferler/{id}")
    public ResponseEntity<Object> seferSil(@PathVariable Long id) {
        try {
            if (!seferRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("hata", "Sefer bulunamadı"));
            }
            seferRepository.deleteById(id);
            return ResponseEntity.ok(Map.of("mesaj", "Sefer başarıyla silindi"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("hata", "Silme hatası"));
        }
    }

    // Tüm kullanıcıları listele
    @GetMapping("/kullanicilar")
    public List<Kullanici> kullanicilariListele() {
        return kullaniciService.tumKullanicilariGetir();
    }

    // Tüm biletleri listele
    @GetMapping("/biletler")
    public List<Bilet> biletleriListele() {
        return biletRepository.findAll();
    }

    // Dashboard istatistikleri
    @GetMapping("/istatistikler")
    public ResponseEntity<Object> istatistikleriGetir() {
        try {
            Map<String, Object> stats = new HashMap<>();
            stats.put("toplamSefer", seferRepository.count());
            stats.put("toplamBilet", biletRepository.count());
            stats.put("aktifBilet", biletRepository.countByDurum(BiletDurumu.AKTIF));
            stats.put("iptalBilet", biletRepository.countByDurum(BiletDurumu.IPTAL_EDILDI));
            stats.put("toplamKullanici", kullaniciRepository.count());

            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("hata", "İstatistikler alınamadı"));
        }
    }
}
