package com.citygo.service;

import com.citygo.model.Kullanici;
import com.citygo.model.Yolcu;
import com.citygo.repository.KullaniciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class KullaniciService {

    @Autowired  // Spring repository'yi otomatik baglıyor, elle new yapmıyoruz
    private KullaniciRepository kullaniciRepository;

    // yeni yolcu kaydi olusturur, email zaten varsa hata firlatir
    public Yolcu kayitOl(String ad, String soyad, String email, String sifre, String telefon, String tcNo) {

        if (kullaniciRepository.existsByEmail(email)) {
            throw new RuntimeException("Bu email adresi zaten kayıtlı: " + email);
        }

        Yolcu yolcu = new Yolcu();
        yolcu.setAd(ad);
        yolcu.setSoyad(soyad);
        yolcu.setEmail(email);
        yolcu.setSifre(sifreHashle(sifre));  // sifreyi hashleyerek kaydediyoruz
        yolcu.setTelefon(telefon);
        yolcu.setTcNo(tcNo);

        return kullaniciRepository.save(yolcu);
    }

    // email ve sifre ile giris dogrulama
    public Kullanici girisYap(String email, String sifre){

        Kullanici kullanici = kullaniciRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + email));

        // girilen sifreyi hashleyip veritabanindaki hash ile karsilastiriyoruz
        if(!kullanici.getSifre().equals(sifreHashle(sifre))){
            throw new RuntimeException("Yanlış şifre");
        }

        return kullanici;


    }

    public Kullanici kullaniciBul(Long id) {
        return kullaniciRepository.findById(id).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı, ID: " + id));
    }

    // admin paneli icin tum kullanicilari listeler
    public List<Kullanici> tumKullanicilariGetir() {
        return kullaniciRepository.findAll();
    }

    // sifreleri duz metin olarak degil, SHA-256 hash olarak kaydediyoruz
    // boylece veritabanini biri gorse bile sifreleri okuyamaz
    private String sifreHashle(String sifre) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(sifre.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Şifre hashlenemedi", e);
        }
    }

}

/*
 * =============================================================
 * KullaniciService.java — Kullanıcı İş Mantığı Katmanı
 * =============================================================
 * Sorumlu: Muhammed
 *
 * Kullanıcı kayıt, giriş ve yönetim işlemlerini yürüten servis sınıfı.
 *
 * Anotasyonlar:
 * - @Service
 *
 * Bağımlılıklar (@Autowired veya Constructor Injection):
 * - KullaniciRepository
 *
 * Metotlar:
 *
 * - kayitOl(String ad, String soyad, String email, String sifre, String
 * telefon, String tcNo): Yolcu
 * → Yeni yolcu kaydı oluşturur
 * → Email zaten varsa uygun exception fırlatır
 * → Şifreyi hashleyerek kaydeder (basit bir hash yeterli, ör: BCrypt veya
 * SHA-256)
 *
 * - girisYap(String email, String sifre): Kullanici
 * → Email ve şifre ile giriş doğrulama
 * → Bulunamazsa KullaniciBulunamadiException fırlatır
 * → Başarılı girişte Kullanici nesnesini döndürür
 *
 * - kullaniciBul(Long id): Kullanici
 * → ID ile kullanıcı bulma
 *
 * - tumKullanicilariGetir(): List<Kullanici>
 * → Admin paneli için tüm kullanıcıları listele
 *
 * Not: Şifreleme için basit bir yaklaşım yeterlidir.
 * Spring Security kullanmıyoruz, karmaşıklaştırmayın.
 */
