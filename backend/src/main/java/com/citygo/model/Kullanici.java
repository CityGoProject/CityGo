package com.citygo.model;

/*
 * =============================================================
 * Kullanici.java — Abstract Kullanıcı Sınıfı (INHERITANCE + ABSTRACTION)
 * =============================================================
 * Sorumlu: Muhammed
 *
 * Yolcu ve Admin sınıflarının ortak özelliklerini barındıran ABSTRACT sınıf.
 *
 * OOP Prensipleri:
 * - INHERITANCE: Yolcu ve Admin bu sınıftan miras alacak
 * - ABSTRACTION: Ortak kullanıcı yapısını soyutlar
 * - ENCAPSULATION: Tüm alanlar private
 *
 * Anotasyonlar:
 * - @Entity
 * - @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
 * - @DiscriminatorColumn(name = "rol")
 *
 * Alanlar (private):
 * - id: Long        → @Id, @GeneratedValue — Birincil anahtar
 * - ad: String      → Kullanıcının adı (@NotBlank)
 * - soyad: String   → Kullanıcının soyadı (@NotBlank)
 * - email: String   → Email adresi (@Email, @Column(unique = true))
 * - sifre: String   → Şifre (hashlenmiş olarak saklanacak)
 * - telefon: String → Telefon numarası
 *
 * Getter/Setter:
 * - Tüm alanlar için public getter ve setter metotları
 */
