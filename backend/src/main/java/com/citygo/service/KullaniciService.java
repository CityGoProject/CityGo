package com.citygo.service;

import com.citygo.model.Kullanici;
import com.citygo.model.Yolcu;
import com.citygo.model.Admin;
import com.citygo.exception.KimlikDogrulamaException;
import com.citygo.exception.KullaniciBulunamadiException;  // Elif Feyza'nin yazdigi exception sinifi
import com.citygo.repository.KullaniciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

@Service
public class KullaniciService {

    @Autowired  // Spring repository'yi otomatik baglıyor, elle new yapmıyoruz
    private KullaniciRepository kullaniciRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // yeni yolcu kaydi olusturur, email zaten varsa hata firlatir
    public Yolcu kayitOl(String ad, String soyad, String email, String sifre, String telefon, String tcNo) {

        if (kullaniciRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Bu email adresi zaten kayıtlı: " + email);
        }
        if (kullaniciRepository.existsByTelefon(telefon)) {
            // Duzeltme: iki farkli kullanici ayni telefon ile kayit olamamali.
            throw new IllegalArgumentException("Bu telefon numarası zaten kayıtlı: " + telefon);
        }
        if (kullaniciRepository.existsByTcNo(tcNo)) {
            // Duzeltme: iki yolcu ayni TC kimlik numarasi ile kayit olamamali.
            throw new IllegalArgumentException("Bu TC kimlik numarası zaten kayıtlı.");
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

        // Duzeltme: Login sirasinda kullanici var/yok bilgisini aciga cikarmadan
        // tek tip 401 hatasi uretiyoruz.
        Kullanici kullanici = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new KimlikDogrulamaException("E-posta veya şifre hatalı"));

        // Duzeltme: Yeni kayitlar BCrypt ile dogrulanir; eski SHA-256 seed/kayitlar
        // ilk basarili giriste BCrypt'e tasinir.
        if(!sifreDogruMu(sifre, kullanici)){
            throw new KimlikDogrulamaException("E-posta veya şifre hatalı");
        }

        return kullanici;


    }

    public Kullanici kullaniciBul(Long id) {
        return kullaniciRepository.findById(id).orElseThrow(() -> new KullaniciBulunamadiException("Kullanıcı bulunamadı, ID: " + id));
    }

    // admin paneli icin tum kullanicilari listeler
    public List<Kullanici> tumKullanicilariGetir() {
        return kullaniciRepository.findAll();
    }

    public Admin adminKullaniciBul(Long id) {
        // Duzeltme: Admin endpointleri artik frontend guard'a guvenmek yerine
        // backend tarafinda da kullanicinin admin olup olmadigini kontrol ediyor.
        Kullanici kullanici = kullaniciBul(id);
        if (kullanici instanceof Admin admin) {
            return admin;
        }
        throw new KimlikDogrulamaException("Bu işlem için admin yetkisi gereklidir.");
    }

    // sifreleri duz metin olarak degil, BCrypt hash olarak kaydediyoruz
    // boylece veritabanini biri gorse bile sifreleri okuyamaz
    public String sifreHashle(String sifre) {
        // Duzeltme: Saltsiz SHA-256 yerine BCrypt kullaniyoruz.
        return passwordEncoder.encode(sifre);
    }

    private boolean sifreDogruMu(String sifre, Kullanici kullanici) {
        String kayitliSifre = kullanici.getSifre();
        if (kayitliSifre != null && kayitliSifre.startsWith("$2")) {
            return passwordEncoder.matches(sifre, kayitliSifre);
        }

        if (kayitliSifre != null && kayitliSifre.equals(sha256(sifre))) {
            // Duzeltme: Eski SHA-256 kayitlar veri silmeden korunur; basarili giriste
            // BCrypt'e sessizce tasinir.
            kullanici.setSifre(sifreHashle(sifre));
            kullaniciRepository.save(kullanici);
            return true;
        }

        return false;
    }

    private String sha256(String sifre) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(sifre.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
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
