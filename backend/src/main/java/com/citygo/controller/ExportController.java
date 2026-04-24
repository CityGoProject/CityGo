package com.citygo.controller;

import com.citygo.service.ExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

@RestController
@RequestMapping("/api/export")
@CrossOrigin(origins = "http://localhost:5173")
public class ExportController {

    private final ExportService exportService;

    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping("/biletler/json")
    public ResponseEntity<byte[]> exportJson() {
        byte[] jsonData = exportService.exportJSON();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"biletler.json\"")
                .body(jsonData);
    }

    @GetMapping("/biletler/csv")
    public ResponseEntity<byte[]> exportCsv() {
        byte[] csvData = exportService.exportCSV();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/csv"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"biletler.csv\"")
                .body(csvData);
    }
}