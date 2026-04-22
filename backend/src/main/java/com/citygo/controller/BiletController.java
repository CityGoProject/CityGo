package com.citygo.controller;

import com.citygo.model.Bilet;
import com.citygo.service.RezervasyonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/biletler") // bu sınıftaki tüm metodların bu adresle başlayacağını tanımlar
@CrossOrigin // faklı portlarda çalışan uygulamaların bu apiye erişmesine izin verir

public class BiletController {

    private final RezervasyonService rezervasyonService;

    @Autowired
    public BiletController(RezervasyonService rezervasyonService) {
        this.rezervasyonService = rezervasyonService;
    }


    @PostMapping // POST /api/biletler - Bilet satın alma
    public ResponseEntity<Bilet> biletAl(@RequestBody Map<String, Object> request) {

        Long yolcuId = ((Number) request.get("yolcuId")).longValue();
        Long seferId = ((Number) request.get("seferId")).longValue();
        Integer koltukNo = (Integer) request.get("koltukNo");

        Bilet yeniBilet = rezervasyonService.rezervasyonYap(yolcuId, seferId, koltukNo);

        return new ResponseEntity<>(yeniBilet, HttpStatus.CREATED);
    }


    @GetMapping("/benim") // GET /api/biletler/benim?yolcuId=...  - Yolcunun biletlerini listeleme
    public ResponseEntity<List<Bilet>> biletlerimiGetir(@RequestParam Long yolcuId) {

        List<Bilet> biletler = rezervasyonService.rezervasyonlariGetir(yolcuId);

        return ResponseEntity.ok(biletler);
    }


    @PutMapping("/{id}/iptal") // PUT /api/biletler/{id}/iptal  -Bilet iptal etme
    public ResponseEntity<Map<String, String>> biletIptal(@PathVariable Long id) {

        rezervasyonService.rezervasyonIptal(id);

        return ResponseEntity.ok(Map.of("mesaj", "Bilet iptal edildi"));
    }

}

/*
 * =============================================================
 * BiletController.java — Bilet İşlemleri Controller
 * =============================================================
 * Sorumlu: Elif Feyza
 *
 * Bilet satın alma, listeleme ve iptal endpoint'lerini barındırır.
 *
 * Anotasyonlar:
 * - @RestController
 * - @RequestMapping("/api/biletler")
 * - @CrossOrigin
 *
 * Bağımlılıklar:
 * - RezervasyonService
 *
 * Endpoint'ler:
 *
 * POST /api/biletler
 *     → Request Body: { yolcuId, seferId, koltukNo }
 *     → RezervasyonService.rezervasyonYap() çağır
 *     → Başarılı: 201 Created + Bilet nesnesi
 *     → Hata: 400 (koltuk dolu), 404 (sefer/yolcu bulunamadı)
 *
 * GET /api/biletler/benim?yolcuId=...
 *     → Query parametresi ile yolcunun biletlerini getir
 *     → RezervasyonService.rezervasyonlariGetir() çağır
 *     → 200 OK + List<Bilet>
 *
 * PUT /api/biletler/{id}/iptal
 *     → @PathVariable ile bilet ID'si al
 *     → RezervasyonService.rezervasyonIptal() çağır
 *     → Başarılı: 200 OK + { mesaj: "Bilet iptal edildi" }
 *     → Hata: 404 (bilet bulunamadı)
 */
