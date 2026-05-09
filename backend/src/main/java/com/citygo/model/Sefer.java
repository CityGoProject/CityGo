package com.citygo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

/*
 * =============================================================
 * Sefer.java — Sefer Entity Sınıfı
 * =============================================================
 * Sorumlu: Mert
 *
 * Bir ulaşım aracının belirli bir tarih ve güzergahta
 * gerçekleştireceği seferi temsil eder.
 *
 * Anotasyonlar:
 * - @Entity
 *
 * Alanlar (private):
 * - id: Long                    → @Id, @GeneratedValue
 * - arac: UlasimAraci           → @ManyToOne — Bu seferde kullanılan araç
 * - kalkisNoktasi: String       → Kalkış şehri/istasyonu (ör: "İstanbul")
 * - varisNoktasi: String        → Varış şehri/istasyonu (ör: "Ankara")
 * - kalkisZamani: LocalDateTime → Kalkış tarihi ve saati
 * - varisZamani: LocalDateTime  → Tahmini varış tarihi ve saati
 * - koltuklar: List<Koltuk>     → @OneToMany — Bu sefere ait koltuklar
 *                                  (mappedBy = "sefer", cascade = ALL)
 *
 * Getter/Setter:
 * - Tüm alanlar için getter/setter metotları
 *
 * Not: koltuklar listesi sefer oluşturulurken otomatik olarak
 *      aracın kapasitesi kadar Koltuk nesnesi ile doldurulmalıdır.
 */

// Veritabanını tablo ile eşleştirme ve Tablo ismini ayarlama

@Entity
@Table(name = "seferler")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Sefer {

    // Her kayıt için otomatik artarak üretilen birincil anahtar
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Duzeltme: Frontend seferle birlikte arac bilgisini de kullaniyor, bu alan
    // JSON'da gelmeli.
    // Sonsuz dongu riski koltuk tarafinda sefer alanini gizleyerek kontrol
    // ediliyor.
    @ManyToOne
    @JoinColumn(name = "arac_id")
    private UlasimAraci arac;

    @Column(nullable = false)
    private String kalkisNoktasi;
    @Column(nullable = false)
    private String varisNoktasi;

    @Column(nullable = false)
    private LocalDateTime kalkisZamani;
    @Column(nullable = false)
    private LocalDateTime varisZamani;

    // mappedBy = ilişkiyi Koltuk.java'daki "sefer" alanı yönetiyor
    // cascade = ALL → sefere ne olursa koltuk'lara da yansı
    @JsonIgnore // Duzeltme: Sefer listelerinde binlerce koltuk JSON'a eklenip cevabi sisirmesin.
    @OneToMany(mappedBy = "sefer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Koltuk> koltuklar;

    public Sefer() {
    }

    public Sefer(Long id, UlasimAraci arac, String kalkisNoktasi, String varisNoktasi, LocalDateTime kalkisZamani,
            LocalDateTime varisZamani, List<Koltuk> koltuklar) {
        this.id = id;
        this.arac = arac;
        this.kalkisNoktasi = kalkisNoktasi;
        this.varisNoktasi = varisNoktasi;
        this.kalkisZamani = kalkisZamani;
        this.varisZamani = varisZamani;
        this.koltuklar = koltuklar;
    }

    // getter/setter'lar

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UlasimAraci getArac() {
        return arac;
    }

    public void setArac(UlasimAraci arac) {
        this.arac = arac;
    }

    public String getKalkisNoktasi() {
        return kalkisNoktasi;
    }

    public void setKalkisNoktasi(String kalkisNoktasi) {
        this.kalkisNoktasi = kalkisNoktasi;
    }

    public String getVarisNoktasi() {
        return varisNoktasi;
    }

    public void setVarisNoktasi(String varisNoktasi) {
        this.varisNoktasi = varisNoktasi;
    }

    public LocalDateTime getKalkisZamani() {
        return kalkisZamani;
    }

    public void setKalkisZamani(LocalDateTime kalkisZamani) {
        this.kalkisZamani = kalkisZamani;
    }

    public LocalDateTime getVarisZamani() {
        return varisZamani;
    }

    public void setVarisZamani(LocalDateTime varisZamani) {
        this.varisZamani = varisZamani;
    }

    public List<Koltuk> getKoltuklar() {
        return koltuklar;
    }

    public void setKoltuklar(List<Koltuk> koltuklar) {
        this.koltuklar = koltuklar;
    }

    // Sefer oluşturulurken aracın kapasitesi kadar otomatik koltuk üretir
    public void koltuklariOlustur() {

        // Araç atanmadan bu metot çağrılırsa hata vermemesi için kontrol
        if (arac == null) {
            throw new IllegalStateException("Önce araç atanmalidir! setArac()");
        }

        this.koltuklar = new ArrayList<>();

        for (int i = 1; i <= arac.getKapasite(); i++) {
            Koltuk koltuk = new Koltuk();
            koltuk.setKoltukNo(i);
            koltuk.setDolu(false); // başlangıçta hepsi boş
            koltuk.setTip(KoltukTipi.STANDART); // varsayılan tip
            koltuk.setSefer(this); // bu koltuğun seferi = bu sefer
            this.koltuklar.add(koltuk);
        }
    }

    // Duzeltme: Frontend'in hesaplanmis son fiyati (vergi, ikram dahil) gormesi saglanir.
    public double getBiletFiyati() {
        if (arac == null) {
            return 0;
        }
        return arac.hesaplaToplamFiyat(arac.getBiletFiyati());
    }

}
