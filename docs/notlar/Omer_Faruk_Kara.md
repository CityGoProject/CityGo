# 📝 Ömer Faruk Kara — Geliştirme Notları

Bu dosya, proje geliştirme sürecinde **Ömer Faruk'un takip etmesi gereken notları** içerir.

**Kullanım:** Kodunda bir bağımlılık veya güncelleme gerektiğinde buraya not eklenir. Her sprint/hafta sonunda bu dosyayı kontrol et ve gerekli güncellemeleri yap.

**Format:**
- ✅ → Tamamlandı, işlem yapıldı
- ⏳ → Beklemede, ilgili kişi henüz tamamlamadı
- 🔄 → Güncelleme gerekiyor, aksiyon al

---

## Bekleyen Güncellemeler

### ⏳ Frontend HomePage — Mert'in SeferController API'sini bekliyor
**Bağımlılık:** Mert → `SeferController.java`

HomePage'deki arama formu, Mert'in yazacağı sefer arama endpoint'ini kullanacak:
- `GET http://localhost:8080/api/seferler/ara?kalkis=...&varis=...&tarih=...`

### 🔄 AdminController.java — 🆕 Yeni görev!
**Durum:** Bu dosya artık **tamamen senin sorumluluğunda**. (Eskiden Muhammed ile paylaşımlıydı)

**Yapılacaklar:**
- `@RestController`, `@RequestMapping("/api/admin")`
- Sefer CRUD: `GET/POST/PUT/DELETE /api/admin/seferler`
- Kullanıcı listesi: `GET /api/admin/kullanicilar`
- Bilet listesi: `GET /api/admin/biletler`
- İstatistikler: `GET /api/admin/istatistikler`

**Bağımlılıklar:** `SeferRepository` (Mert), `KullaniciService` (Muhammed), `BiletRepository` (Elif Feyza)

### 📌 Görev Değişikliği Notu
`LoginPage.jsx`, `RegisterPage.jsx` ve `AdminPanel.jsx` **Muhammed'e devredildi**. Auth backend'ini o yazdığı için frontend'ini de o yapacak. Senin frontend yükün azaldı ama backend OOP görevin arttı — dengeleme yapıldı.
