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

@Entity
@DiscriminatorValue("OTOBUS")
public class Otobus extends UlasimAraci {

    private boolean ikramVar;
    private double ikramBedeli;

    public void setIkramVar(boolean ikramVar) {this.ikramvar = ikramVar;}
    public boolean getIkramVar() {return ikramvar;}
    public void setIkramBedeli(double ikramBedeli) {this.ikramBedeli = ikramBedeli;} 
    public double getIkramBedeli () {return ikramBedeli;}

    @Override 
    public double hesaplaToplamFiyat(double temelFiyat) 
    {
        if(ikramVar ==  true) 
            {
                return ikramBedeli + temelFiyat;
            }
        else {return temelFiyat;}
    }

    @Override
    public String getAracTipi() {return "OTOBUS";}
}