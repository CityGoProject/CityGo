package com.citygo.config;

import com.citygo.model.*;
import com.citygo.repository.UlasimAraciRepository;
import com.citygo.repository.SeferRepository;
import com.citygo.repository.KoltukRepository;
import com.citygo.repository.KullaniciRepository;
import com.citygo.service.KullaniciService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

/*
 * =============================================================
 * DataSeeder.java — Başlangıç Verileri (Seed Data)
 * =============================================================
 * Sorumlu: Mert
 *
 * Uygulama ilk çalıştığında veritabanına örnek veriler yükleyen sınıf.
 * Böylece hoca projeyi açtığında boş sayfa görmez, hazır verilerle test eder.
 *
 * Anotasyonlar:
 * - @Component
 *
 * Implements:
 * - CommandLineRunner → run() metodu uygulama başladığında otomatik çalışır
 *
 * Bağımlılıklar:
 * - UlasimAraciRepository
 * - SeferRepository
 * - KoltukRepository
 * - KullaniciRepository
 *
 * run() Metodu İçinde Yapılacaklar:
 *
 * 1. ADMIN KULLANICI OLUŞTUR:
 *    - Email: admin@citygo.com, Şifre: admin123
 *
 * 2. ÖRNEK YOLCU OLUŞTUR:
 *    - Email: yolcu@citygo.com, Şifre: yolcu123
 *
 * 3. ULAŞIM ARAÇLARI OLUŞTUR:
 *    - 1-2 Uçak (THY, Pegasus)
 *    - 1-2 Tren (TCDD YHT, TCDD Normal)
 *    - 1-2 Otobüs (Metro Turizm, Pamukkale)
 *
 * 4. SEFERLER OLUŞTUR (her araç için en az 2-3 sefer):
 *    - İstanbul → Ankara (farklı tarihler)
 *    - Ankara → İzmir
 *    - İstanbul → Antalya
 *    - vb.
 *
 * 5. KOLTUKLAR OLUŞTUR:
 *    - Her sefer için aracın kapasitesi kadar koltuk
 *    - Bazıları STANDART, bazıları PREMIUM
 *
 * Not: Veritabanında zaten veri varsa tekrar eklememek için
 *      if (repository.count() == 0) kontrolü yapın!
 *
 * Örnek tarihler bugünden itibaren 1-2 hafta sonrasına ayarlanmalı
 * ki seferler hep "gelecekte" görünsün.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final UlasimAraciRepository araciRepository;
    private final SeferRepository seferRepository;
    private final KoltukRepository koltukRepository;
    private final KullaniciRepository kullaniciRepository;
    private final KullaniciService kullaniciService;


    public DataSeeder(UlasimAraciRepository araciRepository, SeferRepository seferRepository,
            KoltukRepository koltukRepository, KullaniciRepository kullaniciRepository,
            KullaniciService kullaniciService) {
        this.araciRepository = araciRepository;
        this.seferRepository = seferRepository;
        this.koltukRepository = koltukRepository;
        this.kullaniciRepository = kullaniciRepository;
        this.kullaniciService = kullaniciService;
    }


    @Override
    public void run(String... args) throws Exception {
        if (kullaniciRepository.count() > 0) {
            log.info("Veritabanı zaten dolu, seed etmiyoruz!");
            return;
        }
        log.info("Seed Başlıyor!");

        seedAdminKullanici();
        seedYolcuKullanici();
        seedUlasimAraclari();
        seedSeferler();
        seedKoltuklar();

        log.info("Seed Başarı ile Tamamlandı!");
    }

    private void seedAdminKullanici() {
        Admin admin = new Admin();
        admin.setAd("Admin");
        admin.setSoyad("CityGo");
        admin.setEmail("admin@citygo.com");
        admin.setSifre(kullaniciService.sifreHashle("admin123"));
        admin.setYetki("SUPER_ADMIN");

        admin.setTelefon("05000000000");
        kullaniciRepository.save(admin);
        log.info("ADMIN OLUŞTURULDU: {}", admin.getEmail());
    }

    private void seedYolcuKullanici() {
        Yolcu yolcu = new Yolcu();
        yolcu.setAd("Yolcu");
        yolcu.setSoyad("Yolcu");
        yolcu.setEmail("yolcu@citygo.com");
        yolcu.setSifre(kullaniciService.sifreHashle("yolcu123"));
        yolcu.setTelefon("05000000001");

        kullaniciRepository.save(yolcu);
        log.info("YOLCU OLUŞTURULDU: {}", yolcu.getEmail());
    }

    private void seedUlasimAraclari() {
        log.info("Ulaşım araçları oluşturuluyor...");

        Ucak ucak = new Ucak();
        ucak.setFirma("THY");
        ucak.setModel("Boeing 737");
        ucak.setKapasite(180);
        ucak.setBiletFiyati(1500.0);
        ucak.setHavaalani("IST");
        ucak.setHavaalaniVergiOrani(0.15);
        araciRepository.save(ucak);

        Tren tren = new Tren();
        tren.setFirma("TCDD");
        tren.setModel("YHT");
        tren.setKapasite(300);
        tren.setBiletFiyati(450.0);
        tren.setHatTipi("YHT");
        tren.setVagonTipi("BUSINESS");
        araciRepository.save(tren);

        Otobus otobus = new Otobus();
        otobus.setFirma("Metro Turizm");
        otobus.setModel("Mercedes Travego");
        otobus.setKapasite(40);
        otobus.setBiletFiyati(600.0);
        otobus.setIkramVar(true);
        otobus.setIkramBedeli(50.0);
        araciRepository.save(otobus);

        log.info("Ulaşım araçları kaydedildi.");
    }

    private void seedSeferler() {
        log.info("Seferler oluşturuluyor...");
        List<UlasimAraci> araclar = araciRepository.findAll();

        for (UlasimAraci arac : araclar) {
            // İstanbul -> Ankara
            Sefer s1 = new Sefer();
            s1.setArac(arac);
            s1.setKalkisNoktasi("İstanbul");
            s1.setVarisNoktasi("Ankara");
            s1.setKalkisZamani(LocalDateTime.now().plusDays(2).withHour(10).withMinute(0));
            s1.setVarisZamani(s1.getKalkisZamani().plusHours(1));
            seferRepository.save(s1);

            // Ankara -> İstanbul
            Sefer s2 = new Sefer();
            s2.setArac(arac);
            s2.setKalkisNoktasi("Ankara");
            s2.setVarisNoktasi("İstanbul");
            s2.setKalkisZamani(LocalDateTime.now().plusDays(3).withHour(14).withMinute(30));
            s2.setVarisZamani(s2.getKalkisZamani().plusHours(1));
            seferRepository.save(s2);
        }
        log.info("Seferler kaydedildi.");
    }

    private void seedKoltuklar() {
        log.info("Koltuklar oluşturuluyor...");
        List<Sefer> seferler = seferRepository.findAll();
        for (Sefer sefer : seferler) {
            sefer.koltuklariOlustur();
            seferRepository.save(sefer);
        }
        log.info("Koltuklar kaydedildi.");
    }
}