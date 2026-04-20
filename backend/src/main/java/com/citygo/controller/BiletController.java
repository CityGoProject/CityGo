package com.citygo.controller;

import com.citygo.model.Bilet;

/*
 * =============================================================
 * BiletController.java — Bilet İşlemleri Controller
 * =============================================================
 * Sorumlu: Elif Feyza
 *
 * Bilet satın alma, listeleme ve iptal endpoint'lerini barındırır.
 *
 * Anotasyonlar:
 * - @RestController
 * - @RequestMapping("/api/biletler")
 * - @CrossOrigin
 *
 * Bağımlılıklar:
 * - RezervasyonService
 *
 * Endpoint'ler:
 *
 * POST /api/biletler
 *     → Request Body: { yolcuId, seferId, koltukNo }
 *     → RezervasyonService.rezervasyonYap() çağır
 *     → Başarılı: 201 Created + Bilet nesnesi
 *     → Hata: 400 (koltuk dolu), 404 (sefer/yolcu bulunamadı)
 *
 * GET /api/biletler/benim?yolcuId=...
 *     → Query parametresi ile yolcunun biletlerini getir
 *     → RezervasyonService.rezervasyonlariGetir() çağır
 *     → 200 OK + List<Bilet>
 *
 * PUT /api/biletler/{id}/iptal
 *     → @PathVariable ile bilet ID'si al
 *     → RezervasyonService.rezervasyonIptal() çağır
 *     → Başarılı: 200 OK + { mesaj: "Bilet iptal edildi" }
 *     → Hata: 404 (bilet bulunamadı)
 */
