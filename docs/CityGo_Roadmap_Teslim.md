# CENG106 OBJECT ORIENTED PROGRAMMING

# Project RoadMap

**Tarih:** 9 Nisan 2026

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

CityGo, farklı ulaşım türlerini (uçak, tren, otobüs) tek bir platformda birleştiren, Java tabanlı bir web uygulamasıdır. Kullanıcılar bu platform üzerinden sefer arayabilir, koltuk seçebilir, bilet satın alabilir ve biletlerini dijital olarak yönetebilir. Yönetici (admin) kullanıcıları ise seferleri, araçları ve rezervasyonları sistem üzerinden yönetebilir.

Proje, tarayıcı tabanlı bir web uygulaması olarak geliştirilecek olup; backend tarafında Spring Boot framework'ü, frontend tarafında ise React kütüphanesi kullanılacaktır. Veriler H2 gömülü ilişkisel veritabanında dosya tabanlı olarak saklanacaktır.

### 3.2 Projenin Amacı

Projenin temel amacı, Nesne Yönelimli Programlama (OOP) prensiplerinin tamamını gerçek dünya senaryolarına uygulayarak, modüler ve sürdürülebilir bir ulaşım rezervasyon altyapısı oluşturmaktır.

**Fonksiyonel Amaçlar:**

- Kullanıcıların sisteme kayıt olup giriş yapabilmesi
- Farklı ulaşım türleri arasında sefer arama ve filtreleme yapabilmesi
- Görsel koltuk haritası üzerinden koltuk seçimi yapabilmesi
- Bilet satın alma, görüntüleme ve iptal edebilmesi
- Yöneticilerin sefer ekleme, güncelleme, silme ve tüm rezervasyonları görüntüleyebilmesi

**Teknik Amaçlar (OOP Uygulaması):**

1. **Kalıtım (Inheritance):** `UlasimAraci` adlı abstract üst sınıftan `Ucak`, `Tren` ve `Otobus` alt sınıfları türetilecektir. Benzer şekilde `Kullanici` abstract sınıfından `Yolcu` ve `Admin` sınıfları miras alacaktır.

2. **Kapsülleme (Encapsulation):** Tüm sınıfların alanları `private` erişim belirteci ile tanımlanacak, dış erişim yalnızca `getter` ve `setter` metotları üzerinden sağlanacaktır.

3. **Çok Biçimlilik (Polymorphism):**
   - *Overriding:* Her ulaşım aracı kendi fiyat hesaplama algoritmasını (`hesaplaToplamFiyat()`) geçersiz kılacaktır. Örneğin uçak için havaalanı vergisi, otobüs için ikram bedeli eklenecektir.
   - *Overloading:* Sefer arama metodu farklı parametre kombinasyonlarıyla aşırı yüklenecektir (sadece güzergâh, güzergâh + tarih, güzergâh + tarih + araç tipi).

4. **Soyutlama (Abstraction):** `UlasimAraci` ve `Kullanici` sınıfları abstract olarak tanımlanacak; `IRezervasyon`, `IAranabilir` ve `IExportable` interface'leri kullanılacaktır.

5. **Hata Yönetimi (Exception Handling):** Geçersiz tarih seçimi, dolu kapasite, bulunamayan bilet gibi senaryolar için özel exception sınıfları tanımlanacak ve merkezi bir hata yönetim mekanizması (`GlobalExceptionHandler`) kurulacaktır.

---

## 4. Proje Tasarımı

### 4.1 Kullanılan Teknolojiler

| Katman | Teknoloji | Versiyon | Açıklama |
|--------|-----------|----------|----------|
| Programlama Dili | Java | 17 | Backend geliştirme |
| Backend Framework | Spring Boot | 3.x | REST API geliştirme, bağımlılık yönetimi |
| ORM | Spring Data JPA (Hibernate) | - | Nesne-ilişkisel eşleme, veritabanı işlemleri |
| Veritabanı | H2 Database (Dosya Tabanlı) | 2.x | Gömülü ilişkisel veritabanı, kurulum gerektirmez |
| Frontend | React | 18.x | Kullanıcı arayüzü geliştirme |
| UI Kütüphanesi | MUI (Material UI) | 5.x | Hazır, profesyonel UI bileşenleri |
| Build Aracı | Maven | - | Backend bağımlılık ve derleme yönetimi |
| Build Aracı (Frontend) | Vite | - | Frontend geliştirme sunucusu ve derleme |
| Versiyon Kontrolü | Git & GitHub | - | Kaynak kod yönetimi ve iş birliği |
| API İletişimi | REST API (JSON) | - | Frontend-backend arası veri alışverişi |

