package com.citygo.service;

import com.citygo.exception.SeferBulunamadiException;
import com.citygo.model.Otobus;
import com.citygo.model.Sefer;
import com.citygo.repository.SeferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AramaServiceTest {

    @Mock
    private SeferRepository seferRepository;

    @InjectMocks
    private AramaService aramaService;

    @Test
    void tarihliAramaTumGunAraliginiKullanir() {
        LocalDate tarih = LocalDate.of(2026, 4, 24);
        when(seferRepository.findByKalkisNoktasiAndVarisNoktasiAndKalkisZamaniBetween(
            "Istanbul",
            "Ankara",
            tarih.atStartOfDay(),
            tarih.atTime(LocalTime.MAX)
        )).thenReturn(List.of());

        aramaService.ara("Istanbul", "Ankara", tarih);

        ArgumentCaptor<LocalDateTime> baslangicCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        ArgumentCaptor<LocalDateTime> bitisCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        verify(seferRepository).findByKalkisNoktasiAndVarisNoktasiAndKalkisZamaniBetween(
            org.mockito.ArgumentMatchers.eq("Istanbul"),
            org.mockito.ArgumentMatchers.eq("Ankara"),
            baslangicCaptor.capture(),
            bitisCaptor.capture()
        );

        assertEquals(tarih.atStartOfDay(), baslangicCaptor.getValue());
        assertEquals(tarih.atTime(LocalTime.MAX), bitisCaptor.getValue());
    }

    @Test
    void tipliAramaAracTipiniBuyukKucukHarfDuyarsizFiltreler() {
        Sefer otobusSeferi = new Sefer();
        Otobus otobus = new Otobus();
        otobusSeferi.setArac(otobus);

        Sefer aracsizSefer = new Sefer();

        LocalDate tarih = LocalDate.of(2026, 4, 24);
        when(seferRepository.findByKalkisNoktasiAndVarisNoktasiAndKalkisZamaniBetween(
            "Istanbul",
            "Ankara",
            tarih.atStartOfDay(),
            tarih.atTime(LocalTime.MAX)
        )).thenReturn(List.of(otobusSeferi, aracsizSefer));

        List<Sefer> sonuc = aramaService.ara("Istanbul", "Ankara", tarih, "otobus");

        assertEquals(1, sonuc.size());
        assertSame(otobusSeferi, sonuc.get(0));
    }

    @Test
    void seferDetayBulunamazsaAlanExceptionFirlatir() {
        when(seferRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(SeferBulunamadiException.class, () -> aramaService.seferDetay(99L));
    }
}
