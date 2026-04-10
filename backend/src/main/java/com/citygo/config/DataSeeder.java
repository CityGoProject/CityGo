package com.citygo.config;

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
