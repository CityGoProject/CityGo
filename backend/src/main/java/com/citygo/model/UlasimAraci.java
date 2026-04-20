package com.citygo.model;
import jakarta.persistence.*;

@Entity // Veritabanını tablo ile eşleştirme
@Table(name="ulasimaraci") // Tablo ismini ayarlıyorum 
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Tüm alt sınıfları tek tablo ile saklar
@DiscriminatorColumn(name="arac_tipi") // Tabloda arac_tipi sutunu ile ayırt eder.
public abstract class  UlasimAraci {

    // Otomatik artan id 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kolon isimleri atama (name) ve her araçta yazılması zorun hale getirme (nullable = false)
    @Column(name="firma",nullable = false)
    private String firma;
    
    @Column(name="model",nullable = false)
    private String model;       
    
    @Column(name="kapasite",nullable = false)
    private int kapasite;
    
    @Column(name="bilet_fiyati",nullable = false)
    private double biletFiyati;
    
    //getter/setter'lar
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    
    public String getFirma () {return firma;}
    public void setFirma(String firma) {this.firma = firma;}
    
    public String getModel() {return model;}
    public void setModel(String model) {this.model = model;}
    
    public int getKapasite() {return kapasite;}
    public void setKapasite(int kapasite) {this.kapasite = kapasite;}

    public double getBiletFiyati() {return biletFiyati;}
    public void setBiletFiyati(double biletFiyati) {this.biletFiyati = biletFiyati;}

    // Alt sınıflara kendi fiyatlarını hesaplama implement etme
    public abstract double hesaplaToplamFiyat(double temelFiyat);
    // Alt sınıflar kendi araç tipini döndürür ("UCAK","TREN","OTOBUS")
    public abstract String getAracTipi();
    
} 




/*
 * =============================================================
 * UlasimAraci.java — Abstract Üst Sınıf (INHERITANCE + ABSTRACTION)
 * =============================================================
 * Sorumlu: Mert
 *
 * Tüm ulaşım araçlarının (Uçak, Tren, Otobüs) ortak özelliklerini
 * barındıran ABSTRACT sınıf. Doğrudan nesne oluşturulamaz.
 *
 * OOP Prensipleri:
 * - INHERITANCE: Ucak, Tren, Otobus bu sınıftan miras alacak
 * - ABSTRACTION: abstract metotlar alt sınıflarda implement edilecek
 * - ENCAPSULATION: Tüm alanlar private, getter/setter ile erişim
 *
 * Anotasyonlar:
 * - @Entity
 * - @Inheritance(strategy = InheritanceType.SINGLE_TABLE) veya JOINED
 * - @DiscriminatorColumn (SINGLE_TABLE kullanılırsa)
 *
 * Alanlar (private):
 * - id: Long            → @Id, @GeneratedValue — Birincil anahtar
 * - firma: String       → Araç firması (ör: "THY", "TCDD", "Metro Turizm")
 * - model: String       → Araç modeli
 * - kapasite: int       → Toplam koltuk sayısı
 * - biletFiyati: double → Temel bilet fiyatı (TL)
 *
 * Abstract Metotlar:
 * - hesaplaToplamFiyat(double temelFiyat): double
 *     → Her alt sınıf kendi fiyat hesaplamasını yapacak (POLYMORPHISM - Override)
 *     → Uçak: temelFiyat + havaalanı vergisi
 *     → Tren: temelFiyat + vagon tipi farkı
 *     → Otobüs: temelFiyat + ikram bedeli
 *
 * - getAracTipi(): String
 *     → "UCAK", "TREN" veya "OTOBUS" döndürecek
 *
 * Getter/Setter:
 * - Tüm alanlar için public getter ve setter metotları yazılacak
 */
