package com.citygo.repository;

import com.citygo.model.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

// JpaRepository'den extends edince save, findAll, deleteById gibi metotlar otomatik geliyor
public interface KullaniciRepository extends JpaRepository<Kullanici, Long> {

    // Spring metot adina bakarak SQL uretiyor, biz sql yazmiyoruz
    Optional<Kullanici> findByEmail(String email); // SELECT * FROM kullanicilar WHERE email = ?

    Optional<Kullanici> findByEmailAndSifre(String email, String sifre); // email + sifre ile giris dogrulama

    boolean existsByEmail(String email); // kayit sirasinda ayni email var mi kontrolu

    // Duzeltme: kayitta telefon tekilligini servis katmaninda net hata mesaji ile kontrol ediyoruz.
    @Query("select case when count(k) > 0 then true else false end from Kullanici k where k.telefon = :telefon")
    boolean existsByTelefon(@Param("telefon") String telefon);

    // Duzeltme: TC kimlik no Yolcu alt sinifinda oldugu icin JPQL ile dogrudan Yolcu uzerinden sorguluyoruz.
    @Query("select case when count(y) > 0 then true else false end from Yolcu y where y.tcNo = :tcNo")
    boolean existsByTcNo(@Param("tcNo") String tcNo);

}

/*
 * =============================================================
 * KullaniciRepository.java — Kullanıcı Veri Erişim Katmanı
 * =============================================================
 * Sorumlu: Muhammed
 *
 * Spring Data JPA repository interface'i. JpaRepository'den extends eder.
 * SQL yazmadan otomatik CRUD metotları sağlar.
 *
 * Tanım:
 * public interface KullaniciRepository extends JpaRepository<Kullanici, Long>
 *
 * Özel Query Metotları:
 * - findByEmail(String email): Optional<Kullanici>
 * → Giriş yaparken email ile kullanıcı bulma
 *
 * - findByEmailAndSifre(String email, String sifre): Optional<Kullanici>
 * → Email + şifre ile giriş doğrulama
 *
 * - existsByEmail(String email): boolean
 * → Kayıt sırasında email'in zaten var olup olmadığını kontrol etme
 *
 * - existsByTelefon(String telefon): boolean
 * → Kayıt sırasında telefonun zaten var olup olmadığını kontrol etme
 *
 * - existsByTcNo(String tcNo): boolean
 * → Kayıt sırasında TC kimlik numarasının zaten var olup olmadığını kontrol etme
 *
 * Not: Temel sorgular metot isminden uretilir; alt sinif alani olan tcNo icin
 * JPQL @Query kullanilir.
 */
