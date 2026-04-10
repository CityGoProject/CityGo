package com.citygo.model;

/*
 * =============================================================
 * Ucak.java — Uçak Alt Sınıfı (INHERITANCE + POLYMORPHISM)
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
 * - @DiscriminatorValue("UCAK") (eğer SINGLE_TABLE stratejisi kullanılırsa)
 *
 * Ek Alanlar (private):
 * - havaalaniVergiOrani: double → Havaalanı vergi oranı (ör: 0.15 = %15)
 * - havaalani: String           → Kalkış havaalanı kodu (ör: "IST", "SAW")
 *
 * Override Edilecek Metotlar:
 * - hesaplaToplamFiyat(double temelFiyat): double
 *     → return temelFiyat + (temelFiyat * havaalaniVergiOrani)
 *     → Uçak biletine havaalanı vergisi eklenir
 *
 * - getAracTipi(): String
 *     → return "UCAK"
 *
 * Getter/Setter:
 * - Ek alanlar için getter/setter metotları
 */
