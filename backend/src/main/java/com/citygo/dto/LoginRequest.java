package com.citygo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Email(message = "Geçerli bir email adresi giriniz.")
        @NotBlank(message = "Email boş olamaz")
        String email,

        @NotBlank(message = "Şifre boş olamaz")
        String sifre
) {
}
