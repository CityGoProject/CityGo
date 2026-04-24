package com.citygo.model;

/*
 * =============================================================
 * KoltukTipi.java — Koltuk Tipi Enum
 * =============================================================
 * Sorumlu: Mert
 *
 * Koltuğun tipini belirleyen enum sabitleri.
 *
 * Değerler:
 * - STANDART → Normal koltuk
 * - PREMIUM  → Ekstra konforlu / geniş koltuk
 *
 * Kullanımı: Koltuk entity'sinde @Enumerated(EnumType.STRING) ile
 */


public enum KoltukTipi {
    STANDART, // Normal koltuk -- standart fiyat
    PREMIUM   // Geniş koltuk -- daha yüksek fiyat
}