### 4.2 Mimari Tasarım

Proje, **katmanlı mimari (Layered Architecture)** prensibiyle tasarlanmıştır. Frontend ve backend birbirinden bağımsız iki ayrı uygulama olarak geliştirilecek, aralarındaki iletişim REST API üzerinden JSON formatında sağlanacaktır.

```
┌──────────────────────────────────────────────┐
│              KULLANICI (Tarayıcı)             │
│  ┌────────────────────────────────────────┐   │
│  │        React + MUI (Frontend)          │   │
│  │  Sayfalar: Giriş, Kayıt, Ana Sayfa,   │   │
│  │  Arama Sonuçları, Koltuk Seçimi,       │   │
│  │  Biletlerim, Admin Paneli              │   │
│  └───────────────┬────────────────────────┘   │
└──────────────────┼────────────────────────────┘
                   │ REST API (HTTP/JSON)
┌──────────────────┼────────────────────────────┐
│  ┌───────────────▼────────────────────────┐   │
│  │  Controller Katmanı (REST Endpoints)   │   │
│  │  AuthController, SeferController,      │   │
│  │  BiletController, AdminController      │   │
│  └───────────────┬────────────────────────┘   │
│  ┌───────────────▼────────────────────────┐   │
│  │  Service Katmanı (İş Mantığı)          │   │
│  │  KullaniciService, AramaService,       │   │
│  │  RezervasyonService, ExportService     │   │
│  │  Interfaces: IRezervasyon, IAranabilir  │   │
│  └───────────────┬────────────────────────┘   │
│  ┌───────────────▼────────────────────────┐   │
│  │  Repository Katmanı (Veri Erişim)      │   │
│  │  Spring Data JPA Repositories          │   │
│  └───────────────┬────────────────────────┘   │
│  ┌───────────────▼────────────────────────┐   │
│  │       H2 Veritabanı (Dosya Tabanlı)    │   │
│  └────────────────────────────────────────┘   │
│              SUNUCU (Spring Boot)              │
└───────────────────────────────────────────────┘
```

### 4.3 Sınıf Diyagramı (OOP Hiyerarşisi)

#### Ulaşım Araçları Hiyerarşisi

```
              ┌──────────────────────┐
              │    «abstract»        │
              │    UlasimAraci       │
              │──────────────────────│
              │ - id: Long           │
              │ - firma: String      │
              │ - model: String      │
              │ - kapasite: int      │
              │ - biletFiyati: double│
              │──────────────────────│
              │ + getters/setters    │
              │ + hesaplaToplamFiyat()│ ← abstract
              │ + getAracTipi()      │ ← abstract
              └──────────┬───────────┘
           ┌─────────────┼─────────────┐
           ▼             ▼             ▼
   ┌──────────────┐ ┌──────────┐ ┌──────────────┐
   │    Ucak      │ │   Tren   │ │   Otobus     │
   │──────────────│ │──────────│ │──────────────│
   │- havaalani   │ │-vagonTipi│ │- ikramVar    │
   │- vergiOrani  │ │-hatTipi  │ │- ikramBedeli │
   │──────────────│ │──────────│ │──────────────│
   │+hesapla...() │ │+hesap.() │ │+hesapla...() │
   └──────────────┘ └──────────┘ └──────────────┘
```

#### Kullanıcı Hiyerarşisi

```
              ┌──────────────────────┐
              │    «abstract»        │
              │     Kullanici        │
              │──────────────────────│
              │ - id: Long           │
              │ - ad: String         │
              │ - soyad: String      │
              │ - email: String      │
              │ - sifre: String      │
              │ - telefon: String    │
              │──────────────────────│
              │ + getters/setters    │
              └──────────┬───────────┘
                    ┌────┴────┐
                    ▼         ▼
            ┌────────────┐ ┌──────────┐
            │   Yolcu    │ │  Admin   │
            │────────────│ │──────────│
            │- tcNo      │ │- yetki   │
            │- biletler  │ │          │
            └────────────┘ └──────────┘
```

#### Interface Tanımları

