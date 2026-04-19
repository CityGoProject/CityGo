package com.citygo.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ADMIN") // veri tabanında rol kolonunda admin yazcak

public class Admin extends Kullanici {

    private String yetki; // süper admin veya moderatör gibi..

    public String getYetki() {
        return yetki;
    }

    public void setYetki(String yetki) {
        this.yetki = yetki;
    }

}

/*
 * =============================================================
 * Admin.java — Admin Alt Sınıfı (INHERITANCE)
 * =============================================================
 * Sorumlu: Muhammed
 *
 * Kullanici abstract sınıfından miras alan, sistemi yöneten
 * admin kullanıcılarını temsil eder.
 *
 * Anotasyonlar:
 * - @Entity
 * - @DiscriminatorValue("ADMIN")
 *
 * Ek Alanlar (private):
 * - yetki: String → Admin yetki seviyesi (ör: "SUPER_ADMIN", "MODERATOR")
 *
 * Getter/Setter:
 * - Ek alanlar için getter/setter metotları
 */
