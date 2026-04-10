package com.citygo.exception;

/*
 * =============================================================
 * GlobalExceptionHandler.java — Merkezi Hata Yönetimi
 * =============================================================
 * Sorumlu: Elif Feyza
 *
 * Tüm controller'lardaki exception'ları yakalayıp düzgün HTTP
 * response'ları döndüren merkezi hata yönetim sınıfı.
 *
 * Anotasyonlar:
 * - @RestControllerAdvice (veya @ControllerAdvice + @ResponseBody)
 *
 * Bu sınıf sayesinde controller'larda try-catch yazmaya gerek kalmaz!
 * Exception fırlatıldığında Spring otomatik olarak bu sınıftaki
 * uygun handler metodu çalıştırır.
 *
 * Handler Metotları:
 *
 * @ExceptionHandler(KapasiteDoluException.class)
 * @ResponseStatus(HttpStatus.BAD_REQUEST) → 400
 *     → return { hata: "Kapasite Dolu", mesaj: exception.getMessage() }
 *
 * @ExceptionHandler(GecersizTarihException.class)
 * @ResponseStatus(HttpStatus.BAD_REQUEST) → 400
 *     → return { hata: "Geçersiz Tarih", mesaj: exception.getMessage() }
 *
 * @ExceptionHandler(BiletBulunamadiException.class)
 * @ResponseStatus(HttpStatus.NOT_FOUND) → 404
 *     → return { hata: "Bilet Bulunamadı", mesaj: exception.getMessage() }
 *
 * @ExceptionHandler(KullaniciBulunamadiException.class)
 * @ResponseStatus(HttpStatus.NOT_FOUND) → 404
 *     → return { hata: "Kullanıcı Bulunamadı", mesaj: exception.getMessage() }
 *
 * @ExceptionHandler(SeferBulunamadiException.class)
 * @ResponseStatus(HttpStatus.NOT_FOUND) → 404
 *     → return { hata: "Sefer Bulunamadı", mesaj: exception.getMessage() }
 *
 * @ExceptionHandler(Exception.class)
 * @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) → 500
 *     → Beklenmeyen hatalar için genel handler
 *     → return { hata: "Sunucu Hatası", mesaj: "Beklenmeyen bir hata oluştu" }
 *
 * Döndürülen Response Body formatı (Map veya özel bir ErrorResponse sınıfı):
 * {
 *   "hata": "Hata tipi",
 *   "mesaj": "Detaylı açıklama",
 *   "zaman": "2026-04-10T15:30:00"
 * }
 */
