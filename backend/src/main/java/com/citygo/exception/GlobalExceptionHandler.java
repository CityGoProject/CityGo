package com.citygo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice // tüm controllerdeki hataları dinleyen merkez, bu hatayı yakalayacak metodu arar.
public class GlobalExceptionHandler {

    //Bulunamadı Hataları (404 not found)
    @ExceptionHandler({
        BiletBulunamadiException.class,
        KoltukBulunamadiException.class,
        KullaniciBulunamadiException.class,
        SeferBulunamadiException.class
    })
    public ResponseEntity<Object> handleNotFoundExceptions(RuntimeException ex) { //hem mesajı hem durum kodunu göndermeyi sağlar.
        return createResponse("Bulunamadi", ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    //Geçersiz İşlem - Tarih Hataları(400 bad request)
    @ExceptionHandler({
        KapasiteDoluException.class,
        GecersizTarihException.class,
        IllegalArgumentException.class
    })
    public ResponseEntity<Object> handleBadRequestExceptions(RuntimeException ex) {
        return createResponse("Hatali İstek", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    //Genel Beklenmedik Hatalar (500 internal server error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralExceptions(Exception ex) {
        return createResponse("Sunucu Hatasi", " Beklenmeyen bir hata olustu.", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //yardımcı metot -> standart bir hata formatı oluşturmayı sağlar.
    private ResponseEntity<Object> createResponse(String hataTipi, String mesaj, HttpStatus durum) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("hata", hataTipi); //"hata" nın yerine hataTipi değişkenini koymaya yarar.
        body.put("mesaj", mesaj);
        body.put("zaman", LocalDateTime.now());

        return new ResponseEntity<>(body,durum);
    }

}
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
