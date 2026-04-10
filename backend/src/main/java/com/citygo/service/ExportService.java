package com.citygo.service;

/*
 * =============================================================
 * ExportService.java — Dışa Aktarım İş Mantığı
 * =============================================================
 * Sorumlu: Ömer Faruk
 *
 * IExportable interface'ini implement eden veri dışa aktarım servisi.
 *
 * Anotasyonlar:
 * - @Service
 *
 * Implements:
 * - IExportable
 *
 * Bağımlılıklar:
 * - BiletRepository
 *
 * Metotlar:
 *
 * - exportJSON(): byte[]
 *     Akış:
 *     1. Tüm biletleri BiletRepository.findAll() ile getir
 *     2. ObjectMapper (Jackson) kullanarak JSON'a çevir
 *     3. JSON string'ini byte dizisine çevirip döndür
 *     Örnek: objectMapper.writeValueAsBytes(biletler)
 *
 * - exportCSV(): byte[]
 *     Akış:
 *     1. Tüm biletleri getir
 *     2. StringBuilder ile CSV formatı oluştur
 *        - İlk satır: başlıklar (ID, Yolcu, Sefer, Koltuk, Tutar, Tarih, Durum)
 *        - Sonraki satırlar: veri
 *     3. CSV string'ini byte dizisine çevirip döndür
 *
 * Not: Jackson kütüphanesi spring-boot-starter-web ile otomatik gelir.
 *      Ekstra bağımlılık eklemeye gerek yok.
 */
