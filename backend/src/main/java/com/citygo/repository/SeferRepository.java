package com.citygo.repository;

import com.citygo.model.Sefer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


/*
 * =============================================================
 * SeferRepository.java — Sefer Veri Erişim Katmanı
 * =============================================================
 * Sorumlu: Mert
 *
 * Spring Data JPA repository interface'i.
 *
 * Tanım:
 *   public interface SeferRepository extends JpaRepository<Sefer, Long>
 *
 * Özel Query Metotları:
 * - findByKalkisNoktasiAndVarisNoktasi(String kalkis, String varis): List<Sefer>
 *     → Kalkış ve varış noktasına göre sefer arama
 *
 * - findByKalkisNoktasiAndVarisNoktasiAndKalkisZamaniBetween(
 *       String kalkis, String varis,
 *       LocalDateTime baslangic, LocalDateTime bitis): List<Sefer>
 *     → Kalkış, varış ve tarih aralığına göre arama
 *
 * - findByArac_AracTipi(String aracTipi): List<Sefer>
 *     → Araç tipine göre filtreleme (opsiyonel @Query ile)
 *
 * Not: Tarih filtresi için kalkisZamani alanının başlangıç-bitiş
 *      arasında olup olmadığı kontrol edilecek (Between).
 */

@Repository
public interface SeferRepository extends JpaRepository<Sefer, Long> {

    List<Sefer> findByKalkisNoktasiAndVarisNoktasi(String kalkis, String varis);

    List<Sefer> findByKalkisNoktasiAndVarisNoktasiAndKalkisZamani(
        String kalkis, String varis, String kalkisZamani);

    List<Sefer> findByArac_AracTipi(String aracTipi);
}