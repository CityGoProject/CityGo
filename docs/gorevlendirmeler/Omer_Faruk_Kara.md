# 👤 Ömer Faruk Kara — Görev Planı

**Rol:** Frontend Geliştirme & Backend Admin API & Veri Dışa Aktarım  
**Öğrenci No:** 24118080064

---

## 📁 Sorumlu Olduğun Dosyalar (Project Tree)

Aşağıdaki ağaçta **⭐ işaretli dosyalar** senin sorumluluğunda.  
Frontend klasörünün **tamamı** sana ait.

```
backend/
└── src/main/java/com/citygo/
    ├── CityGoApplication.java                       (Muhammed)
    ├── config/
    │   ├── WebConfig.java                           (Muhammed)
    │   └── DataSeeder.java                          (Mert)
    ├── model/
    │   ├── ...                                      (Muhammed + Mert + Elif Feyza)
    ├── interfaces/
    │   ├── IRezervasyon.java                        (Elif Feyza)
    │   ├── IAranabilir.java                         (Mert)
    │   └── IExportable.java                         ⭐ Export interface'i
    ├── repository/
    │   └── ...                                      (Muhammed + Mert + Elif Feyza)
    ├── service/
    │   ├── KullaniciService.java                    (Muhammed)
    │   ├── AramaService.java                        (Mert)
    │   ├── RezervasyonService.java                  (Elif Feyza)
    │   └── ExportService.java                       ⭐ Export iş mantığı
    ├── controller/
    │   ├── AuthController.java                      (Muhammed)
    │   ├── SeferController.java                     (Mert)
    │   ├── BiletController.java                     (Elif Feyza)
    │   ├── AdminController.java                     ⭐ Admin paneli API (tamamen Ömer Faruk'un)
    │   └── ExportController.java                    ⭐ Export API endpoint'leri
    └── exception/
        └── ...                                      (Elif Feyza)

frontend/                                            ⭐ TAMAMINI SEN YAPACAKSIN
├── public/
│   └── index.html
├── src/
│   ├── main.jsx                                     ⭐ React giriş noktası
│   ├── App.jsx                                      ⭐ Ana uygulama + routing
│   ├── components/                                  ⭐ Yeniden kullanılabilir bileşenler
│   │   ├── Navbar.jsx                               ⭐ Üst navigasyon çubuğu
│   │   ├── Footer.jsx                               ⭐ Alt kısım
│   │   ├── TripCard.jsx                             ⭐ Sefer kartı bileşeni
│   │   ├── SeatMap.jsx                              ⭐ Görsel koltuk haritası
│   │   └── TicketCard.jsx                           ⭐ Bilet kartı bileşeni
│   ├── pages/                                       ⭐ Sayfa bileşenleri
│   │   ├── LoginPage.jsx                            ⭐ Giriş sayfası
│   │   ├── RegisterPage.jsx                         ⭐ Kayıt sayfası
│   │   ├── HomePage.jsx                             ⭐ Ana sayfa (arama formu)
│   │   ├── SearchResultsPage.jsx                    ⭐ Arama sonuçları
│   │   ├── SeatSelectionPage.jsx                    ⭐ Koltuk seçimi
│   │   ├── MyTicketsPage.jsx                        ⭐ Biletlerim
│   │   └── AdminPanel.jsx                           ⭐ Admin paneli
│   └── services/                                    ⭐ API çağrıları
│       └── api.js                                   ⭐ axios ile backend bağlantısı
├── package.json
└── vite.config.js
```

> **Toplam sorumlu dosya sayısı:** ~18 dosya (frontend + backend export modülü)

---

## 📅 Haftalık Görev Planı

### 🔴 HAFTA 1 — 7–13 Nisan (Frontend Kurulumu)

| # | Görev | Yapılacaklar | Öncelik |
|---|-------|-------------|---------|
| 1 | React projesi oluştur | `npx create-vite@latest frontend -- --template react` komutu ile | 🔴 İLK İŞ |
| 2 | MUI kur | `npm install @mui/material @emotion/react @emotion/styled @mui/icons-material` | 🔴 İLK İŞ |
| 3 | React Router kur | `npm install react-router-dom` | 🔴 İLK İŞ |
| 4 | Axios kur | `npm install axios` | 🔴 İLK İŞ |
| 5 | `api.js` dosyasını oluştur | axios instance: `baseURL: "http://localhost:8080/api"` | 🔴 İLK İŞ |
| 6 | `App.jsx` routing yap | React Router ile sayfa route'larını tanımla | 🔴 İLK İŞ |
| 7 | `Navbar.jsx` | MUI AppBar bileşeni ile navigasyon çubuğu | 🟡 |

