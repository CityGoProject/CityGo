package com.citygo.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TripRequest(
        @NotNull(message = "Araç ID boş olamaz")
        Long aracId,

        @NotBlank(message = "Kalkış noktası boş olamaz")
        String kalkisNoktasi,

        @NotBlank(message = "Varış noktası boş olamaz")
        String varisNoktasi,

        @NotNull(message = "Kalkış zamanı boş olamaz")
        @Future(message = "Kalkış zamanı gelecekte olmalıdır")
        LocalDateTime kalkisZamani,

        @NotNull(message = "Varış zamanı boş olamaz")
        @Future(message = "Varış zamanı gelecekte olmalıdır")
        LocalDateTime varisZamani
) {
}
