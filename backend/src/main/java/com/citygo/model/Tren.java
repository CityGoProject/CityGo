package com.citygo.model;
import jakarta.persistence.*;

/*
 * =============================================================
 * Tren.java — Tren Alt Sınıfı (INHERITANCE + POLYMORPHISM)
 * =============================================================
 * Sorumlu: Mert
 *
 * UlasimAraci abstract sınıfından miras alan somut sınıf.
 *
 * OOP Prensipleri:
 * - INHERITANCE: UlasimAraci'ndan extends eder
 * - POLYMORPHISM (Override): hesaplaToplamFiyat() metodunu geçersiz kılar
 *
 * Anotasyonlar:
 * - @Entity
 * - @DiscriminatorValue("TREN")
 *
 * Ek Alanlar (private):
 * - vagonTipi: String → "EKONOMI" veya "BUSINESS"
 * - hatTipi: String   → "YHT" (Yüksek Hızlı Tren) veya "NORMAL"
 *
 * Override Edilecek Metotlar:
 * - hesaplaToplamFiyat(double temelFiyat): double
 *     → Business vagon ise temelFiyat * 1.5
 *     → YHT ise ek %20 hız farkı ücreti
 *     → Ekonomi + Normal ise temelFiyat aynen döner
 *
 * - getAracTipi(): String
 *     → return "TREN"
 *
 * Getter/Setter:
 * - Ek alanlar için getter/setter metotları
 */

@Entity  // Veritabanını tablo ile eşleştirme 
@DiscriminatorValue("TREN") // SuperClass'da yazdığımız ayırt edici sutünun değeri

public class Tren extends UlasimAraci{

    private String vagonTipi;
    private String hatTipi;
    
    // getter/setter'lar

    public String getVagonTipi() {return vagonTipi;}
    public void setVagonTipi(String vagonTipi) {this.vagonTipi = vagonTipi;}

    public String getHatTipi() {return hatTipi;}
    public void setHatTipi(String hatTipi) {this.hatTipi = hatTipi;}

    @Override 
    public String getAracTipi() {return "TREN";} // Araç tipi döndürme


    // VagonTipi ve HatTipi Kontrolleriyle son fiyat hesaplama
    @Override
    public double hesaplaToplamFiyat(double temelFiyat) {
        if ( vagonTipi.equals("BUSINESS") && hatTipi.equals("YHT")) 
            { 
                return (temelFiyat * 1.5)*1.2;
            }

        else if (vagonTipi.equals("EKONOMI") && hatTipi.equals("YHT"))
            {
                return (temelFiyat)*1.2;
            }

        else if ( vagonTipi.equals("BUSINESS") && hatTipi.equals("NORMAL"))
            {
                return (temelFiyat)*1.5;
            }

        else {return temelFiyat;}
    }

}