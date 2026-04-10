package com.citygo.service;

/*
 * =============================================================
 * KullaniciService.java — Kullanıcı İş Mantığı Katmanı
 * =============================================================
 * Sorumlu: Muhammed
 *
 * Kullanıcı kayıt, giriş ve yönetim işlemlerini yürüten servis sınıfı.
 *
 * Anotasyonlar:
 * - @Service
 *
 * Bağımlılıklar (@Autowired veya Constructor Injection):
 * - KullaniciRepository
 *
 * Metotlar:
 *
 * - kayitOl(String ad, String soyad, String email, String sifre, String telefon, String tcNo): Yolcu
 *     → Yeni yolcu kaydı oluşturur
 *     → Email zaten varsa uygun exception fırlatır
 *     → Şifreyi hashleyerek kaydeder (basit bir hash yeterli, ör: BCrypt veya SHA-256)
 *
 * - girisYap(String email, String sifre): Kullanici
 *     → Email ve şifre ile giriş doğrulama
 *     → Bulunamazsa KullaniciBulunamadiException fırlatır
 *     → Başarılı girişte Kullanici nesnesini döndürür
 *
 * - kullaniciBul(Long id): Kullanici
 *     → ID ile kullanıcı bulma
 *
 * - tumKullanicilariGetir(): List<Kullanici>
 *     → Admin paneli için tüm kullanıcıları listele
 *
 * Not: Şifreleme için basit bir yaklaşım yeterlidir.
 *      Spring Security kullanmıyoruz, karmaşıklaştırmayın.
 */