```
«interface» IRezervasyon
─────────────────────────────────
+ rezervasyonYap(yolcuId, seferId, koltukNo): Bilet
+ rezervasyonIptal(biletId): boolean
+ rezervasyonlariGetir(yolcuId): List<Bilet>

«interface» IAranabilir
─────────────────────────────────
+ ara(kalkis, varis): List<Sefer>
+ ara(kalkis, varis, tarih): List<Sefer>                    ← Overloading
+ ara(kalkis, varis, tarih, aracTipi): List<Sefer>          ← Overloading

«interface» IExportable
─────────────────────────────────
+ exportJSON(): byte[]
+ exportCSV(): byte[]
```

#### Diğer Temel Sınıflar

```
┌─────────────────────────┐     ┌─────────────────────────┐
│         Sefer            │     │         Bilet            │
│─────────────────────────│     │─────────────────────────│
│ - id: Long               │     │ - id: Long               │
│ - arac: UlasimAraci      │     │ - yolcu: Yolcu           │
│ - kalkisNoktasi: String   │     │ - sefer: Sefer           │
│ - varisNoktasi: String    │     │ - koltuk: Koltuk         │
│ - kalkisZamani: DateTime  │     │ - odenenTutar: double    │
│ - varisZamani: DateTime   │     │ - olusturmaTarihi: Date  │
│ - koltuklar: List<Koltuk> │     │ - durum: BiletDurumu     │
└─────────────────────────┘     └─────────────────────────┘

┌─────────────────────────┐     ┌─────────────────────────┐
│         Koltuk           │     │  «enum» BiletDurumu     │
│─────────────────────────│     │─────────────────────────│
│ - id: Long               │     │   AKTIF                 │
│ - koltukNo: int           │     │   IPTAL_EDILDI          │
│ - dpieces: boolean        │     │   KULLANILDI            │
│ - tip: KoltukTipi         │     └─────────────────────────┘
└─────────────────────────┘
```

### 4.4 Veritabanı Tasarımı

H2 gömülü ilişkisel veritabanı dosya tabanlı modda kullanılacak olup, Spring Data JPA (Hibernate) ile nesne-ilişkisel eşleme (ORM) yapılacaktır. H2, kurulum gerektirmeden Spring Boot ile birinci sınıf entegrasyon sağlar. Java entity sınıfları `@Entity` anotasyonu ile işaretlenerek otomatik olarak veritabanı tablolarına dönüştürülecektir. Veritabanı verileri proje dizininde `.mv.db` dosyası olarak saklanacaktır.

**Temel Tablolar:**

| Tablo Adı | Açıklama | Temel Alanlar |
|-----------|----------|---------------|
| kullanicilar | Tüm kullanıcı bilgileri (Yolcu ve Admin) | id, ad, soyad, email, sifre, telefon, rol |
| ulasim_araclari | Ulaşım araçları (Uçak, Tren, Otobüs) | id, firma, model, kapasite, fiyat, arac_tipi, tip_ozel_alanlar |
| seferler | Sefer bilgileri | id, arac_id, kalkis, varis, kalkis_zamani, varis_zamani |
| koltuklar | Koltuk bilgileri | id, sefer_id, koltuk_no, dpieces, tip |
| biletler | Bilet/Rezervasyon bilgileri | id, yolcu_id, sefer_id, koltuk_id, tutar, tarih, durum |

### 4.5 Kullanıcı Arayüzü Tasarımı

Arayüz, React ve MUI (Material UI) kütüphanesi ile geliştirilecektir. MUI'nin hazır bileşenleri (buton, form, tablo, kart, dialog vb.) kullanılarak profesyonel ve kullanıcı dostu bir deneyim sağlanacaktır.

**Uygulama Sayfaları:**

| Sayfa | Açıklama | Temel Özellikler |
|-------|----------|-----------------|
| Giriş Sayfası | Kullanıcı girişi | Email/şifre formu, hata mesajları |
| Kayıt Sayfası | Yeni kullanıcı kaydı | Form validasyonu, otomatik giriş |
| Ana Sayfa | Sefer arama | Kalkış/varış seçimi, tarih, araç tipi filtresi |
| Arama Sonuçları | Sefer listesi | Sıralama, filtreleme, sefer detayları |
| Koltuk Seçimi | Görsel koltuk haritası | Dolu/boş koltuk gösterimi, seçim |
| Biletlerim | Kullanıcının biletleri | Bilet detayları, iptal butonu |
| Admin Paneli | Yönetim ekranı | Sefer CRUD, kullanıcı listesi, bilet yönetimi, istatistikler |

### 4.6 API Endpoint Tasarımı

