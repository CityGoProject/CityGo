package com.citygo.repository;

/*
 * =============================================================
 * BiletRepository.java — Bilet Veri Erişim Katmanı
 * =============================================================
 * Sorumlu: Elif Feyza
 *
 * Spring Data JPA repository interface'i.
 *
 * Tanım:
 *   public interface BiletRepository extends JpaRepository<Bilet, Long>
 *
 * Özel Query Metotları:
 * - findByYolcu_Id(Long yolcuId): List<Bilet>
 *     → Bir yolcunun tüm biletlerini getir
 *
 * - findByYolcu_IdAndDurum(Long yolcuId, BiletDurumu durum): List<Bilet>
 *     → Bir yolcunun sadece aktif/iptal/kullanılmış biletlerini getir
 *
 * - findBySefer_Id(Long seferId): List<Bilet>
 *     → Bir sefere ait tüm biletleri getir (admin paneli için)
 *
 * - countByDurum(BiletDurumu durum): long
 *     → Durum bazlı bilet sayısı (istatistikler için)
 */
