package com.citygo.controller;

import com.citygo.model.Kullanici;
import com.citygo.model.Yolcu;
import com.citygo.service.KullaniciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private KullaniciService kullaniciService;

    @PostMapping("/register")
    public ResponseEntity<?> kayitOl(@RequestBody Map<String, String> body) {
        try {
            Yolcu yolcu = kullaniciService.kayitOl(
                    body.get("ad"),
                    body.get("soyad"),
                    body.get("email"),
                    body.get("sifre"),
                    body.get("telefon"),
                    body.get("tcNo"));
            return ResponseEntity.status(HttpStatus.CREATED).body(yolcu);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("hata", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> girisYap(@RequestBody Map<String, String> body) {
        try {
            Kullanici kullanici = kullaniciService.girisYap(
                    body.get("email"),
                    body.get("sifre"));
            return ResponseEntity.ok(kullanici);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("hata", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> cikisYap() {
        // Auth state frontend'de tutulduğu için backend tarafında bilgilendirici cevap yeterli.
        return ResponseEntity.ok(Map.of("mesaj", "Çıkış başarılı"));
    }
}

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
 * → Request Body: { ad, soyad, email, sifre, telefon, tcNo }
 * → KullaniciService.kayitOl() çağır
 * → Başarılı: 201 Created + Yolcu nesnesi
 * → Hata: 400 Bad Request (email zaten var)
 *
 * POST /api/auth/login
 * → Request Body: { email, sifre }
 * → KullaniciService.girisYap() çağır
 * → Başarılı: 200 OK + Kullanici nesnesi (id, ad, rol bilgisi)
 * → Hata: 401 Unauthorized (email/şifre yanlış)
 *
 * POST /api/auth/logout
 * → Çıkış işlemi (frontend'de token/session temizleme yeterli)
 * → 200 OK döndür
 *
 * Not: JWT veya session kullanımı opsiyoneldir.
 * Basit yaklaşım: Login'de kullanıcı bilgilerini döndür,
 * frontend localStorage'da tut.
 */
