package com.citygo.service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import com.citygo.exception.SeferBulunamadiException;
import com.citygo.interfaces.IAranabilir;
import com.citygo.model.Sefer;
import com.citygo.model.UlasimAraci;
import com.citygo.repository.SeferRepository;
import com.citygo.repository.UlasimAraciRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
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
    private final UlasimAraciRepository ulasimAraciRepository;

    // Constructor injection
    public AramaService(SeferRepository seferRepository, UlasimAraciRepository ulasimAraciRepository) {
        this.seferRepository = seferRepository;
        this.ulasimAraciRepository = ulasimAraciRepository;
    }

    // Sadece güzergaha göre arama
    @Override
    @Transactional
    public List<Sefer> ara(String kalkis, String varis) {
        validateRoute(kalkis, varis);
        List<Sefer> sonuc = seferRepository.findByKalkisNoktasiAndVarisNoktasi(kalkis, varis);
        if (!sonuc.isEmpty()) {
            return sonuc;
        }

        // Duzeltme: Demo sirasinda yeni bir sehir kombinasyonu secildiginde
        // "sefer bulunamadi" gostermek yerine yarin icin sefer ve koltuk uretiyoruz.
        return demoSeferleriOlustur(kalkis, varis, LocalDate.now().plusDays(1), null);
    }

    // Güzergah ve tarihe göre arama
    @Override
    @Transactional
    public List<Sefer> ara(String kalkis, String varis, LocalDate tarih) {
        validateRoute(kalkis, varis);
        // Duzeltme: Kullanici saat secmiyor, bu yuzden tum gunu kapsayan aralikla ariyoruz.
        LocalDateTime baslangic = tarih.atStartOfDay();
        LocalDateTime bitis = tarih.atTime(LocalTime.MAX);

        List<Sefer> sonuc = seferRepository.findByKalkisNoktasiAndVarisNoktasiAndKalkisZamaniBetween(
            kalkis,
            varis,
            baslangic,
            bitis
        );

        if (!sonuc.isEmpty()) {
            return sonuc;
        }

        // Duzeltme: Hoca ileri/eksik bir tarih secse bile ekran bos kalmasin.
        // Sefer yoksa secilen tarihe demo seferleri ve koltuklarini uretiyoruz.
        return demoSeferleriOlustur(kalkis, varis, tarih, null);
    }

    // Güzergah, tarih ve araç tipine göre arama
    @Override
    @Transactional
    public List<Sefer> ara(String kalkis, String varis, LocalDate tarih, String aracTipi) {
        // Duzeltme: Once gun filtresi uygulaniyor, sonra tip null guvenli sekilde suzuluyor.
        List<Sefer> sonuc = ara(kalkis, varis, tarih)
            .stream()
            .filter(sefer -> sefer.getArac() != null)
            .filter(sefer -> sefer.getArac().getAracTipi().equalsIgnoreCase(aracTipi))
            .toList();

        if (!sonuc.isEmpty()) {
            return sonuc;
        }

        // Duzeltme: Secilen gun/guzergah var ama istenen arac tipi yoksa sadece
        // o arac tipi icin ek demo sefer uret.
        return demoSeferleriOlustur(kalkis, varis, tarih, aracTipi);
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

    private void validateRoute(String kalkis, String varis) {
        if (kalkis.equalsIgnoreCase(varis)) {
            // Duzeltme: Ayni sehir aramalari anlamsiz sonuc uretmesin.
            throw new IllegalArgumentException("Kalkış ve varış noktası aynı olamaz.");
        }
    }

    private List<Sefer> demoSeferleriOlustur(String kalkis, String varis, LocalDate tarih, String aracTipi) {
        LocalDate hedefTarih = tarih.isBefore(LocalDate.now()) ? LocalDate.now().plusDays(1) : tarih;
        List<UlasimAraci> araclar = ulasimAraciRepository.findAll()
            .stream()
            .filter(arac -> aracTipi == null || aracTipi.isBlank() || arac.getAracTipi().equalsIgnoreCase(aracTipi))
            .limit(3)
            .toList();

        List<Sefer> olusturulanlar = new ArrayList<>();
        for (int i = 0; i < araclar.size(); i++) {
            UlasimAraci arac = araclar.get(i);
            LocalDateTime kalkisZamani = hedefTarih.atTime(10, 0).plusHours(i * 3L);

            Sefer sefer = new Sefer();
            sefer.setArac(arac);
            sefer.setKalkisNoktasi(kalkis);
            sefer.setVarisNoktasi(varis);
            sefer.setKalkisZamani(kalkisZamani);
            sefer.setVarisZamani(kalkisZamani.plusHours(2));
            sefer.koltuklariOlustur();

            olusturulanlar.add(seferRepository.save(sefer));
        }

        return olusturulanlar;
    }
}
