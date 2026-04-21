package com.citygo.repository;

import com.citygo.model.Koltuk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/*
 * =============================================================
 * KoltukRepository.java — Koltuk Veri Erişim Katmanı
 * =============================================================
 * Sorumlu: Mert
 *
 * Spring Data JPA repository interface'i.
 *
 * Tanım:
 * public interface KoltukRepository extends JpaRepository<Koltuk, Long>
 *
 * Özel Query Metotları:
 * - findBySefer_Id(Long seferId): List<Koltuk>
 * → Bir sefere ait tüm koltukları getir (dolu/boş hepsi)
 *
 * - findBySefer_IdAndDolu(Long seferId, boolean dolu): List<Koltuk>
 * → Bir seferdeki sadece boş (false) veya dolu (true) koltukları getir
 *
 * - findBySefer_IdAndKoltukNo(Long seferId, int koltukNo): Optional<Koltuk>
 * → Belirli bir seferdeki belirli numaralı koltuğu bul
 */

// Spring'e bu interface veritabanı işlemleri yaptığını söyleme 
@Repository
public interface KoltukRepository extends JpaRepository<Koltuk, Long> {

    // Bir sefere ait TÜM koltukları getirir (dolu + boş)
    // SELECT * FROM koltuklar WHERE sefer_id = ?
    List<Koltuk> findBySefer_Id(Long seferId);

    // Bir seferdeki sadece BOŞ veya DOLU koltukları getirir
    // SELECT * FROM koltuklar WHERE sefer_id = ? AND dolu = ?
    List<Koltuk> findBySefer_IdAndDolu(Long seferId, boolean dolu);

    // Belirli bir seferdeki belirli numaralı koltuğu bulur
    // SELECT * FROM koltuklar WHERE sefer_id = ? AND koltuk_no = ?
    Optional<Koltuk> findBySefer_IdAndKoltukNo(Long seferId, int koltukNo);
}
