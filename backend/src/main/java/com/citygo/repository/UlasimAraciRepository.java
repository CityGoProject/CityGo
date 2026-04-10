package com.citygo.repository;

/*
 * =============================================================
 * UlasimAraciRepository.java — Ulaşım Aracı Veri Erişim Katmanı
 * =============================================================
 * Sorumlu: Mert
 *
 * Spring Data JPA repository interface'i.
 *
 * Tanım:
 *   public interface UlasimAraciRepository extends JpaRepository<UlasimAraci, Long>
 *
 * Özel Query Metotları:
 * - findByFirma(String firma): List<UlasimAraci>
 *     → Firmaya göre araç listesi
 *
 * Not: UlasimAraci abstract olmasına rağmen JPA, SINGLE_TABLE veya JOINED
 *      stratejisi sayesinde alt sınıfları (Ucak, Tren, Otobus) da döndürür.
 */
