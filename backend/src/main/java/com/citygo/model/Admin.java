package com.citygo.model;

/*
 * =============================================================
 * Admin.java — Admin Alt Sınıfı (INHERITANCE)
 * =============================================================
 * Sorumlu: Muhammed
 *
 * Kullanici abstract sınıfından miras alan, sistemi yöneten
 * admin kullanıcılarını temsil eder.
 *
 * Anotasyonlar:
 * - @Entity
 * - @DiscriminatorValue("ADMIN")
 *
 * Ek Alanlar (private):
 * - yetki: String → Admin yetki seviyesi (ör: "SUPER_ADMIN", "MODERATOR")
 *
 * Getter/Setter:
 * - Ek alanlar için getter/setter metotları
 */
