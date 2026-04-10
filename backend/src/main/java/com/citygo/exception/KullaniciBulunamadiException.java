package com.citygo.exception;

/*
 * =============================================================
 * KullaniciBulunamadiException.java — Kullanıcı Bulunamadı Hatası
 * =============================================================
 * Sorumlu: Elif Feyza
 *
 * Verilen ID veya email ile kullanıcı bulunamadığında fırlatılır.
 * Giriş yapılırken email/şifre yanlışsa da kullanılabilir.
 *
 * RuntimeException'dan extends eder.
 *
 * Constructor:
 * - KullaniciBulunamadiException(String mesaj)
 *     → Örnek mesaj: "Kullanıcı bulunamadı: email = test@test.com"
 */
