package com.citygo.model;

/*
 * =============================================================
 * Sefer.java — Sefer Entity Sınıfı
 * =============================================================
 * Sorumlu: Mert
 *
 * Bir ulaşım aracının belirli bir tarih ve güzergahta
 * gerçekleştireceği seferi temsil eder.
 *
 * Anotasyonlar:
 * - @Entity
 *
 * Alanlar (private):
 * - id: Long                    → @Id, @GeneratedValue
 * - arac: UlasimAraci           → @ManyToOne — Bu seferde kullanılan araç
 * - kalkisNoktasi: String       → Kalkış şehri/istasyonu (ör: "İstanbul")
 * - varisNoktasi: String        → Varış şehri/istasyonu (ör: "Ankara")
 * - kalkisZamani: LocalDateTime → Kalkış tarihi ve saati
 * - varisZamani: LocalDateTime  → Tahmini varış tarihi ve saati
 * - koltuklar: List<Koltuk>     → @OneToMany — Bu sefere ait koltuklar
 *                                  (mappedBy = "sefer", cascade = ALL)
 *
 * Getter/Setter:
 * - Tüm alanlar için getter/setter metotları
 *
 * Not: koltuklar listesi sefer oluşturulurken otomatik olarak
 *      aracın kapasitesi kadar Koltuk nesnesi ile doldurulmalıdır.
 */
