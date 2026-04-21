package com.citygo.service;

import com.citygo.exception.SeferBulunamadiException;
import com.citygo.interfaces.IAranabilir;
import com.citygo.model.Sefer;
import com.citygo.repository.SeferRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AramaService implements IAranabilir {

    private final SeferRepository seferRepository;

    public AramaService(SeferRepository seferRepository) {
        this.seferRepository = seferRepository;
    }

    @Override
    public List<Sefer> ara(String kalkis, String varis) {
        return seferRepository.findByKalkisNoktasiAndVarisNoktasi(kalkis, varis);
    }

    @Override
    public List<Sefer> ara(String kalkis, String varis, LocalDate tarih) {
        /*
         * Kullanıcı sadece tarih seçiyor, Sefer entity'sinde ise LocalDateTime var.
         * Bu yüzden seçilen günün 00:00-23:59 aralığını sorguluyoruz.
         */
        LocalDateTime baslangic = tarih.atStartOfDay();
        LocalDateTime bitis = tarih.atTime(LocalTime.MAX);

        return seferRepository.findByKalkisNoktasiAndVarisNoktasiAndKalkisZamaniBetween(
                kalkis,
                varis,
                baslangic,
                bitis);
    }

    @Override
    public List<Sefer> ara(String kalkis, String varis, LocalDate tarih, String aracTipi) {
        return ara(kalkis, varis, tarih)
                .stream()
                .filter(sefer -> sefer.getArac() != null)
                .filter(sefer -> sefer.getArac().getAracTipi().equalsIgnoreCase(aracTipi))
                .toList();
    }

    public Sefer seferDetay(Long seferId) {
        return seferRepository.findById(seferId)
                .orElseThrow(() -> new SeferBulunamadiException("Sefer bulunamadı, ID: " + seferId));
    }

    public List<Sefer> tumSeferleriGetir() {
        return seferRepository.findAll();
    }
}

/*
 * =============================================================
 * AramaService.java — Sefer Arama İş Mantığı (POLYMORPHISM - Overloading)
 * =============================================================
 * Sorumlu: Mert
 *
 * Bu dosya daha önce sadece açıklama içeriyordu. Frontend'deki Sefer Ara
 * butonu /api/seferler/ara endpoint'ine gittiği için artık gerçek servis
 * kodu eklendi.
 *
 * OOP notu:
 * Aynı isimde 3 farklı ara() metodu method overloading örneğidir.
 */
