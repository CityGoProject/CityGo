package com.citygo.model;
import jakarta.persistence.*;

/*
 * =============================================================
 * Otobus.java — Otobüs Alt Sınıfı (INHERITANCE + POLYMORPHISM)
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
 * - @DiscriminatorValue("OTOBUS")
 *
 * Ek Alanlar (private):
 * - ikramVar: boolean    → İkram dahil mi? (çay, kahve, atıştırmalık)
 * - ikramBedeli: double  → İkram bedeli (TL), ikramVar true ise geçerli
 *
 * Override Edilecek Metotlar:
 * - hesaplaToplamFiyat(double temelFiyat): double
 *     → if (ikramVar) return temelFiyat + ikramBedeli
 *     → else return temelFiyat
 *
 * - getAracTipi(): String
 *     → return "OTOBUS"
 *
 * Getter/Setter:
 * - Ek alanlar için getter/setter metotları
 */

@Entity // Veritabanını tablo ile eşleştirme 
@DiscriminatorValue("OTOBUS") // SuperClass'da yazdığımız ayırt edici sutünun değeri

public class Otobus extends UlasimAraci {

    private boolean ikramVar;
    private double ikramBedeli;

    // getter/setter'lar

    public void setIkramVar(boolean ikramVar) {this.ikramVar = ikramVar;}
    public boolean getIkramVar() {return ikramVar;}
    
    public void setIkramBedeli(double ikramBedeli) {this.ikramBedeli = ikramBedeli;} 
    public double getIkramBedeli () {return ikramBedeli;}

    // İkram kontrolü ile son fiyat hesaplama
    @Override 
    public double hesaplaToplamFiyat(double temelFiyat) 
    {
        if(ikramVar) 
            {
                return ikramBedeli + temelFiyat;
            }
        else {return temelFiyat;}
    }

    @Override
    public String getAracTipi() {return "OTOBUS";} // Araç tipi döndürme
}