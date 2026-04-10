package com.citygo.model;

/*
 * =============================================================
 * UlasimAraci.java — Abstract Üst Sınıf (INHERITANCE + ABSTRACTION)
 * =============================================================
 * Sorumlu: Mert
 *
 * Tüm ulaşım araçlarının (Uçak, Tren, Otobüs) ortak özelliklerini
 * barındıran ABSTRACT sınıf. Doğrudan nesne oluşturulamaz.
 *
 * OOP Prensipleri:
 * - INHERITANCE: Ucak, Tren, Otobus bu sınıftan miras alacak
 * - ABSTRACTION: abstract metotlar alt sınıflarda implement edilecek
 * - ENCAPSULATION: Tüm alanlar private, getter/setter ile erişim
 *
 * Anotasyonlar:
 * - @Entity
 * - @Inheritance(strategy = InheritanceType.SINGLE_TABLE) veya JOINED
 * - @DiscriminatorColumn (SINGLE_TABLE kullanılırsa)
 *
 * Alanlar (private):
 * - id: Long            → @Id, @GeneratedValue — Birincil anahtar
 * - firma: String       → Araç firması (ör: "THY", "TCDD", "Metro Turizm")
 * - model: String       → Araç modeli
 * - kapasite: int       → Toplam koltuk sayısı
 * - biletFiyati: double → Temel bilet fiyatı (TL)
 *
 * Abstract Metotlar:
 * - hesaplaToplamFiyat(double temelFiyat): double
 *     → Her alt sınıf kendi fiyat hesaplamasını yapacak (POLYMORPHISM - Override)
 *     → Uçak: temelFiyat + havaalanı vergisi
 *     → Tren: temelFiyat + vagon tipi farkı
 *     → Otobüs: temelFiyat + ikram bedeli
 *
 * - getAracTipi(): String
 *     → "UCAK", "TREN" veya "OTOBUS" döndürecek
 *
 * Getter/Setter:
 * - Tüm alanlar için public getter ve setter metotları yazılacak
 */