| Metot | Endpoint | Açıklama |
|-------|----------|----------|
| POST | `/api/auth/register` | Yeni kullanıcı kaydı |
| POST | `/api/auth/login` | Kullanıcı girişi |
| POST | `/api/auth/logout` | Çıkış işlemi |
| GET | `/api/seferler/ara` | Sefer arama (query parametreleri ile) |
| GET | `/api/seferler/{id}` | Sefer detayı |
| GET | `/api/seferler/{id}/koltuklar` | Sefere ait koltuklar |
| POST | `/api/biletler` | Bilet satın alma |
| GET | `/api/biletler/benim` | Kullanıcının biletleri |
| PUT | `/api/biletler/{id}/iptal` | Bilet iptal etme |
| GET | `/api/admin/seferler` | Tüm seferler (admin) |
| POST | `/api/admin/seferler` | Yeni sefer ekleme (admin) |
| PUT | `/api/admin/seferler/{id}` | Sefer güncelleme (admin) |
| DELETE | `/api/admin/seferler/{id}` | Sefer silme (admin) |
| GET | `/api/admin/kullanicilar` | Tüm kullanıcılar (admin) |
| GET | `/api/admin/biletler` | Tüm biletler (admin) |
| GET | `/api/admin/istatistikler` | Dashboard istatistikleri (admin) |
| GET | `/api/export/biletler/json` | Bilet verilerini JSON olarak dışa aktar |
| GET | `/api/export/biletler/csv` | Bilet verilerini CSV olarak dışa aktar |

---


## 5. Proje Zaman Çizelgesi (Timeline)

### Hafta 1: 7 – 13 Nisan — Temel Kurulum & Öğrenme

| Görev | Sorumlu | Durum |
|-------|---------|-------|
| Spring Boot temel eğitim (tüm ekip) | Herkes | Devam ediyor |
| Spring Boot projesi oluşturma ve GitHub'a pushlanması | Muhammed | Devam ediyor |
| H2 veritabanı konfigürasyonu | Muhammed | Devam ediyor |
| React projesi kurulumu (Vite + MUI) | Ömer Faruk | Devam ediyor |
| Branch stratejisi belirleme ve ekibin branch'lara geçmesi | Muhammed | Devam ediyor |

### Hafta 2: 14 – 20 Nisan — Model & Temel API Geliştirme

| Görev | Sorumlu |
|-------|---------|
| Kullanici, Yolcu, Admin sınıfları ve AuthController | Muhammed |
| UlasimAraci, Ucak, Tren, Otobus, Sefer, Koltuk sınıfları | Mert |
| Bilet, BiletDurumu, IRezervasyon, RezervasyonService | Elif Feyza |
| Login, Register, Ana Sayfa (frontend) | Ömer Faruk |
| Tüm entity'lerin veritabanında test edilmesi | Herkes |

### Hafta 3: 21 – 23 Nisan — Milestone Teslimi

| Görev | Sorumlu |
|-------|---------|
| Sefer arama API'si çalışır durumda | Mert |
| Rezervasyon akışı çalışır durumda | Elif Feyza |
| Frontend-Backend entegrasyonu (en az arama + bilet alma) | Herkes |
| **23 Nisan: Çalışan demo teslimi** | **Tüm Ekip** |

### Hafta 4 – 5: 24 Nisan – 10 Mayıs — Tamamlama & Son Rötuşlar

| Görev | Sorumlu |
|-------|---------|
| Admin paneli (frontend + backend) | Ömer Faruk + Muhammed |
| Koltuk seçim ekranı (görsel harita) | Ömer Faruk |
| Export özelliği (JSON/CSV) | Ömer Faruk |
| Exception handling tamamlanması | Elif Feyza |
| Başlangıç verileri (seed data) | Mert |
| UI son rötuşlar ve responsive tasarım | Ömer Faruk |
| README dosyası | Muhammed |
| Proje raporu yazımı | Tüm Ekip |
| Video sunum kaydı | Tüm Ekip |
| **10 Mayıs: Final teslim** | **Tüm Ekip** |

---

## 6. Kullanılacak Araçlar

| Araç | Kullanım Amacı |
|------|----------------|
| IntelliJ IDEA / VS Code | Kod geliştirme ortamı |
| H2 Console | Gömülü veritabanı yönetim arayüzü (localhost:8080/h2-console) |
| Postman | API test etme |
| Git & GitHub | Versiyon kontrolü ve iş birliği |