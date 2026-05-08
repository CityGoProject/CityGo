package com.citygo.service;

import com.citygo.exception.SeferBulunamadiException;
import com.citygo.model.Otobus;
import com.citygo.model.Sefer;
import com.citygo.model.Ucak;
import com.citygo.repository.SeferRepository;
import com.citygo.repository.UlasimAraciRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AramaServiceTest {

    @Mock
    private SeferRepository seferRepository;

    @Mock
    private UlasimAraciRepository ulasimAraciRepository;

    @InjectMocks
    private AramaService aramaService;

    @Test
    void tarihliAramaTumGunAraliginiKullanir() {
        Sefer sefer = new Sefer();
        LocalDate tarih = LocalDate.of(2026, 4, 24);
        when(seferRepository.findByKalkisNoktasiAndVarisNoktasiAndKalkisZamaniBetween(
            "Istanbul",
            "Ankara",
            tarih.atStartOfDay(),
            tarih.atTime(LocalTime.MAX)
        )).thenReturn(List.of(sefer));

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
    void secilenGundeSeferYoksaDemoSeferOlusturur() {
        LocalDate tarih = LocalDate.of(2026, 6, 1);
        Otobus otobus = new Otobus(null, "Metro", "Test", 40, 500.0, false, 0.0);

        when(seferRepository.findByKalkisNoktasiAndVarisNoktasiAndKalkisZamaniBetween(
            "İstanbul",
            "Ankara",
            tarih.atStartOfDay(),
            tarih.atTime(LocalTime.MAX)
        )).thenReturn(List.of());
        when(ulasimAraciRepository.findAll()).thenReturn(List.of(otobus));
        when(seferRepository.save(any(Sefer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Duzeltme: Demo verisi sinirli tarih araligindaysa ileri tarih aramasi
        // tamamen bos kalmasin, secilen tarihe sefer ve koltuk uretilsin.
        List<Sefer> sonuc = aramaService.ara("İstanbul", "Ankara", tarih);

        assertEquals(1, sonuc.size());
        assertSame(otobus, sonuc.get(0).getArac());
        assertEquals(tarih.atTime(10, 0), sonuc.get(0).getKalkisZamani());
        assertEquals(40, sonuc.get(0).getKoltuklar().size());
        verify(seferRepository).save(any(Sefer.class));
    }

    @Test
    void secilenAracTipiYoksaOAracTipiIcinDemoSeferOlusturur() {
        LocalDate tarih = LocalDate.of(2026, 6, 1);
        Otobus otobus = new Otobus(null, "Metro", "Test", 40, 500.0, false, 0.0);
        Ucak ucak = new Ucak(null, "THY", "Boeing", 180, 1500.0, 0.15, "IST");

        when(seferRepository.findByKalkisNoktasiAndVarisNoktasiAndKalkisZamaniBetween(
            "İstanbul",
            "Ankara",
            tarih.atStartOfDay(),
            tarih.atTime(LocalTime.MAX)
        )).thenReturn(List.of());
        when(ulasimAraciRepository.findAll())
            .thenReturn(List.of(otobus))
            .thenReturn(List.of(otobus, ucak));
        when(seferRepository.save(any(Sefer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Duzeltme: Kullanici ozellikle ucak sectiyse mevcut demo seferleri otobus
        // olsa bile ucak seferi uretilsin.
        List<Sefer> sonuc = aramaService.ara("İstanbul", "Ankara", tarih, "UCAK");

        assertEquals(1, sonuc.size());
        assertSame(ucak, sonuc.get(0).getArac());
        verify(seferRepository, times(2)).save(any(Sefer.class));
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

    @Test
    void ayniKalkisVeVarisIcinAramaReddedilir() {
        // Duzeltme: Kullanici ayni sehir aradiginda repository'e anlamsiz sorgu gitmesin.
        assertThrows(IllegalArgumentException.class, () -> aramaService.ara("Ankara", "ankara"));
    }
}
