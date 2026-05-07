package com.citygo.dto;

import com.citygo.model.Admin;
import com.citygo.model.Kullanici;

public record AuthResponse(
        Long id,
        String ad,
        String soyad,
        String email,
        String telefon,
        String rol,
        String yetki
) {
    public static AuthResponse from(Kullanici kullanici) {
        // Duzeltme: Login/register cevabinda entity ve sifre hash'i donmek yerine
        // sadece frontend'in ihtiyaci olan guvenli kullanici bilgilerini donuyoruz.
        String rol = kullanici instanceof Admin ? "ADMIN" : "YOLCU";
        String yetki = kullanici instanceof Admin admin ? admin.getYetki() : null;

        return new AuthResponse(
                kullanici.getId(),
                kullanici.getAd(),
                kullanici.getSoyad(),
                kullanici.getEmail(),
                kullanici.getTelefon(),
                rol,
                yetki
        );
    }
}
