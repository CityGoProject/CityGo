package com.citygo.controller;

/*
 * =============================================================
 * ExportController.java — Dışa Aktarım Controller
 * =============================================================
 * Sorumlu: Ömer Faruk
 *
 * Bilet verilerini JSON/CSV formatında dosya olarak indirme endpoint'leri.
 *
 * Anotasyonlar:
 * - @RestController
 * - @RequestMapping("/api/export")
 * - @CrossOrigin
 *
 * Bağımlılıklar:
 * - ExportService
 *
 * Endpoint'ler:
 *
 * GET /api/export/biletler/json
 *     → ExportService.exportJSON() çağır
 *     → ResponseEntity<byte[]> döndür
 *     → Header'lar:
 *       - Content-Type: application/json
 *       - Content-Disposition: attachment; filename="biletler.json"
 *
 * GET /api/export/biletler/csv
 *     → ExportService.exportCSV() çağır
 *     → ResponseEntity<byte[]> döndür
 *     → Header'lar:
 *       - Content-Type: text/csv
 *       - Content-Disposition: attachment; filename="biletler.csv"
 *
 * Not: Content-Disposition: attachment header'ı tarayıcının
 *      dosyayı otomatik indirmesini sağlar.
 */
