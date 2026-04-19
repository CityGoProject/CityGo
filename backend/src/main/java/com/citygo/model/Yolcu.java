package com.citygo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("YOLCU")

public class Yolcu extends Kullanici {

    private String tcNo;

    @OneToMany(mappedBy = "yolcu", cascade = CascadeType.ALL)

    @JsonIgnore
    private List<Bilet> biletler = new ArrayList<>();

    public String getTcNo() {
        return tcNo;
    }

    public void setTcNo(String tcNo) {
        this.tcNo = tcNo;
    }

    public List<Bilet> getBiletler() {
        return biletler;
    }

    public void setBiletler(List<Bilet> biletler) {
        this.biletler = biletler;
    }

}

/*
 * =============================================================
 * Yolcu.java — Yolcu Alt Sınıfı (INHERITANCE)
 * =============================================================
 * Sorumlu: Muhammed
 *
 * Kullanici abstract sınıfından miras alan, sisteme kayıt olan
 * normal kullanıcıları (yolcuları) temsil eder.
 *
 * Anotasyonlar:
 * - @Entity
 * - @DiscriminatorValue("YOLCU")
 *
 * Ek Alanlar (private):
 * - tcNo: String → T.C. Kimlik Numarası (11 haneli)
 * - biletler: List<Bilet> → @OneToMany ilişki — Yolcunun biletleri
 * (mappedBy = "yolcu")
 *
 * Getter/Setter:
 * - Ek alanlar için getter/setter metotları
 *
 * Not: biletler alanı @JsonIgnore veya @JsonManagedReference ile
 * sonsuz döngü problemi engellenmelidir.
 */
