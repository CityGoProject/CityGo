package com.citygo.model;

/*
 * =============================================================
 * Tren.java — Tren Alt Sınıfı (INHERITANCE + POLYMORPHISM)
 * =============================================================
 * Sorumlu: Mert
 *
 * UlasimAraci abstract sınıfından miras alan somut sınıf.
 *
 * OOP Prensipleri:
 * - INHERITANCE: UlasimAraci'ndan extends eder
 * - POLYMORPHISM (Override): hesaplaToplamFiyat() metodunu geçersiz kılar
 *
 * Anotasyonlar:
 * - @Entity
 * - @DiscriminatorValue("TREN")
 *
 * Ek Alanlar (private):
 * - vagonTipi: String → "EKONOMI" veya "BUSINESS"
 * - hatTipi: String   → "YHT" (Yüksek Hızlı Tren) veya "NORMAL"
 *
 * Override Edilecek Metotlar:
 * - hesaplaToplamFiyat(double temelFiyat): double
 *     → Business vagon ise temelFiyat * 1.5
 *     → YHT ise ek %20 hız farkı ücreti
 *     → Ekonomi + Normal ise temelFiyat aynen döner
 *
 * - getAracTipi(): String
 *     → return "TREN"
 *
 * Getter/Setter:
 * - Ek alanlar için getter/setter metotları
 */
