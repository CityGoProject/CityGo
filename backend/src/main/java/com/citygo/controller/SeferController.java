package com.citygo.controller;

/*
 * =============================================================
 * SeferController.java — Sefer Arama Controller
 * =============================================================
 * Sorumlu: Mert
 *
 * Sefer arama ve sefer detayı endpoint'lerini barındırır.
 * Herkese açık (public) endpoint'ler — giriş gerektirmez.
 *
 * Anotasyonlar:
 * - @RestController
 * - @RequestMapping("/api/seferler")
 * - @CrossOrigin
 *
 * Bağımlılıklar:
 * - AramaService
 * - KoltukRepository
 *
 * Endpoint'ler:
 *
 * GET /api/seferler/ara?kalkis=...&varis=...&tarih=...&tip=...
 *     → Query parametreleri ile sefer arama
 *     → Hangi parametreler gelirse AramaService'in uygun overloaded
 *       metodu çağrılır:
 *       - Sadece kalkis+varis → ara(kalkis, varis)
 *       - kalkis+varis+tarih → ara(kalkis, varis, tarih)
 *       - kalkis+varis+tarih+tip → ara(kalkis, varis, tarih, tip)
 *     → 200 OK + List<Sefer>
 *
 * GET /api/seferler/{id}
 *     → @PathVariable ile sefer ID'si al
 *     → Sefer detayını döndür
 *     → 200 OK + Sefer nesnesi
 *     → Bulunamazsa: 404 Not Found
 *
 * GET /api/seferler/{id}/koltuklar
 *     → Belirtilen sefere ait tüm koltukları döndür
 *     → 200 OK + List<Koltuk> (her koltuğun dolu/boş durumu dahil)
 *     → Frontend koltuk haritası bu endpoint'i kullanacak
 */
