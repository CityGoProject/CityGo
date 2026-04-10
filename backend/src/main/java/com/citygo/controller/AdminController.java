package com.citygo.controller;

/*
 * =============================================================
 * AdminController.java — Admin Paneli Controller
 * =============================================================
 * Sorumlu: Muhammed + Ömer Faruk
 *
 * Yalnızca admin kullanıcılarının erişebileceği yönetim endpoint'leri.
 *
 * Anotasyonlar:
 * - @RestController
 * - @RequestMapping("/api/admin")
 * - @CrossOrigin
 *
 * Bağımlılıklar:
 * - AramaService (sefer yönetimi için)
 * - KullaniciService (kullanıcı listesi için)
 * - BiletRepository (bilet listesi için)
 * - SeferRepository (sefer CRUD için)
 *
 * Endpoint'ler:
 *
 * GET /api/admin/seferler
 *     → Tüm seferleri listele
 *     → 200 OK + List<Sefer>
 *
 * POST /api/admin/seferler
 *     → Request Body: { aracId, kalkisNoktasi, varisNoktasi, kalkisZamani, varisZamani }
 *     → Yeni sefer oluştur + koltukları otomatik oluştur
 *     → 201 Created + Sefer nesnesi
 *
 * PUT /api/admin/seferler/{id}
 *     → Mevcut seferi güncelle
 *     → 200 OK + güncellenmiş Sefer
 *
 * DELETE /api/admin/seferler/{id}
 *     → Seferi sil (ilişkili koltuklar da silinmeli — cascade)
 *     → 204 No Content
 *
 * GET /api/admin/kullanicilar
 *     → Tüm kullanıcıları listele
 *     → 200 OK + List<Kullanici>
 *
 * GET /api/admin/biletler
 *     → Tüm biletleri listele
 *     → 200 OK + List<Bilet>
 *
 * GET /api/admin/istatistikler
 *     → Dashboard istatistikleri döndür
 *     → { toplamSefer, toplamBilet, aktifBilet, iptalBilet, toplamKullanici }
 *     → 200 OK + JSON
 *
 * Not: Admin kontrolü için basit bir yaklaşım:
 *      Request header'dan veya query'den kullanıcı ID'si alıp
 *      rolünün ADMIN olduğunu kontrol edin.
 */
