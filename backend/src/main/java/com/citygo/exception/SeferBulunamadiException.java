package com.citygo.exception;

public class SeferBulunamadiException extends RuntimeException {

    // Constructor adı sınıf adıyla aynı olmalı.
    public SeferBulunamadiException(String mesaj){
        super(mesaj);
    }
    
}
/*
 * =============================================================
 * SeferBulunamadiException.java — Sefer Bulunamadı Hatası
 * =============================================================
 * Sorumlu: Elif Feyza
 *
 * Verilen ID ile sefer bulunamadığında fırlatılır.
 *
 * RuntimeException'dan extends eder.
 *
 * Constructor:
 * - SeferBulunamadiException(String mesaj)
 *     → Örnek mesaj: "Sefer bulunamadı: ID = 456"
 */
