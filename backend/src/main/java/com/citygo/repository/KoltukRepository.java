package com.citygo.repository;

/*
 * =============================================================
 * KoltukRepository.java — Koltuk Veri Erişim Katmanı
 * =============================================================
 * Sorumlu: Mert
 *
 * Spring Data JPA repository interface'i.
 *
 * Tanım:
 *   public interface KoltukRepository extends JpaRepository<Koltuk, Long>
 *
 * Özel Query Metotları:
 * - findBySefer_Id(Long seferId): List<Koltuk>
 *     → Bir sefere ait tüm koltukları getir (dolu/boş hepsi)
 *
 * - findBySefer_IdAndDolu(Long seferId, boolean dolu): List<Koltuk>
 *     → Bir seferdeki sadece boş (false) veya dolu (true) koltukları getir
 *
 * - findBySefer_IdAndKoltukNo(Long seferId, int koltukNo): Optional<Koltuk>
 *     → Belirli bir seferdeki belirli numaralı koltuğu bul
 */
