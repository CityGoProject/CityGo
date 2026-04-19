package com.citygo.repository;

import com.citygo.model.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

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
 * Not: Spring Data JPA, metot isimlerinden otomatik SQL oluşturur.
 * Ekstra @Query anotasyonu yazmaya gerek yok!
 */
