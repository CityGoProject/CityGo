# CENG106 OBJECT ORIENTED PROGRAMMING LAB

# Proje Milestone Raporu

**Grup:** 7  
**Tarih:** 26 Nisan 2026  
**GitHub:** https://github.com/CityGoProject/CityGo

---

## 1. Proje Ekibi

| Ad Soyad | Öğrenci Numarası | Şube |
|----------|------------------|------|
| Mustafa Mert Çevik | 24118080086 | 1. Şube |
| Muhammed Köseoğlu | 24118080049 | 1. Şube |
| Ömer Faruk Kara | 24118080064 | 1. Şube |
| Elif Feyza Şengül | 25118080004 | 1. Şube |

---

## 2. Proje İsmi

**CityGo: Akıllı Ulaşım ve Rezervasyon Sistemi**

---

## 3. Proje Konusu ve Amacı

### 3.1 Proje Konusu

CityGo, farklı ulaşım türlerini (uçak, tren, otobüs) tek bir platformda birleştiren, Java tabanlı bir full-stack web uygulamasıdır. Kullanıcılar bu platform üzerinden sefer arayabilir, koltuk seçebilir, bilet satın alabilir ve biletlerini dijital olarak yönetebilir. Yönetici (admin) kullanıcıları ise seferleri, araçları ve rezervasyonları sistem üzerinden yönetebilir.

Proje, tarayıcı tabanlı bir web uygulaması olarak geliştirilmekte olup; backend tarafında **Spring Boot** framework'ü, frontend tarafında ise **React** kütüphanesi kullanılmaktadır. Veriler **H2 gömülü ilişkisel veritabanında** dosya tabanlı olarak saklanmaktadır.

### 3.2 Projenin Amacı

Projenin temel amacı, Nesne Yönelimli Programlama (OOP) prensiplerinin tamamını gerçek dünya senaryolarına uygulayarak, modüler ve sürdürülebilir bir ulaşım rezervasyon altyapısı oluşturmaktır.

**Fonksiyonel Amaçlar:**

- Kullanıcıların sisteme kayıt olup giriş yapabilmesi
- Farklı ulaşım türleri arasında sefer arama ve filtreleme yapabilmesi
- Görsel koltuk haritası üzerinden koltuk seçimi yapabilmesi
- Bilet satın alma, görüntüleme ve iptal edebilmesi
- Yöneticilerin sefer ekleme, güncelleme, silme ve tüm rezervasyonları yönetebilmesi
- Bilet verilerini JSON ve CSV formatında dışa aktarabilmesi

**OOP Prensiplerinin Uygulanması:**

| OOP Prensibi | Uygulandığı Yer |
|-------------|----------------|
| **Kalıtım (Inheritance)** | `UlasimAraci` (abstract) → `Ucak`, `Tren`, `Otobus` alt sınıfları; `Kullanici` (abstract) → `Yolcu`, `Admin` alt sınıfları |
| **Kapsülleme (Encapsulation)** | Tüm entity sınıflarında alanlar `private`, erişim `getter/setter` metotları ile |
| **Çok Biçimlilik — Override** | Her ulaşım aracı `hesaplaToplamFiyat()` metodunu kendi hesaplama mantığıyla geçersiz kılmaktadır (Uçak → havaalanı vergisi, Tren → vagon tipi farkı, Otobüs → ikram bedeli) |
| **Çok Biçimlilik — Overloading** | `AramaService`'te 3 farklı `ara()` metodu: `ara(kalkış, varış)`, `ara(kalkış, varış, tarih)`, `ara(kalkış, varış, tarih, araçTipi)` |
| **Soyutlama (Abstraction)** | `UlasimAraci` ve `Kullanici` abstract sınıfları; `IRezervasyon`, `IAranabilir`, `IExportable` interface'leri |
| **Hata Yönetimi (Exception Handling)** | 6 özel exception sınıfı + `GlobalExceptionHandler` ile merkezi hata yönetimi |
| **Interface Kullanımı** | `IRezervasyon` (rezervasyon kontratı), `IAranabilir` (arama kontratı), `IExportable` (dışa aktarım kontratı) |

---

## 4. İş Bölümü

Her ekip üyesi hem backend hem de frontend geliştirme sürecinde aktif rol almaktadır. Görevler modül bazlı dağıtılmış olup, her üye kendi modülünün tüm katmanlarından (model → repository → service → controller) sorumludur.

