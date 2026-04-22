package com.citygo.repository;

import com.citygo.model.Bilet;
import com.citygo.model.BiletDurumu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BiletRepository extends JpaRepository<Bilet, Long> {

    List<Bilet> findByYolcu_Id(Long yolcuId); //bir yolcunun tüm biletlerini getirir

    List<Bilet> findByYolcu_IdAndDurum(Long yolcuId, BiletDurumu durum); //bir yolcunun sadece belirli durumdaki biletlerini getirir

    List<Bilet> findBySefer_Id(Long seferId); //bir sefere ait tüm biletleri getirir(admin paneli için)

    long countByDurum(BiletDurumu durum); //istatistikler için durum bazlı bilet sayısı
}
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
