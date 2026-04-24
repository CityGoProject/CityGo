package com.citygo.service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import com.citygo.exception.SeferBulunamadiException;
import com.citygo.interfaces.IAranabilir;
import com.citygo.model.Sefer;
import com.citygo.repository.SeferRepository;
import org.springframework.stereotype.Service;
import java.util.List;

        /*
        * =============================================================
        * AramaService.java — Sefer Arama İş Mantığı (POLYMORPHISM - Overloading)
        * =============================================================
        * Sorumlu: Mert
        *
        * IAranabilir interface'ini implement eden sefer arama servisi.
        * 3 farklı overloaded ara() metodu ile POLYMORPHISM gösterilecek.
        *
        * Anotasyonlar:
        * - @Service
        *
        * Implements:
        * - IAranabilir
        *
        * Bağımlılıklar:
        * - SeferRepository
        *
        * Metotlar (hepsi IAranabilir'den geliyor):
        *
        * - ara(String kalkis, String varis): List<Sefer>
        *     → SeferRepository.findByKalkisNoktasiAndVarisNoktasi() çağır
        *
        * - ara(String kalkis, String varis, LocalDate tarih): List<Sefer>
        *     → Tarih filtresini de ekleyerek arama yap
        *     → Tarih: o günün 00:00 - 23:59 arasındaki seferleri getir
        *
        * - ara(String kalkis, String varis, LocalDate tarih, String aracTipi): List<Sefer>
        *     → Tarih + araç tipi filtreleri ile arama yap
        *     → Sonuçları araç tipine göre filtrele (stream().filter() kullanılabilir)
        *
        * Ek Metotlar:
        * - seferDetay(Long seferId): Sefer
        *     → Tek bir seferin detaylarını getir
        *
        * - tumSeferleriGetir(): List<Sefer>
        *     → Admin paneli için tüm seferleri listele
        *
        * ÖNEMLI: Bu 3 overloaded metot projedeki Polymorphism (Overloading)
        *         örneğinin temelini oluşturuyor!
        */

@Service
public class AramaService implements IAranabilir {

    private final SeferRepository seferRepository;

    // Constructor injection
    public AramaService(SeferRepository seferRepository) {
        this.seferRepository = seferRepository;
    }

    // Sadece güzergaha göre arama
    @Override
    public List<Sefer> ara(String kalkis, String varis) {
        return seferRepository.findByKalkisNoktasiAndVarisNoktasi(kalkis, varis);
    }

    // Güzergah ve tarihe göre arama
    @Override
    public List<Sefer> ara(String kalkis, String varis, LocalDate tarih) {
        // Duzeltme: Kullanici saat secmiyor, bu yuzden tum gunu kapsayan aralikla ariyoruz.
        LocalDateTime baslangic = tarih.atStartOfDay();
        LocalDateTime bitis = tarih.atTime(LocalTime.MAX);

        return seferRepository.findByKalkisNoktasiAndVarisNoktasiAndKalkisZamaniBetween(
            kalkis,
            varis,
            baslangic,
            bitis
        );
    }

    // Güzergah, tarih ve araç tipine göre arama
    @Override
    public List<Sefer> ara(String kalkis, String varis, LocalDate tarih, String aracTipi) {
        // Duzeltme: Once gun filtresi uygulaniyor, sonra tip null guvenli sekilde suzuluyor.
        return ara(kalkis, varis, tarih)
            .stream()
            .filter(sefer -> sefer.getArac() != null)
            .filter(sefer -> sefer.getArac().getAracTipi().equalsIgnoreCase(aracTipi))
            .toList();
    }

    // Tek bir seferin detaylarını getirir
    public Sefer seferDetay(Long seferId) {
        // Duzeltme: Bu endpoint bulunamayan seferde 500 degil 404 donmeli.
        return seferRepository.findById(seferId)
            .orElseThrow(() -> new SeferBulunamadiException("Sefer bulunamadi: " + seferId));
    }

    // Tüm seferleri listeler
    public List<Sefer> tumSeferleriGetir() {
        return seferRepository.findAll();
    }
}
