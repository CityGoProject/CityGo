package com.citygo.model;

/*
 * =============================================================
 * Koltuk.java — Koltuk Entity Sınıfı
 * =============================================================
 * Sorumlu: Mert
 *
 * Bir seferdeki tek bir koltuğu temsil eder.
 *
 * Anotasyonlar:
 * - @Entity
 *
 * Alanlar (private):
 * - id: Long             → @Id, @GeneratedValue
 * - koltukNo: int        → Koltuk numarası (1, 2, 3, ...)
 * - dolu: boolean        → Koltuk dolu mu? (varsayılan: false)
 * - tip: KoltukTipi      → @Enumerated(EnumType.STRING) — STANDART veya PREMIUM
 * - sefer: Sefer          → @ManyToOne — Bu koltuğun ait olduğu sefer
 *
 * Getter/Setter:
 * - Tüm alanlar için getter/setter metotları
 *
 * Not: Roadmap'ta "dpieces" yazıyor ama bu alan "dolu" (occupied) olmalı.
 */
