package com.citygo.model;

/*
 * =============================================================
 * Otobus.java — Otobüs Alt Sınıfı (INHERITANCE + POLYMORPHISM)
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
 * - @DiscriminatorValue("OTOBUS")
 *
 * Ek Alanlar (private):
 * - ikramVar: boolean    → İkram dahil mi? (çay, kahve, atıştırmalık)
 * - ikramBedeli: double  → İkram bedeli (TL), ikramVar true ise geçerli
 *
 * Override Edilecek Metotlar:
 * - hesaplaToplamFiyat(double temelFiyat): double
 *     → if (ikramVar) return temelFiyat + ikramBedeli
 *     → else return temelFiyat
 *
 * - getAracTipi(): String
 *     → return "OTOBUS"
 *
 * Getter/Setter:
 * - Ek alanlar için getter/setter metotları
 */