### 4.1 Muhammed Köseoğlu — Kullanıcı Yönetimi & Proje Altyapısı

| Kategori | Yapılan İşler |
|----------|--------------|
| Proje Altyapısı | Spring Boot projesi oluşturma, `pom.xml` bağımlılık yönetimi |
| Proje Altyapısı | H2 veritabanı konfigürasyonu (`application.properties`) |
| Proje Altyapısı | `CityGoApplication.java` — Ana uygulama sınıfı |
| Proje Altyapısı | `WebConfig.java` — CORS konfigürasyonu |
| Proje Altyapısı | GitHub repository kurulumu, branch stratejisi |
| Backend Model | `Kullanici.java` (abstract), `Yolcu.java`, `Admin.java` entity sınıfları |
| Backend Repository | `KullaniciRepository.java` |
| Backend Service | `KullaniciService.java` — kayıt, giriş, SHA-256 ile şifre hashleme |
| Backend Controller | `AuthController.java` — `/api/auth/register`, `/api/auth/login`, `/api/auth/logout` |
| Frontend | `LoginPage.jsx` — Giriş sayfası (MUI, backend entegrasyonu) |
| Frontend | `RegisterPage.jsx` — Kayıt sayfası (form validasyonu, otomatik giriş) |
| Frontend | `AdminPanel.jsx` — Admin yönetim paneli (sefer CRUD, kullanıcı/bilet listesi, istatistikler, dışa aktarma) |
| Koordinasyon | Ekip toplantıları, code review, modüller arası entegrasyon, `README.md` |

### 4.2 Mustafa Mert Çevik — Ulaşım Modeli & Sefer Yönetimi

| Kategori | Yapılan İşler |
|----------|--------------|
| Backend Model | `UlasimAraci.java` — Abstract üst sınıf (`@Inheritance`, `@DiscriminatorColumn`) |
| Backend Model | `Ucak.java` — Havaalanı vergisi ile fiyat hesaplama (Override) |
| Backend Model | `Tren.java` — Vagon tipi ve hat tipi ile fiyat hesaplama (Override) |
| Backend Model | `Otobus.java` — İkram bedeli ile fiyat hesaplama (Override) |
| Backend Model | `Sefer.java` — Sefer entity, `koltuklariOlustur()` metodu |
| Backend Model | `Koltuk.java` entity, `KoltukTipi.java` enum |
| Backend Interface | `IAranabilir.java` — 3 overloaded `ara()` metodu tanımı |
| Backend Repository | `SeferRepository.java`, `KoltukRepository.java`, `UlasimAraciRepository.java` |
| Backend Service | `AramaService.java` — `IAranabilir` implementasyonu, 3 overloaded arama metodu |
| Backend Controller | `SeferController.java` — `/api/seferler/ara`, `/{id}`, `/{id}/koltuklar` |

### 4.3 Ömer Faruk Kara — Frontend Geliştirme & Veri Dışa Aktarım

| Kategori | Yapılan İşler |
|----------|--------------|
| Frontend Altyapı | React projesi kurulumu (Vite + MUI + React Router) |
| Frontend Altyapı | `api.js` — axios ile backend bağlantısı, `auth.js` — oturum yönetimi |
| Frontend Altyapı | `App.jsx` — Routing yapısı (ProtectedRoute, AdminRoute, PublicOnlyRoute) |
| Frontend Bileşenler | `Navbar.jsx`, `Footer.jsx` — Layout bileşenleri |
| Frontend Sayfalar | `HomePage.jsx` — Sefer arama formu (Autocomplete, şehir listesi, araç tipi filtresi) |
| Frontend Sayfalar | `SearchResultsPage.jsx` — Arama sonuçları listesi |
| Frontend Sayfalar | `SeatSelectionPage.jsx` — Görsel koltuk seçimi |
| Frontend Sayfalar | `MyTicketsPage.jsx` — Biletlerim sayfası (bilet listeleme, iptal) |
| Frontend Servisler | `tripService.js`, `ticketService.js`, `adminService.js`, `exportService.js`, `cities.js` |
| Backend Interface | `IExportable.java` — `exportJSON()` ve `exportCSV()` tanımı |
| Backend Service | `ExportService.java` — JSON (Jackson) ve CSV (StringBuilder) dışa aktarım |
| Backend Controller | `ExportController.java` — `/api/export/biletler/json`, `/api/export/biletler/csv` |
| Backend Controller | `AdminController.java` — Sefer CRUD, kullanıcı/bilet listesi, istatistikler |