**Bu haftanın çıktısı:** `npm run dev` ile boş React uygulaması localhost:5173'te çalışmalı, route'lar arası geçiş yapılabilmeli.

---

### 🟡 HAFTA 2 — 14–20 Nisan (Temel Sayfalar)

| # | Dosya | Yapılacaklar | Detay |
|---|-------|-------------|-------|
| 8 | `HomePage.jsx` | Ana sayfa (arama formu) | MUI Autocomplete/Select (kalkış, varış şehirleri), DatePicker (tarih), ToggleButton (araç tipi: Uçak/Tren/Otobüs). "Sefer Ara" butonu → SearchResultsPage'e yönlendir. |
| 9 | `Footer.jsx` | Alt kısım | Basit bir footer: "© 2026 CityGo" + takım bilgisi |

> **Not:** `LoginPage.jsx`, `RegisterPage.jsx` ve `AdminPanel.jsx` **Muhammed'e devredildi**. Auth backend'ini o yazdığı için frontend'ini de o yapacak.

**Bu haftanın çıktısı:** HomePage ve Footer görsel olarak tamamlanmış ve backend API'ye bağlanabilir durumda olmalı.

---

### 🟠 HAFTA 3 — 21–23 Nisan (Milestone / Demo Teslimi)

| # | Dosya | Yapılacaklar |
|---|-------|-------------|
| 12 | `SearchResultsPage.jsx` | Arama sonuçları sayfası. `api.get("/seferler/ara?kalkis=...&varis=...&tarih=...")` çağrısı. Sonuçları `TripCard` bileşenleri ile listele. |
| 13 | `TripCard.jsx` | Sefer kartı bileşeni. Props: firma, kalkış, varış, tarih, fiyat, araç tipi. "Koltuk Seç" butonu → SeatSelectionPage'e yönlendir. |
| 14 | Frontend ↔ Backend entegrasyonu | Login, arama ve bilet alma akışının uçtan uca çalışması |

> **🚨 23 Nisan: ÇALIŞAN DEMO TESLİMİ — En az giriş yapılıp sefer aranabilmeli!**

---

### 🟢 HAFTA 4–5 — 24 Nisan – 10 Mayıs (Tamamlama)

