# 📝 Elif Feyza Şengül — Geliştirme Notları

Bu dosya, proje geliştirme sürecinde **Elif Feyza'nın takip etmesi gereken notları** içerir.

**Kullanım:** Kodunda bir bağımlılık veya güncelleme gerektiğinde buraya not eklenir. Her sprint/hafta sonunda bu dosyayı kontrol et ve gerekli güncellemeleri yap.

**Format:**
- ✅ → Tamamlandı, işlem yapıldı
- ⏳ → Beklemede, ilgili kişi henüz tamamlamadı
- 🔄 → Güncelleme gerekiyor, aksiyon al

---

## Bekleyen Güncellemeler

### 🔄 KullaniciBulunamadiException.java — Muhammed bunu bekliyor!
**Bağımlı kişi:** Muhammed

Muhammed `KullaniciService.java`'da giriş ve kayıt işlemlerini yazdı ama senin `KullaniciBulunamadiException` sınıfın henüz hazır olmadığı için geçici olarak `RuntimeException` kullanıyor.

**Senin yapman gereken:**
1. `backend/src/main/java/com/citygo/exception/KullaniciBulunamadiException.java` dosyasını yaz
2. `RuntimeException`'dan extends etmeli
3. Tek bir constructor yeterli: `KullaniciBulunamadiException(String mesaj)`
4. Yazdıktan sonra **Muhammed'e haber ver** ki kendi kodunu güncellesin

**Örnek:**
```java
public class KullaniciBulunamadiException extends RuntimeException {
    public KullaniciBulunamadiException(String mesaj) {
        super(mesaj);
    }
}
```
