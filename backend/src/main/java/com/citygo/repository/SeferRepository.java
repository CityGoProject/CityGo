package com.citygo.repository;

import com.citygo.model.Sefer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
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

// Spring'e bu interface veritabanı işlemleri yaptığını söyleme 
@Repository
public interface SeferRepository extends JpaRepository<Sefer, Long> {

    // Sadece kalkış ve varış noktasına göre sefer arama
    // SELECT * FROM seferler WHERE kalkis_noktasi = ? AND varis_noktasi = ?
    List<Sefer> findByKalkisNoktasiAndVarisNoktasi(String kalkis, String varis);

    // Kalkış, varış ve tarih ARALIĞINA göre sefer arama
    // Between → o günün 00:00 ile 23:59 arasındaki seferleri bulur
    // SELECT * FROM seferler WHERE kalkis_noktasi = ? AND varis_noktasi = ?
    List<Sefer> findByKalkisNoktasiAndVarisNoktasiAndKalkisZamaniBetween(String kalkis, String varis,LocalDateTime baslangic, LocalDateTime bitis);

    List<Sefer> findByKalkisNoktasiAndVarisNoktasiAndKalkisZamani(
    String kalkis, String varis, LocalDateTime kalkisZamani);
}