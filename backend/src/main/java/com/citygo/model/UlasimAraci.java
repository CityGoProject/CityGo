package com.citygo.model;
import jakarta.persistence.*;


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


// Veritabanını tablo ile eşleştirme ve Tablo ismini ayarlama
@Entity 
@Table(name="ulasimaraci") 
 
// Inheritance Stratejisi : Tüm alt sınıfları tek tablo ile saklama
// Tabloda arac_tipi sutunu ile ayırt eder.
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) 
@DiscriminatorColumn(name="arac_tipi") 

public abstract class  UlasimAraci {

    // Her kayıt için otomatik artarak üretilen birincil anahtar
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable = false → veri tabanında boş bırakılamaz hale getirme

    @Column(nullable = false)
    private String firma;
    
    @Column(nullable = false)
    private String model;       
    
    @Column(nullable = false)
    private int kapasite;
    
    @Column(nullable = false)
    private double biletFiyati;
    
    // getter/setter'lar
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

    // --- Abstract Metodlar --- 

    // Her Ulaşım Türünün kendi fiyatını hesaplaması (Uçak → havaalin vergisi, Tren → Business ve YHT, Otobüs → İkram Bedeli)
    public abstract double hesaplaToplamFiyat(double temelFiyat);
    // Alt sınıflar kendi araç tipini döndürür ("UCAK","TREN","OTOBUS")
    public abstract String getAracTipi();
    
} 



