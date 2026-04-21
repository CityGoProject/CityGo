package com.citygo.repository;

import com.citygo.model.UlasimAraci;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

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

// Spring'e bu interface veritabanı işlemleri yaptığını söyleme 
@Repository

public interface UlasimAraciRepository extends JpaRepository<UlasimAraci, Long> {

    // Firmaya göre araç listesi getirir
    // SELECT * FROM ulasim_araclari WHERE firma = ?
    List<UlasimAraci> findByFirma(String firma);
}