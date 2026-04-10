package com.citygo.model;

/*
 * =============================================================
 * Yolcu.java — Yolcu Alt Sınıfı (INHERITANCE)
 * =============================================================
 * Sorumlu: Muhammed
 *
 * Kullanici abstract sınıfından miras alan, sisteme kayıt olan
 * normal kullanıcıları (yolcuları) temsil eder.
 *
 * Anotasyonlar:
 * - @Entity
 * - @DiscriminatorValue("YOLCU")
 *
 * Ek Alanlar (private):
 * - tcNo: String               → T.C. Kimlik Numarası (11 haneli)
 * - biletler: List<Bilet>      → @OneToMany ilişki — Yolcunun biletleri
 *                                 (mappedBy = "yolcu")
 *
 * Getter/Setter:
 * - Ek alanlar için getter/setter metotları
 *
 * Not: biletler alanı @JsonIgnore veya @JsonManagedReference ile
 *      sonsuz döngü problemi engellenmelidir.
 */
