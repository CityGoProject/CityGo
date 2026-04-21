package com.citygo.controller;

import com.citygo.model.Koltuk;
import com.citygo.model.Sefer;
import com.citygo.repository.KoltukRepository;
import com.citygo.service.AramaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/seferler")
public class SeferController {

    private final AramaService aramaService;
    private final KoltukRepository koltukRepository;

    public SeferController(AramaService aramaService, KoltukRepository koltukRepository) {
        this.aramaService = aramaService;
        this.koltukRepository = koltukRepository;
    }

    @GetMapping("/ara")
    public List<Sefer> seferAra(
            @RequestParam String kalkis,
            @RequestParam String varis,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tarih,
            @RequestParam(required = false) String tip) {

        /*
         * Frontend tarih ve araç tipini opsiyonel gönderiyor.
         * Hangi filtre geldiyse AramaService'in uygun overloaded ara() metodu çağrılır.
         */
        if (tarih != null && tip != null && !tip.isBlank()) {
            return aramaService.ara(kalkis, varis, tarih, tip);
        }

        if (tarih != null) {
            return aramaService.ara(kalkis, varis, tarih);
        }

        return aramaService.ara(kalkis, varis);
    }

    @GetMapping("/{id}")
    public Sefer seferDetay(@PathVariable Long id) {
        return aramaService.seferDetay(id);
    }

    @GetMapping("/{id}/koltuklar")
    public List<Koltuk> seferKoltuklari(@PathVariable Long id) {
        aramaService.seferDetay(id);
        return koltukRepository.findBySefer_Id(id);
    }
}

/*
 * =============================================================
 * SeferController.java — Sefer Arama Controller
 * =============================================================
 * Sorumlu: Mert
 *
 * Bu dosya daha önce sadece yorum içeriyordu. Bu yüzden frontend'den
 * /api/seferler/ara isteği gelince backend 404 dönüyordu.
 * Artık arama, detay ve koltuk endpoint'leri gerçek olarak tanımlandı.
 */
