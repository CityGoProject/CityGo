package com.citygo.service;

import com.citygo.exception.KoltukBulunamadiException;
import com.citygo.model.Admin;
import com.citygo.model.Sefer;
import com.citygo.model.Yolcu;
import com.citygo.repository.BiletRepository;
import com.citygo.repository.KoltukRepository;
import com.citygo.repository.KullaniciRepository;
import com.citygo.repository.SeferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RezervasyonServiceTest {

    @Mock
    private BiletRepository biletRepository;

    @Mock
    private KoltukRepository koltukRepository;

    @Mock
    private SeferRepository seferRepository;

    @Mock
    private KullaniciRepository kullaniciRepository;

    @InjectMocks
    private RezervasyonService rezervasyonService;

    @Test
    void yolcuOlmayanKullaniciIcinAcikBadRequestHatasiVerir() {
        Admin kullanici = new Admin();
        when(kullaniciRepository.findById(1L)).thenReturn(Optional.of(kullanici));

        assertThrows(IllegalArgumentException.class, () -> rezervasyonService.rezervasyonYap(1L, 2L, 3));
    }

    @Test
    void koltukBulunamazsaOzelExceptionFirlatir() {
        Yolcu yolcu = new Yolcu();
        Sefer sefer = new Sefer();
        sefer.setId(2L);

        when(kullaniciRepository.findById(1L)).thenReturn(Optional.of(yolcu));
        when(seferRepository.findById(2L)).thenReturn(Optional.of(sefer));
        when(koltukRepository.findBySefer_IdAndKoltukNo(2L, 3)).thenReturn(Optional.empty());

        assertThrows(KoltukBulunamadiException.class, () -> rezervasyonService.rezervasyonYap(1L, 2L, 3));
    }
}
