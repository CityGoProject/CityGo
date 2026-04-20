# 📝 Muhammed Köseoğlu — Geliştirme Notları

Bu dosya, proje geliştirme sürecinde **Muhammed'in takip etmesi gereken notları** içerir.

**Kullanım:** Kodunda bir bağımlılık veya güncelleme gerektiğinde buraya not eklenir. Her sprint/hafta sonunda bu dosyayı kontrol et ve gerekli güncellemeleri yap.

**Format:**
- ✅ → Tamamlandı, işlem yapıldı
- ⏳ → Beklemede, ilgili kişi henüz tamamlamadı
- 🔄 → Güncelleme gerekiyor, aksiyon al

---

## Bekleyen Güncellemeler

### ✅ KullaniciService.java — Exception sınıfı güncellemesi (TAMAMLANDI)
**Bağımlılık:** Elif Feyza → `KullaniciBulunamadiException.java`

Elif Feyza exception sınıfını yazdı, `KullaniciService.java`'daki `RuntimeException`'lar `KullaniciBulunamadiException` ile değiştirildi.

1. `KullaniciService.java`'daki import'lara ekle:
   ```java
   import com.citygo.exception.KullaniciBulunamadiException;
   ```
2. Tüm `new RuntimeException(...)` → `new KullaniciBulunamadiException(...)` ile değiştir
3. Bu notu ✅ olarak işaretle

**Etkilenen dosyalar:** `KullaniciService.java`, `AuthController.java`
