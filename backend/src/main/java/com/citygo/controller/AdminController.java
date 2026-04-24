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
import com.citygo.service.AramaService;
import com.citygo.service.KullaniciService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AramaService aramaService;

    @Autowired
    private KullaniciService kullaniciService;

    @Autowired
    private BiletRepository biletRepository;

    @Autowired
    private SeferRepository seferRepository;

    @Autowired
    private UlasimAraciRepository ulasimAraciRepository;

    @Autowired
    private KullaniciRepository kullaniciRepository;

    // Tüm seferleri listele
    @GetMapping("/seferler")
    public List<Sefer> tumSeferleriGetir() {
        return seferRepository.findAll();
    }

    // Yeni sefer oluştur + koltukları otomatik oluştur
    @PostMapping("/seferler")
    public ResponseEntity<?> seferEkle(@RequestBody Map<String, String> body) {
        try {
            Long aracId = Long.parseLong(body.get("aracId"));
            String kalkisNoktasi = body.get("kalkisNoktasi");
            String varisNoktasi = body.get("varisNoktasi");
            String kalkisZamaniStr = body.get("kalkisZamani");
            String varisZamaniStr = body.get("varisZamani");

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
    public ResponseEntity<?> seferGuncelle(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            Optional<Sefer> seferOpt = seferRepository.findById(id);
            if (seferOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("hata", "Sefer bulunamadı"));
            }
            Sefer sefer = seferOpt.get();

            if (body.containsKey("aracId")) {
                Long aracId = Long.parseLong(body.get("aracId"));
                Optional<UlasimAraci> aracOpt = ulasimAraciRepository.findById(aracId);
                aracOpt.ifPresent(sefer::setArac);
            }

            if (body.containsKey("kalkisNoktasi"))
                sefer.setKalkisNoktasi(body.get("kalkisNoktasi"));
            if (body.containsKey("varisNoktasi"))
                sefer.setVarisNoktasi(body.get("varisNoktasi"));
            if (body.containsKey("kalkisZamani"))
                sefer.setKalkisZamani(LocalDateTime.parse(body.get("kalkisZamani")));
            if (body.containsKey("varisZamani"))
                sefer.setVarisZamani(LocalDateTime.parse(body.get("varisZamani")));

            Sefer guncellenen = seferRepository.save(sefer);
            return ResponseEntity.ok(guncellenen);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("hata", "Güncelleme hatası: " + e.getMessage()));
        }
    }

    // Seferi sil
    @DeleteMapping("/seferler/{id}")
    public ResponseEntity<?> seferSil(@PathVariable Long id) {
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
    public ResponseEntity<?> istatistikleriGetir() {
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
