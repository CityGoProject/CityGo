    package com.citygo.model;
    import jakarta.persistence.*;

    /*
    * =============================================================
    * Ucak.java — Uçak Alt Sınıfı (INHERITANCE + POLYMORPHISM)
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
    * - @DiscriminatorValue("UCAK") (eğer SINGLE_TABLE stratejisi kullanılırsa)
    *
    * Ek Alanlar (private):
    * - havaalaniVergiOrani: double → Havaalanı vergi oranı (ör: 0.15 = %15)
    * - havaalani: String           → Kalkış havaalanı kodu (ör: "IST", "SAW")
    *
    * Override Edilecek Metotlar:
    * - hesaplaToplamFiyat(double temelFiyat): double
    *     → return temelFiyat + (temelFiyat * havaalaniVergiOrani)
    *     → Uçak biletine havaalanı vergisi eklenir
    *
    * - getAracTipi(): String
    *     → return "UCAK"
    *
    * Getter/Setter:
    * - Ek alanlar için getter/setter metotları
    */

    @Entity
    @DiscriminatorValue("UCAK") // SuperClass'da yazdığımız ayırt edici sutünun değeri
    public class Ucak extends UlasimAraci{
        
        @Column(name="havaalani_vergi_orani")        
        private double havaalaniVergiOrani;
        @Column(name="havaalani")
        private String havaalani;

        // getter/setter'lar

        public double getHavaalaniVergiOrani() {return havaalaniVergiOrani;}
        public void setHavaalaniVergiOrani(double havaalaniVergiOrani) {this.havaalaniVergiOrani = havaalaniVergiOrani;}

        public String getHavaalani() {return havaalani;}
        public void setHavaalani(String havaalin) {this.havaalani = havaalani;}

        
        @Override
        public String getAracTipi() {return "UCAK";}

        @Override
        public double hesaplaToplamFiyat(double temelFiyat) {
            return temelFiyat + (temelFiyat + havaalaniVergiOrani);
        }
        
    }