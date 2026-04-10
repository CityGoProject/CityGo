package com.citygo.service;

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
