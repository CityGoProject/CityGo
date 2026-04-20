package com.citygo.model;

/*
 * =============================================================
 * Koltuk.java — Koltuk Entity Sınıfı
 * =============================================================
 * Sorumlu: Mert
 *
 * Bir seferdeki tek bir koltuğu temsil eder.
 *
 * Anotasyonlar:
 * - @Entity
 *
 * Alanlar (private):
 * - id: Long             → @Id, @GeneratedValue
 * - koltukNo: int        → Koltuk numarası (1, 2, 3, ...)
 * - dolu: boolean        → Koltuk dolu mu? (varsayılan: false)
 * - tip: KoltukTipi      → @Enumerated(EnumType.STRING) — STANDART veya PREMIUM
 * - sefer: Sefer          → @ManyToOne — Bu koltuğun ait olduğu sefer
 *
 * Getter/Setter:
 * - Tüm alanlar için getter/setter metotları
 *
 * Not: Roadmap'ta "dpieces" yazıyor ama bu alan "dolu" (occupied) olmalı.
 */

@Entity
@Table(name = "koltuklar")
public class Koltuk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "koltuk_no", nullable = false)
    private int koltukNo;

    @Column(name = "dolu", nullable = false)
    private boolean dolu = false; 

    @Enumerated(EnumType.STRING)
    @Column(name = "tip", nullable = false)
    private KoltukTipi tip;

    @ManyToOne
    @JoinColumn(name = "sefer_id", nullable = false) 
    private Sefer sefer;


    public Long getId() {return id; }
    public void setId(Long id) {this.id = id;}

    public int getKoltukNo() {return koltukNo;}

    public void setKoltukNo(int koltukNo) {this.koltukNo = koltukNo;}

    public boolean isDolu() {return dolu;}
    public void setDolu(boolean dolu) {this.dolu = dolu;}

    public KoltukTipi getTip() {return tip;}
    public void setTip(KoltukTipi tip) {this.tip = tip;}

    public Sefer getSefer() {return sefer;}
    public void setSefer(Sefer sefer) {this.sefer = sefer;}
}