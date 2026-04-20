package com.citygo.exception;

public class BiletBulunamadiException extends RuntimeException {
   
    public BiletBulunamadiException(String mesaj){
        super(mesaj);
    }

}
/*
 * =============================================================
 * BiletBulunamadiException.java — Bilet Bulunamadı Hatası
 * =============================================================
 * Sorumlu: Elif Feyza
 *
 * Verilen ID ile bilet bulunamadığında fırlatılır.
 *
 * RuntimeException'dan extends eder.
 *
 * Constructor:
 * - BiletBulunamadiException(String mesaj)
 *     → Örnek mesaj: "Bilet bulunamadı: ID = 123"
 */
