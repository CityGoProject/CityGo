package com.citygo.controller;

import com.citygo.dto.AuthResponse;
import com.citygo.dto.LoginRequest;
import com.citygo.dto.RegisterRequest;
import com.citygo.model.Yolcu;
import com.citygo.service.KullaniciService;
import jakarta.validation.Valid;
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
    public ResponseEntity<AuthResponse> kayitOl(@Valid @RequestBody RegisterRequest request) {
        // Duzeltme: Map yerine DTO + @Valid kullaniyoruz ve sifre icermeyen
        // AuthResponse donuyoruz.
        Yolcu yolcu = kullaniciService.kayitOl(
                request.ad(),
                request.soyad(),
                request.email(),
                request.sifre(),
                request.telefon(),
                request.tcNo());
        return ResponseEntity.status(HttpStatus.CREATED).body(AuthResponse.from(yolcu));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> girisYap(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(AuthResponse.from(kullaniciService.girisYap(
                request.email(),
                request.sifre())));
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
