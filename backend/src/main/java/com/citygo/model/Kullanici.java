package com.citygo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "kullanicilar")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Yolcu ve Admin ayni tabloda tutuluyor
@DiscriminatorColumn(name = "rol") // tabloda "rol" kolonu ile YOLCU/ADMIN ayrimi yapiliyor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public abstract class Kullanici {
    // Yolcu ve Admin bu siniftan miras alacak, o yuzden abstract

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id otomatik artiyor (1, 2, 3...)
    private Long id;

    @NotBlank(message = "Ad boş olamaz")
    private String ad;

    @NotBlank(message = "Soyad boş olamaz")
    private String soyad;

    @Email(message = "Geçerli bir email adresi giriniz.")
    @Column(unique = true) // ayni emailden iki tane olamaz
    @NotBlank(message = "email boş olamaz")
    private String email;

    @NotBlank(message = "Şifre boş olamaz")
    @JsonIgnore // Duzeltme: Kullanici entity'si donse bile sifre hash'i JSON'a sizmasin.
    private String sifre;

    @Column(unique = true) // Duzeltme: iki kullanici ayni telefon numarasi ile kayit olamaz.
    private String telefon; // zorunlu degil, opsiyonel

    public Kullanici(Long id, String ad, String soyad, String email, String sifre, String telefon) {
        super();
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.email = email;
        this.sifre = sifre;
        this.telefon = telefon;
    }

    public Kullanici() {

    }

    // --- Getter ve Setter'lar ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

}

/*
 * =============================================================
 * Kullanici.java — Abstract Kullanıcı Sınıfı (INHERITANCE + ABSTRACTION)
 * =============================================================
 * Sorumlu: Muhammed
 *
 * Yolcu ve Admin sınıflarının ortak özelliklerini barındıran ABSTRACT sınıf.
 *
 * OOP Prensipleri:
 * - INHERITANCE: Yolcu ve Admin bu sınıftan miras alacak
 * - ABSTRACTION: Ortak kullanıcı yapısını soyutlar
 * - ENCAPSULATION: Tüm alanlar private
 *
 * Anotasyonlar:
 * - @Entity
 * - @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
 * - @DiscriminatorColumn(name = "rol")
 *
 * Alanlar (private):
 * - id: Long → @Id, @GeneratedValue — Birincil anahtar
 * - ad: String → Kullanıcının adı (@NotBlank)
 * - soyad: String → Kullanıcının soyadı (@NotBlank)
 * - email: String → Email adresi (@Email, @Column(unique = true))
 * - sifre: String → Şifre (hashlenmiş olarak saklanacak)
 * - telefon: String → Telefon numarası
 *
 * Getter/Setter:
 * - Tüm alanlar için public getter ve setter metotları
 */
