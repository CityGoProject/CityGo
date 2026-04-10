package com.citygo.model;

/*
 * =============================================================
 * Bilet.java — Bilet Entity Sınıfı
 * =============================================================
 * Sorumlu: Elif Feyza
 *
 * Bir yolcunun bir seferdeki koltuk rezervasyonunu temsil eder.
 *
 * Anotasyonlar:
 * - @Entity
 *
 * Alanlar (private):
 * - id: Long                    → @Id, @GeneratedValue
 * - yolcu: Yolcu                → @ManyToOne — Bilet sahibi yolcu
 * - sefer: Sefer                → @ManyToOne — Hangi sefer
 * - koltuk: Koltuk              → @OneToOne  — Hangi koltuk
 * - odenenTutar: double         → Ödenen toplam tutar (hesaplaToplamFiyat ile)
 * - olusturmaTarihi: LocalDate  → Bilet oluşturulma tarihi
 * - durum: BiletDurumu          → @Enumerated(EnumType.STRING)
 *                                  AKTIF / IPTAL_EDILDI / KULLANILDI
 *
 * Getter/Setter:
 * - Tüm alanlar için getter/setter metotları
 *
 * Not: olusturmaTarihi alanına @PrePersist ile otomatik tarih atanabilir.
 *      yolcu alanı @JsonBackReference ile sonsuz döngü engellenmelidir.
 */
