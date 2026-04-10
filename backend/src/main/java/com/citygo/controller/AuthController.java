package com.citygo.controller;

/*
 * =============================================================
 * AuthController.java — Kimlik Doğrulama Controller
 * =============================================================
 * Sorumlu: Muhammed
 *
 * Kullanıcı kayıt ve giriş endpoint'lerini barındırır.
 *
 * Anotasyonlar:
 * - @RestController
 * - @RequestMapping("/api/auth")
 * - @CrossOrigin (CORS izni — frontend'den erişim için)
 *
 * Bağımlılıklar:
 * - KullaniciService
 *
 * Endpoint'ler:
 *
 * POST /api/auth/register
 *     → Request Body: { ad, soyad, email, sifre, telefon, tcNo }
 *     → KullaniciService.kayitOl() çağır
 *     → Başarılı: 201 Created + Yolcu nesnesi
 *     → Hata: 400 Bad Request (email zaten var)
 *
 * POST /api/auth/login
 *     → Request Body: { email, sifre }
 *     → KullaniciService.girisYap() çağır
 *     → Başarılı: 200 OK + Kullanici nesnesi (id, ad, rol bilgisi)
 *     → Hata: 401 Unauthorized (email/şifre yanlış)
 *
 * POST /api/auth/logout
 *     → Çıkış işlemi (frontend'de token/session temizleme yeterli)
 *     → 200 OK döndür
 *
 * Not: JWT veya session kullanımı opsiyoneldir.
 *      Basit yaklaşım: Login'de kullanıcı bilgilerini döndür,
 *      frontend localStorage'da tut.
 */
