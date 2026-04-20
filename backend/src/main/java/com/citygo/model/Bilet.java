package com.citygo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

   @Entity //veritabanında biletler tablosuna eşlenir.
   @Table(name = "biletler")

   public class Bilet{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // bir yolcunun birden fazla bileti olabilir.
    @JoinColumn(name = "yolcu_id", nullable = false)
    private Yolcu yolcu;

    @ManyToOne(fetch = FetchType.LAZY) //bir seferde birçok bilet satılabilir.
    @JoinColumn(name = "sefer_id", nullable = false)
    private Sefer sefer;

    @OneToOne(fetch = FetchType.LAZY) //her bilet tek bir spesifik koltuğa atanır.
    @JoinColumn(name = "koltuk_id", nullable = false)
    private Koltuk koltuk;

    @Column(nullable = false)
    private double odenenTutar; //biletin toplam satış bedeli.

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BiletDurumu durum;

    @Column(nullable = false)
    private LocalDateTime olusturmaTarihi;

    @PrePersist //veritabanına yeni bilet kaydı eklenmeden hemen önce bu metod tetiklenir ve biletin satın alınma anını sistem saatiyle kaydeder.
    protected void onCreate(){
        this.olusturmaTarihi = LocalDateTime.now();
        if(this.durum == null){
            this.durum = BiletDurumu.AKTIF;
        }
    }

    public void setId(Long id){
        this.id = id;
    }
    public Long getId(){
        return this.id;
    }

    public void setYolcu(Yolcu yolcu){
        this.yolcu = yolcu;
    }
    public Yolcu getYolcu(){
        return this.yolcu;
    }

    public void setSefer(Sefer sefer){
        this.sefer = sefer;
    }
    public Sefer getSefer(){
        return this.sefer;
    }

    public void setKoltuk(Koltuk koltuk){
        this.koltuk = koltuk;
    }
    public Koltuk getKoltuk(){
        return this.koltuk;
    }

    public void setOdenenTutar(double odenenTutar){
        this.odenenTutar = odenenTutar;
    }
    public double getOdenenTutar(){
        return this.odenenTutar;
    }

    public void setDurum(BiletDurumu durum){
        this.durum = durum;
    }
    public BiletDurumu getDurum(){
        return this.durum;
    }

    public void setOlusturmaTarihi(LocalDateTime olusturmaTarihi){
        this.olusturmaTarihi = olusturmaTarihi;
    }
    public LocalDateTime getOlusturmaTarihi(){
        return this.olusturmaTarihi;
    }

   }
/*
 * =============================================================
 * Bilet.java — Bilet Entity Sınıfı
 * =============================================================
 * Sorumlu: Elif Feyza
 *
 * Bir yolcunun bir seferdeki koltuk rezervasyonunu temsil eder.
 *
 * Anotasyonlar:
 * - @Entity
 *
 * Alanlar (private):
 * - id: Long                    → @Id, @GeneratedValue
 * - yolcu: Yolcu                → @ManyToOne — Bilet sahibi yolcu
 * - sefer: Sefer                → @ManyToOne — Hangi sefer
 * - koltuk: Koltuk              → @OneToOne  — Hangi koltuk
 * - odenenTutar: double         → Ödenen toplam tutar (hesaplaToplamFiyat ile)
 * - olusturmaTarihi: LocalDate  → Bilet oluşturulma tarihi
 * - durum: BiletDurumu          → @Enumerated(EnumType.STRING)
 *                                  AKTIF / IPTAL_EDILDI / KULLANILDI
 *
 * Getter/Setter:
 * - Tüm alanlar için getter/setter metotları
 *
 * Not: olusturmaTarihi alanına @PrePersist ile otomatik tarih atanabilir.
 *      yolcu alanı @JsonBackReference ile sonsuz döngü engellenmelidir.
 */
