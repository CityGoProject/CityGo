package com.citygo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
        @NotBlank(message = "Ad boş olamaz")
        String ad,

        @NotBlank(message = "Soyad boş olamaz")
        String soyad,

        @Email(message = "Geçerli bir email adresi giriniz.")
        @NotBlank(message = "Email boş olamaz")
        String email,

        @NotBlank(message = "Şifre boş olamaz")
        String sifre,

        @NotBlank(message = "Telefon boş olamaz")
        String telefon,

        @NotBlank(message = "TC kimlik no boş olamaz")
        @Pattern(regexp = "\\d{11}", message = "TC kimlik no 11 haneli olmalıdır")
        String tcNo
) {
}