| # | Dosya | Yapılacaklar | Detay |
|---|-------|-------------|-------|
| 15 | `SeatSelectionPage.jsx` | Koltuk seçim sayfası | `api.get("/seferler/{id}/koltuklar")` ile koltukları çek. `SeatMap` bileşeniyle görsel harita. Dolu koltuklar gri/kırmızı, boş koltuklar yeşil. Tıklayınca seçim + bilet al butonu. |
| 16 | `SeatMap.jsx` | Görsel koltuk haritası bileşeni | Grid layout ile koltukları göster (4'lü sıra: 2+koridor+2). Props: koltuklar dizisi, seçim callback'i. Renk kodlaması: boş=yeşil, dolu=gri, seçili=mavi. |
| 17 | `MyTicketsPage.jsx` | Biletlerim sayfası | `api.get("/biletler/benim?yolcuId=...")` ile biletleri çek. `TicketCard` bileşenleri ile listele. İptal butonu → `api.put("/biletler/{id}/iptal")`. |
| 18 | `TicketCard.jsx` | Bilet kartı bileşeni | Props: sefer bilgisi, koltuk no, fiyat, tarih, durum. Aktif biletlerde "İptal Et" butonu. İptal edilen biletlerde "İptal Edildi" badge'i. |
| 19 | `AdminController.java` | **🆕 Backend Admin API** | `@RestController`, `@RequestMapping("/api/admin")`. Endpoint'ler: `GET/POST/PUT/DELETE /seferler` (sefer CRUD), `GET /kullanicilar`, `GET /biletler`, `GET /istatistikler`. Birden fazla entity ile çalışır — OOP prensiplerini gösterir. |
| 20 | `IExportable.java` | Backend interface | `exportJSON()` ve `exportCSV()` metot tanımları. `byte[]` döndürecek. |
| 21 | `ExportService.java` | Backend servis | `@Service`, `implements IExportable`. JSON: ObjectMapper ile. CSV: StringBuilder ile başlık satırı + veri satırları. |
| 22 | `ExportController.java` | Backend controller | `@RestController`, `@RequestMapping("/api/export")`. `GET /biletler/json` → dosya indirme. `GET /biletler/csv` → dosya indirme. Content-Disposition header'ı eklemeyi unutma! |
| 23 | UI son rötuşlar | Responsive tasarım, animasyonlar, tema tutarlılığı |
| 24 | Proje raporu | Tüm ekiple birlikte |
| 25 | Video sunum | Tüm ekiple birlikte |

> **🚨 10 Mayıs: FİNAL TESLİM**

---

## 🎯 Başlama Sırası (Nereden Başlamalıyım?)

```
 ── HAFTA 1 ──
1. Vite + React projesi oluştur
2. MUI, React Router, Axios kur
3. api.js → axios instance
4. App.jsx → routing yapısı
5. Navbar.jsx → navigasyon
 ── HAFTA 2 ──
6. LoginPage.jsx → giriş
7. RegisterPage.jsx → kayıt
8. HomePage.jsx → arama formu
9. Footer.jsx
   ↓ (temel sayfalar hazır)
 ── HAFTA 3 (DEMO!) ──
10. SearchResultsPage.jsx → sonuçlar
11. TripCard.jsx → sefer kartı
12. Backend entegrasyonu test
    ↓ (demo teslime hazır)
 ── HAFTA 4-5 ──
13. SeatSelectionPage.jsx → koltuk seçimi
14. SeatMap.jsx → görsel harita
15. MyTicketsPage.jsx → biletlerim
16. TicketCard.jsx → bilet kartı
17. AdminPanel.jsx → admin paneli
18. IExportable.java → backend interface
19. ExportService.java → backend servis
20. ExportController.java → backend API
21. UI son rötuşlar
```

---

## 📌 API Çağrıları Referans Tablosu

Sayfalarını yazarken bu endpoint'leri kullanacaksın:

| Sayfa | HTTP Metot | Endpoint | Açıklama |
|-------|-----------|----------|----------|
| LoginPage | `POST` | `/api/auth/login` | `{ email, sifre }` |
| RegisterPage | `POST` | `/api/auth/register` | `{ ad, soyad, email, sifre, telefon, tcNo }` |
| HomePage | `GET` | `/api/seferler/ara?kalkis=...&varis=...` | Query params |
| SearchResults | `GET` | `/api/seferler/ara?...` | Aynı endpoint |
| SeatSelection | `GET` | `/api/seferler/{id}/koltuklar` | Koltuk listesi |
| SeatSelection | `POST` | `/api/biletler` | `{ yolcuId, seferId, koltukNo }` |
| MyTickets | `GET` | `/api/biletler/benim?yolcuId=...` | Bilet listesi |
| MyTickets | `PUT` | `/api/biletler/{id}/iptal` | Bilet iptal |
| AdminPanel | `GET` | `/api/admin/seferler` | Tüm seferler |
| AdminPanel | `POST` | `/api/admin/seferler` | Sefer ekle |
| AdminPanel | `PUT` | `/api/admin/seferler/{id}` | Sefer güncelle |
| AdminPanel | `DELETE` | `/api/admin/seferler/{id}` | Sefer sil |
| AdminPanel | `GET` | `/api/admin/kullanicilar` | Kullanıcı listesi |
| AdminPanel | `GET` | `/api/admin/istatistikler` | İstatistikler |
| Export | `GET` | `/api/export/biletler/json` | JSON indir |
| Export | `GET` | `/api/export/biletler/csv` | CSV indir |

---

## 📌 OOP Prensipleri (Senin Modülünde)

| Prensip | Nerede Uygulanıyor |
|---------|-------------------|
| **Abstraction** | `IExportable` interface tanımı |
| **Encapsulation** | ExportService'de iç detayların gizlenmesi |
| **Polymorphism** | AdminController'da farklı entity'lerle (Sefer, Kullanici, Bilet) çalışma |

---

## 💡 Faydalı Kaynaklar

- [MUI Components](https://mui.com/material-ui/all-components/)
- [React Router v6](https://reactrouter.com/en/main/start/tutorial)
- [Axios Kullanımı](https://axios-http.com/docs/intro)
- [Vite + React Kurulum](https://vitejs.dev/guide/)
