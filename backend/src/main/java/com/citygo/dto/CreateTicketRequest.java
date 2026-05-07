package com.citygo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateTicketRequest(
        @NotNull(message = "Yolcu ID boş olamaz")
        Long yolcuId,

        @NotNull(message = "Sefer ID boş olamaz")
        Long seferId,

        @NotNull(message = "Koltuk numarası boş olamaz")
        @Min(value = 1, message = "Koltuk numarası 1 veya daha büyük olmalıdır")
        Integer koltukNo
) {
}
