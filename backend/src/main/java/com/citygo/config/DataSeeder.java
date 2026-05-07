package com.citygo.config;

import com.citygo.model.*;
import com.citygo.repository.UlasimAraciRepository;
import com.citygo.repository.SeferRepository;
import com.citygo.repository.KoltukRepository;
import com.citygo.repository.KullaniciRepository;
import com.citygo.repository.BiletRepository;
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
    private final BiletRepository biletRepository;

    public DataSeeder(UlasimAraciRepository araciRepository, SeferRepository seferRepository,
            KoltukRepository koltukRepository, KullaniciRepository kullaniciRepository,
            KullaniciService kullaniciService, BiletRepository biletRepository) {
        this.araciRepository = araciRepository;
        this.seferRepository = seferRepository;
        this.koltukRepository = koltukRepository;
        this.kullaniciRepository = kullaniciRepository;
        this.kullaniciService = kullaniciService;
        this.biletRepository = biletRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Veritabanı temizleniyor...");
        biletRepository.deleteAll();
        koltukRepository.deleteAll();
        seferRepository.deleteAll();
        araciRepository.deleteAll();
        kullaniciRepository.deleteAll();
        log.info("Veritabanı temizlendi.");

        log.info("Seed işlemi başlıyor...");
        seedAdminKullanici();
        seedYolcuKullanici();
        seedUlasimAraclari();
        seedSeferler();
        log.info("Seed Başarı ile Tamamlandı!");
    }

    private void seedAdminKullanici() {
        String hashedSifre = kullaniciService.sifreHashle("admin123");
        Admin admin = new Admin(null, "Admin", "CityGo", "admin@citygo.com", hashedSifre, "05000000000", "SUPER_ADMIN");
        kullaniciRepository.save(admin);
        log.info("ADMIN OLUŞTURULDU: {}", admin.getEmail());
    }

    private void seedYolcuKullanici() {
        String hashedSifre = kullaniciService.sifreHashle("yolcu123");
        Yolcu yolcu = new Yolcu(null, "Yolcu", "Yolcu", "yolcu@citygo.com", hashedSifre, "05000000001", "12345678901");
        kullaniciRepository.save(yolcu);
        log.info("YOLCU OLUŞTURULDU: {}", yolcu.getEmail());
    }

    private void seedUlasimAraclari() {
        log.info("Ulaşım araçları oluşturuluyor...");

        Ucak ucak = new Ucak(null, "THY", "Boeing 737", 180, 1500.0, 0.15, "IST");
        araciRepository.save(ucak);

        Tren tren = new Tren(null, "TCDD", "YHT", 300, 450.0, "BUSINESS", "YHT");
        araciRepository.save(tren);

        Otobus otobus = new Otobus(null, "Metro Turizm", "Anadolu Isuzu", 40, 600.0, true, 50.0);
        araciRepository.save(otobus);

        log.info("Ulaşım araçları kaydedildi.");
    }

    private void seedSeferler() {
        log.info("Şehirler arası seferler ve koltuklar döngü ile oluşturuluyor (10 Büyükşehir)...");
        List<UlasimAraci> araclar = araciRepository.findAll();
        String[] sehirler = {
                "İstanbul", "Ankara", "İzmir", "Bursa", "Antalya", "Adana", "Konya", "Gaziantep", "Şanlıurfa", "Kocaeli"
        };

        int gunOfset = 1;
        for (UlasimAraci arac : araclar) {
            for (String kalkis : sehirler) {
                for (String varis : sehirler) {
                    if (!kalkis.equals(varis)) {
                        // Her araç için her şehir kombinasyonuna sefer ekle
                        Sefer sefer = new Sefer(null, arac, kalkis, varis,
                                LocalDateTime.now().plusDays(gunOfset).withHour(10).withMinute(0),
                                LocalDateTime.now().plusDays(gunOfset).withHour(12).withMinute(0), null);

                        // Koltukları otomatik oluştur (Sefer.java içindeki mantık)
                        sefer.koltuklariOlustur();

                        // Sefer kaydedilirken koltuklar da CascadeType.ALL sayesinde kaydedilir
                        seferRepository.save(sefer);

                        // Zamanları biraz dağıtalım
                        gunOfset = (gunOfset % 7) + 1;
                    }
                }
            }
        }
        log.info("Tüm şehirler arası seferler ve koltuklar kaydedildi.");
    }

}