### 4.4 Elif Feyza Şengül — Rezervasyon Sistemi & Hata Yönetimi

| Kategori | Yapılan İşler |
|----------|--------------|
| Backend Model | `Bilet.java` — Entity sınıfı (`@PrePersist` ile otomatik tarih) |
| Backend Model | `BiletDurumu.java` — Enum: `AKTIF`, `IPTAL_EDILDI`, `KULLANILDI` |
| Backend Interface | `IRezervasyon.java` — Rezervasyon kontratı (3 metot) |
| Backend Repository | `BiletRepository.java` — Özel sorgu metotları |
| Backend Service | `RezervasyonService.java` — `IRezervasyon` implementasyonu, `@Transactional` işlem yönetimi |
| Backend Controller | `BiletController.java` — `/api/biletler`, `/benim`, `/{id}/iptal` |
| Backend Exception | `KapasiteDoluException.java` — Koltuk dolu hatası |
| Backend Exception | `GecersizTarihException.java` — Geçersiz tarih hatası |
| Backend Exception | `BiletBulunamadiException.java` — Bilet bulunamadı hatası |
| Backend Exception | `KullaniciBulunamadiException.java` — Kullanıcı bulunamadı hatası |
| Backend Exception | `SeferBulunamadiException.java` — Sefer bulunamadı hatası |
| Backend Exception | `KoltukBulunamadiException.java` — Koltuk bulunamadı hatası |
| Backend Exception | `GlobalExceptionHandler.java` — `@RestControllerAdvice` ile merkezi hata yönetimi |

---

## 5. Neler Yapıldı — Proje İşlemleri

### 5.1 Backend Geliştirme (Spring Boot — Java 17)

