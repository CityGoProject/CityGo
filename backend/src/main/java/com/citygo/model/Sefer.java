package com.citygo.model;
import jakarta.persistence.*;

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

@Entity
@Table(name = "seferler")
public class Sefer {

    @id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "arac_id")
    private UlasimAraci arac;
    @Column(name = "kalkis_noktasi", nullable = false)
    private String kalkisNoktasi;
    @Column(name = "varis_noktasi", nullable = false)
    private String varisNoktasi;

    @Column(name = "kalkis_zamani", nullable = false)
    private String kalkisZamani;
    @Column(name = "varis_zamani", nullable = false)
    private String varisZamani;

    @OneToMany(mappedBy = "sefer", cascade = CascadeType.ALL)
    private List<Koltuk> koltuklar;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public UlasimAraci getArac() {return arac;}
    public void setArac(UlasimAraci arac) {this.arac = arac;}

    public String getKalkisNoktasi() {return kalkisNoktasi;}
    public void setKalkisNoktasi(String kalkisNoktasi) {this.kalkisNoktasi = kalkisNoktasi;}

    public String getVarisNoktasi() {return varisNoktasi;}
    public void setVarisNoktasi(String varisNoktasi) {this.varisNoktasi = varisNoktasi;}
    
    public String getKalkisNoktasi() {return kalkisNoktasi;}
    public void setKalkisNoktasi(String kalkisNoktasi) {this.kalkisNoktasi = kalkisNoktasi;}

    public String getVarisNoktasi() {return varisNoktasi;}
    public void setVarisNoktasi(String varisNoktasi) {this.varisNoktasi = varisNoktasi;}
    
    public List<Koltuk> getKoltuklar() {return koltuklar;}
    public void setKoltuklar(List<Koltuk> koltuklar) {this.koltuklar = koltuklar;}

    // Koltukları Otamatik Doldurma Fonksiyonu Oluşturcağım

}
