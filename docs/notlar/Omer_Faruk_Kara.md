# 📝 Ömer Faruk Kara — Geliştirme Notları

Bu dosya, proje geliştirme sürecinde **Ömer Faruk'un takip etmesi gereken notları** içerir.

**Kullanım:** Kodunda bir bağımlılık veya güncelleme gerektiğinde buraya not eklenir. Her sprint/hafta sonunda bu dosyayı kontrol et ve gerekli güncellemeleri yap.

**Format:**
- ✅ → Tamamlandı, işlem yapıldı
- ⏳ → Beklemede, ilgili kişi henüz tamamlamadı
- 🔄 → Güncelleme gerekiyor, aksiyon al

---

## Bekleyen Güncellemeler

### ⏳ Frontend Login/Register — Muhammed'in Auth API'sini bekliyor
**Bağımlılık:** Muhammed → `AuthController.java`

Muhammed auth API'yi tamamladığında, Login ve Register sayfalarını şu endpoint'lere bağlaman gerekecek:

- `POST http://localhost:8080/api/auth/register` → Kayıt
  - Body: `{ "ad", "soyad", "email", "sifre", "telefon", "tcNo" }`
- `POST http://localhost:8080/api/auth/login` → Giriş
  - Body: `{ "email", "sifre" }`
- `POST http://localhost:8080/api/auth/logout` → Çıkış

Muhammed API'yi bitirince sana haber verecek.