#### Proje Altyapısı ✅
- Spring Boot 3.2.5 projesi oluşturuldu ve konfigüre edildi
- Maven bağımlılıkları tanımlandı: `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `h2`, `spring-boot-starter-validation`, `spring-boot-devtools`
- H2 veritabanı dosya tabanlı modda yapılandırıldı (`application.properties`)
- CORS konfigürasyonu oluşturuldu (`WebConfig.java`)
- GitHub repository ve branch stratejisi kuruldu

#### Model Katmanı — Entity Sınıfları ✅
Toplam **12 Java sınıfı** (model katmanı) tamamlandı:

| Sınıf | Tip | OOP Prensibi | Durum |
|-------|-----|-------------|-------|
| `UlasimAraci.java` | Abstract Entity | Inheritance, Abstraction | ✅ Tamamlandı |
| `Ucak.java` | Entity (extends UlasimAraci) | Inheritance, Polymorphism (Override) | ✅ Tamamlandı |
| `Tren.java` | Entity (extends UlasimAraci) | Inheritance, Polymorphism (Override) | ✅ Tamamlandı |
| `Otobus.java` | Entity (extends UlasimAraci) | Inheritance, Polymorphism (Override) | ✅ Tamamlandı |
| `Kullanici.java` | Abstract Entity | Inheritance, Abstraction | ✅ Tamamlandı |
| `Yolcu.java` | Entity (extends Kullanici) | Inheritance | ✅ Tamamlandı |
| `Admin.java` | Entity (extends Kullanici) | Inheritance | ✅ Tamamlandı |
| `Sefer.java` | Entity | Encapsulation | ✅ Tamamlandı |
| `Koltuk.java` | Entity | Encapsulation | ✅ Tamamlandı |
| `Bilet.java` | Entity | Encapsulation | ✅ Tamamlandı |
| `BiletDurumu.java` | Enum | — | ✅ Tamamlandı |
| `KoltukTipi.java` | Enum | — | ✅ Tamamlandı |

#### Interface Tanımları ✅

| Interface | Implement Eden | Metotlar |
|-----------|---------------|---------|
| `IRezervasyon` | `RezervasyonService` | `rezervasyonYap()`, `rezervasyonIptal()`, `rezervasyonlariGetir()` |
| `IAranabilir` | `AramaService` | `ara(kalkış, varış)`, `ara(kalkış, varış, tarih)`, `ara(kalkış, varış, tarih, araçTipi)` |
| `IExportable` | `ExportService` | `exportJSON()`, `exportCSV()` |

#### Repository Katmanı ✅
5 JPA Repository interface'i tamamlandı: `KullaniciRepository`, `SeferRepository`, `KoltukRepository`, `BiletRepository`, `UlasimAraciRepository`

#### Service Katmanı ✅
4 servis sınıfı tamamlandı:

| Servis | Temel İşlevler |
|--------|---------------|
| `KullaniciService` | Kayıt, giriş, SHA-256 şifre hashleme, kullanıcı yönetimi |
| `AramaService` | 3 overloaded sefer arama metodu, sefer detayı |
| `RezervasyonService` | Bilet alma (polymorphic fiyat hesaplama), iptal, listeleme |
| `ExportService` | JSON (Jackson) ve CSV (StringBuilder) dışa aktarım |

#### Controller Katmanı (REST API) ✅
5 REST Controller tamamlandı ve 18 endpoint hizmete açıldı:

| Controller | Endpoint Sayısı | Temel İşlev |
|-----------|----------------|-------------|
| `AuthController` | 3 | Kayıt, giriş, çıkış |
| `SeferController` | 3 | Sefer arama, detay, koltuklar |
| `BiletController` | 3 | Bilet alma, listeleme, iptal |
| `AdminController` | 7 | Sefer CRUD, kullanıcı/bilet listesi, istatistikler |
| `ExportController` | 2 | JSON ve CSV dışa aktarım |

#### Hata Yönetimi (Exception Handling) ✅
6 özel exception sınıfı + 1 merkezi hata yöneticisi:
- `KapasiteDoluException` → 400 Bad Request
- `GecersizTarihException` → 400 Bad Request
- `BiletBulunamadiException` → 404 Not Found
- `KullaniciBulunamadiException` → 404 Not Found
- `SeferBulunamadiException` → 404 Not Found
- `KoltukBulunamadiException` → 404 Not Found
- `GlobalExceptionHandler` → `@RestControllerAdvice` ile tüm hataları merkezi olarak yakalama

### 5.2 Frontend Geliştirme (React + MUI)

#### Uygulama Altyapısı ✅
- React 19 + Vite 8 projesi oluşturuldu
- MUI 9 (Material UI) entegrasyonu yapıldı
- React Router ile sayfa yönlendirmeleri oluşturuldu
- Axios ile backend API bağlantı katmanı kuruldu
- `ProtectedRoute`, `PublicOnlyRoute`, `AdminRoute` guard bileşenleri yazıldı

#### Sayfa Bileşenleri ✅

| Sayfa | Özellikler | Durum |
|-------|-----------|-------|
| `LoginPage.jsx` | E-posta/şifre formu, hata yönetimi, backend bağlantı kontrolü | ✅ Tamamlandı |
| `RegisterPage.jsx` | Kayıt formu (ad, soyad, email, şifre, telefon, TC No), otomatik giriş | ✅ Tamamlandı |
| `HomePage.jsx` | Sefer arama formu (Autocomplete şehir seçimi, tarih, araç tipi filtresi) | ✅ Tamamlandı |
| `SearchResultsPage.jsx` | Arama sonuçları listesi, sefer kartları | ✅ Tamamlandı |
| `SeatSelectionPage.jsx` | Görsel koltuk seçimi, bilet satın alma | ✅ Tamamlandı |
| `MyTicketsPage.jsx` | Kullanıcının biletleri, iptal butonu | ✅ Tamamlandı |
| `AdminPanel.jsx` | Tab yapısı: İstatistikler, Sefer CRUD, Kullanıcılar, Biletler, Dışa Aktarma | ✅ Tamamlandı |

#### Layout Bileşenleri ✅
- `Navbar.jsx` — Üst navigasyon çubuğu
- `Footer.jsx` — Alt bilgi bölümü

#### API Servis Katmanı ✅

| Servis Dosyası | İşlev |
|---------------|-------|
| `api.js` | Axios instance (baseURL: `localhost:8080/api`) |
| `auth.js` | Login, register, localStorage oturum yönetimi |
| `tripService.js` | Sefer arama, detay, koltuk çekme |
| `ticketService.js` | Bilet alma, listeleme, iptal |
| `adminService.js` | Admin CRUD, istatistikler |
| `exportService.js` | JSON/CSV dosya indirme |
| `cities.js` | Türkiye şehir listesi |

### 5.3 Veritabanı Tasarımı

H2 gömülü ilişkisel veritabanı dosya tabanlı modda çalışmaktadır. JPA/Hibernate ile nesne-ilişkisel eşleme yapılmaktadır. Tablo yapısı:

| Tablo | Açıklama | Temel Alanlar |
|-------|----------|---------------|
| `kullanicilar` | Yolcu ve Admin bilgileri (SINGLE_TABLE stratejisi) | id, ad, soyad, email, sifre, telefon, rol |
| `ulasimaraci` | Uçak, Tren, Otobüs (SINGLE_TABLE stratejisi) | id, firma, model, kapasite, biletFiyati, arac_tipi |
| `seferler` | Sefer bilgileri | id, arac_id, kalkisNoktasi, varisNoktasi, kalkisZamani, varisZamani |
| `koltuklar` | Koltuk bilgileri | id, sefer_id, koltukNo, dolu, tip |
| `biletler` | Bilet/Rezervasyon bilgileri | id, yolcu_id, sefer_id, koltuk_id, odenenTutar, olusturmaTarihi, durum |

### 5.4 Kullanılan Teknolojiler

| Katman | Teknoloji | Versiyon |
|--------|-----------|----------|
| Programlama Dili | Java | 17 |
| Backend Framework | Spring Boot | 3.2.5 |
| ORM | Spring Data JPA (Hibernate) | — |
| Veritabanı | H2 Database (Dosya Tabanlı) | 2.x |
| Frontend | React | 19.x |
| UI Kütüphanesi | MUI (Material UI) | 9.x |
| Build Aracı (Backend) | Maven | — |
| Build Aracı (Frontend) | Vite | 8.x |
| HTTP İstemcisi | Axios | — |
| Routing | React Router | 7.x |
| Versiyon Kontrolü | Git & GitHub | — |

### 5.5 Proje Dosya Yapısı (Özet)

```
CityGo/
├── backend/                              # Spring Boot Backend
│   ├── pom.xml                           # Maven bağımlılıkları
│   ├── src/main/resources/
│   │   └── application.properties        # Uygulama konfigürasyonu
│   └── src/main/java/com/citygo/
│       ├── CityGoApplication.java        # Ana uygulama sınıfı
│       ├── config/                       # WebConfig, DataSeeder
│       ├── model/                        # 12 entity/enum sınıfı
│       ├── interfaces/                   # 3 interface (IRezervasyon, IAranabilir, IExportable)
│       ├── repository/                   # 5 JPA Repository
│       ├── service/                      # 4 servis sınıfı
│       ├── controller/                   # 5 REST Controller (18 endpoint)
│       └── exception/                    # 6 özel exception + GlobalExceptionHandler
│
├── frontend/                             # React Frontend
│   ├── src/
│   │   ├── App.jsx                       # Routing + guard yapısı
│   │   ├── pages/                        # 7 sayfa bileşeni
│   │   ├── components/                   # Layout ve yardımcı bileşenler
│   │   └── services/                     # 7 API servis dosyası
│   ├── package.json
│   └── vite.config.js
│
├── docs/                                 # Proje dokümentasyonu
├── README.md                             # Kurulum ve çalıştırma talimatları
└── LICENSE                               # MIT Lisansı
```

---

## 6. Toplam İstatistikler

| Metrik | Değer |
|--------|-------|
| Backend Java dosya sayısı | 39 |
| Frontend JSX/JS dosya sayısı | ~25 |
| REST API endpoint sayısı | 18 |
| Entity/Model sınıf sayısı | 12 |
| Interface sayısı | 3 |
| Özel exception sayısı | 6 |
| Sayfa bileşeni sayısı | 7 |
| Toplam API servisi (frontend) | 7 |

---

## 7. Kalan Görevler (10 Mayıs Final Teslime Kadar)

| Görev | Sorumlu | Öncelik |
|-------|---------|---------|
| Başlangıç verileri (seed data) kodlaması | Mustafa Mert | Yüksek |
| UI son rötuşlar ve responsive tasarım iyileştirmeleri | Ömer Faruk | Orta |
| Proje raporu yazımı | Tüm Ekip | Yüksek |
| Video sunum kaydı (10–15 dk) | Tüm Ekip | Yüksek |
