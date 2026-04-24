package com.citygo.service;

import com.citygo.exception.*;
import com.citygo.model.*;
import com.citygo.repository.*;
import com.citygo.interfaces.IRezervasyon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RezervasyonService implements IRezervasyon {

    private final BiletRepository biletRepository;
    private final KoltukRepository koltukRepository;
    private final SeferRepository seferRepository;
    private final KullaniciRepository kullaniciRepository;

    @Autowired
    public RezervasyonService(BiletRepository biletRepository, KoltukRepository koltukRepository, SeferRepository seferRepository, KullaniciRepository kullaniciRepository) {

        this.biletRepository = biletRepository;
        this.koltukRepository = koltukRepository;
        this.seferRepository = seferRepository;
        this.kullaniciRepository = kullaniciRepository;

    }

    @Override
    @Transactional
    public Bilet rezervasyonYap(Long yolcuId, Long seferId, int koltukNo) {

        // Yolcu Kontrolü
        Kullanici kullanici = kullaniciRepository.findById(yolcuId).orElseThrow(() -> new KullaniciBulunamadiException("Yolcu Bulunamadi!"));
        // Duzeltme: Yanlis tipte kullaniciyi zorla cast etmek yerine acik hata veriyoruz.
        if (!(kullanici instanceof Yolcu yolcu)) {
            throw new IllegalArgumentException("Rezervasyon sadece yolcu hesaplari icin yapilabilir.");
        }
        
        // Sefer Kontrolü
        Sefer sefer = seferRepository.findById(seferId).orElseThrow(() -> new SeferBulunamadiException("Sefer Bulunamadi!"));

        // Koltuk Kontrolü (Sefer + KoltukNo ile)
        // Duzeltme: Koltuk bulunamazsa genel 500 yerine anlamli bir hata donuyoruz.
        Koltuk koltuk = koltukRepository.findBySefer_IdAndKoltukNo(sefer.getId(), koltukNo)
            .orElseThrow(() -> new KoltukBulunamadiException("Koltuk mevcut degil!"));
        
        // Koltuk Dolu Mu Kontrolü
        if (koltuk.isDolu()) {
            throw new KapasiteDoluException("Bu Koltuk Zaten Rezerve Edilmiş!");
        }

        // Koltuğu Dolu Olarak İşaretleme
        koltuk.setDolu(true);
        koltukRepository.save(koltuk);

        // Fiyat Hesaplama
        double toplamFiyat = sefer.getArac().hesaplaToplamFiyat(sefer.getArac().getBiletFiyati());
        
        // Bilet Nesnesi Oluşturma
        Bilet bilet = new Bilet();
        bilet.setYolcu(yolcu);
        bilet.setSefer(sefer);
        bilet.setKoltuk(koltuk);
        bilet.setOdenenTutar(toplamFiyat);
        bilet.setDurum(BiletDurumu.AKTIF);

        // Kaydet ve Döndür
        return biletRepository.save(bilet);
    }

    @Override
    @Transactional
    public boolean rezervasyonIptal(Long biletId) {

        // Bilet Kontrolü
        Bilet bilet = biletRepository.findById(biletId).orElseThrow(() -> new BiletBulunamadiException("Bilet Bulunamadi!"));

        // Bilet Durumunu IPTAL_EDILDI Yap
        bilet.setDurum(BiletDurumu.IPTAL_EDILDI);

        // İlgili Koltuğu Tekrar Boş Yap
        Koltuk koltuk = bilet.getKoltuk();
        koltuk.setDolu(false);
        koltukRepository.save(koltuk);

        // Kaydet ve True Döndür
        biletRepository.save(bilet);
        return true;
    }

    @Override
    public List<Bilet> rezervasyonlariGetir(Long yolcuId) {

        return biletRepository.findByYolcu_Id(yolcuId); // BiletRepositorydeki özel metodu çağırıyoruz

    }
}
/*
 * =============================================================
 * RezervasyonService.java — Rezervasyon İş Mantığı
 * =============================================================
 * Sorumlu: Elif Feyza
 *
 * IRezervasyon interface'ini implement eden bilet/rezervasyon servisi.
 *
 * Anotasyonlar:
 * - @Service
 *
 * Implements:
 * - IRezervasyon
 *
 * Bağımlılıklar:
 * - BiletRepository
 * - KoltukRepository
 * - SeferRepository
 * - KullaniciRepository
 *
 * Metotlar (IRezervasyon'dan geliyor):
 *
 * - rezervasyonYap(Long yolcuId, Long seferId, int koltukNo): Bilet
 *     Akış:
 *     1. Yolcu'yu ID ile bul (yoksa KullaniciBulunamadiException)
 *     2. Sefer'i ID ile bul (yoksa SeferBulunamadiException)
 *     3. Koltuk'u sefer + koltukNo ile bul
 *     4. Koltuk dolu mu kontrol et (doluysa KapasiteDoluException)
 *     5. Koltuğu "dolu" olarak işaretle
 *     6. UlasimAraci.hesaplaToplamFiyat() ile fiyat hesapla (POLYMORPHISM!)
 *     7. Bilet nesnesi oluştur (durum: AKTIF)
 *     8. Bilet'i kaydet ve döndür
 *
 * - rezervasyonIptal(Long biletId): boolean
 *     Akış:
 *     1. Bilet'i ID ile bul (yoksa BiletBulunamadiException)
 *     2. Bilet durumunu IPTAL_EDILDI yap
 *     3. İlgili koltuğu tekrar "boş" yap
 *     4. Kaydet ve true döndür
 *
 * - rezervasyonlariGetir(Long yolcuId): List<Bilet>
 *     → BiletRepository.findByYolcu_Id(yolcuId) çağır
 *
 * ÖNEMLI: hesaplaToplamFiyat() çağrısında araç tipine göre farklı
 *         hesaplama çalışacak — bu Polymorphism (Override) örneğidir!
 */
