package com.citygo.exception;

public class KapasiteDoluException extends RuntimeException {

    public KapasiteDoluException(String mesaj){
        super(mesaj);
    }
    
}
/*
 * =============================================================
 * KapasiteDoluException.java — Kapasite Dolu Hatası
 * =============================================================
 * Sorumlu: Elif Feyza
 *
 * Koltuk zaten doluyken rezervasyon yapılmaya çalışıldığında fırlatılır.
 *
 * RuntimeException'dan extends eder.
 *
 * Constructor:
 * - KapasiteDoluException(String mesaj)
 *     → super(mesaj) çağrısı
 *     → Örnek mesaj: "Seçilen koltuk zaten dolu!"
 */
