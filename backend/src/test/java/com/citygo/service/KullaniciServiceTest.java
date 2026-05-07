package com.citygo.service;

import com.citygo.repository.KullaniciRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KullaniciServiceTest {

    @Mock
    private KullaniciRepository kullaniciRepository;

    @InjectMocks
    private KullaniciService kullaniciService;

    @Test
    void ayniTelefonNumarasiIleKayitOlunamaz() {
        when(kullaniciRepository.existsByTelefon("05551234567")).thenReturn(true);

        // Duzeltme: telefon tekilligi servis katmaninda acik hata ile korunuyor.
        assertThrows(IllegalArgumentException.class, () -> kullaniciService.kayitOl(
                "Ali",
                "Veli",
                "ali@example.com",
                "secret123",
                "05551234567",
                "12345678901"
        ));
        verify(kullaniciRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void ayniTcKimlikNumarasiIleKayitOlunamaz() {
        when(kullaniciRepository.existsByTcNo("12345678901")).thenReturn(true);

        // Duzeltme: ayni TC kimlik no ile ikinci yolcu olusturulmasi engelleniyor.
        assertThrows(IllegalArgumentException.class, () -> kullaniciService.kayitOl(
                "Ayse",
                "Yilmaz",
                "ayse@example.com",
                "secret123",
                "05557654321",
                "12345678901"
        ));
        verify(kullaniciRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }
}
