package com.citygo.controller;

import java.time.LocalDate;
import com.citygo.model.Koltuk;
import com.citygo.model.Sefer;
import com.citygo.repository.KoltukRepository;
import com.citygo.service.AramaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/*
 * =============================================================
 * SeferController.java — Sefer Arama Controller
 * =============================================================
 * Sorumlu: Mert
 *
 * Sefer arama ve sefer detayı endpoint'lerini barındırır.
 * Herkese açık (public) endpoint'ler — giriş gerektirmez.
 *
 * Anotasyonlar:
 * - @RestController
 * - @RequestMapping("/api/seferler")
 * - @CrossOrigin
 *
 * Bağımlılıklar:
 * - AramaService
 * - KoltukRepository
 *
 * Endpoint'ler:
 *
 * GET /api/seferler/ara?kalkis=...&varis=...&tarih=...&tip=...
 *     → Query parametreleri ile sefer arama
 *     → Hangi parametreler gelirse AramaService'in uygun overloaded
 *       metodu çağrılır:
 *       - Sadece kalkis+varis → ara(kalkis, varis)
 *       - kalkis+varis+tarih → ara(kalkis, varis, tarih)
 *       - kalkis+varis+tarih+tip → ara(kalkis, varis, tarih, tip)
 *     → 200 OK + List<Sefer>
 *
 * GET /api/seferler/{id}
 *     → @PathVariable ile sefer ID'si al
 *     → Sefer detayını döndür
 *     → 200 OK + Sefer nesnesi
 *     → Bulunamazsa: 404 Not Found
 *
 * GET /api/seferler/{id}/koltuklar
 *     → Belirtilen sefere ait tüm koltukları döndür
 *     → 200 OK + List<Koltuk> (her koltuğun dolu/boş durumu dahil)
 *     → Frontend koltuk haritası bu endpoint'i kullanacak
 */

@RestController
@RequestMapping("/api/seferler")
@CrossOrigin
public class SeferController {

    private final AramaService aramaService;
    private final KoltukRepository koltukRepository;

    // Constructor injection
    public SeferController(AramaService aramaService, KoltukRepository koltukRepository) {
        this.aramaService = aramaService;
        this.koltukRepository = koltukRepository;
    }

    // Sefer arama — gelen parametreye göre uygun overloaded metot çağrılır
    @GetMapping("/ara")
    public List<Sefer> ara(
    @RequestParam String kalkis,
    @RequestParam String varis,
    // Duzeltme: Frontend sadece tarih gonderiyor, bu yuzden burada LocalDate bekliyoruz.
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tarih,
    @RequestParam(required = false) String tip
    ) {
    // Duzeltme: Bos tip geldiginde tarihli ama tipsiz aramaya dusmesi gerekiyor.
    if (tarih != null && tip != null && !tip.isBlank()) {
        return aramaService.ara(kalkis, varis, tarih, tip);
    } else if (tarih != null) {
        return aramaService.ara(kalkis, varis, tarih);
    } else {
        return aramaService.ara(kalkis, varis);
    }
    }

    // Sefer detayı
    @GetMapping("/{id}")
    public Sefer seferDetay(@PathVariable Long id) {
        return aramaService.seferDetay(id);
    }

    // Sefere ait koltuklar
    @GetMapping("/{id}/koltuklar")
    public List<Koltuk> koltuklar(@PathVariable Long id) {
        // Duzeltme: Gecersiz sefer id'sinde bos liste yerine kontrollu 404 donsun.
        aramaService.seferDetay(id);
        return koltukRepository.findBySefer_Id(id);
    }
}
