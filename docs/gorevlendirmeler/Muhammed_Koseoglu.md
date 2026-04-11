# 👤 Muhammed Köseoğlu — Görev Planı

**Rol:** Kullanıcı Yönetimi & Proje Altyapısı + Koordinasyon  
**Öğrenci No:** 24118080049

---

## 📁 Sorumlu Olduğun Dosyalar (Project Tree)

Aşağıdaki ağaçta **⭐ işaretli dosyalar** senin sorumluluğunda:

```
backend/
├── pom.xml                                          ⭐ Bağımlılık yönetimi
├── src/main/resources/
│   └── application.properties                       ⭐ Uygulama konfigürasyonu
└── src/main/java/com/citygo/
    ├── CityGoApplication.java                       ⭐ Ana uygulama sınıfı
    ├── config/
    │   ├── WebConfig.java                           ⭐ CORS ayarları
    │   └── DataSeeder.java                          (Mert)
    ├── model/
    │   ├── UlasimAraci.java                         (Mert)
    │   ├── Ucak.java                                (Mert)
    │   ├── Tren.java                                (Mert)
    │   ├── Otobus.java                              (Mert)
    │   ├── Kullanici.java                           ⭐ Abstract kullanıcı sınıfı
    │   ├── Yolcu.java                               ⭐ Yolcu alt sınıfı
    │   ├── Admin.java                               ⭐ Admin alt sınıfı
    │   ├── Sefer.java                               (Mert)
    │   ├── Koltuk.java                              (Mert)
    │   ├── Bilet.java                               (Elif Feyza)
    │   ├── BiletDurumu.java                         (Elif Feyza)
    │   └── KoltukTipi.java                          (Mert)
    ├── interfaces/
    │   ├── IRezervasyon.java                        (Elif Feyza)
    │   ├── IAranabilir.java                         (Mert)
    │   └── IExportable.java                         (Ömer Faruk)
    ├── repository/
    │   ├── KullaniciRepository.java                 ⭐ Kullanıcı veri erişimi
    │   ├── SeferRepository.java                     (Mert)
    │   ├── KoltukRepository.java                    (Mert)
    │   ├── BiletRepository.java                     (Elif Feyza)
    │   └── UlasimAraciRepository.java               (Mert)
    ├── service/
    │   ├── KullaniciService.java                    ⭐ Kullanıcı iş mantığı
    │   ├── AramaService.java                        (Mert)
    │   ├── RezervasyonService.java                  (Elif Feyza)
    │   └── ExportService.java                       (Ömer Faruk)
    ├── controller/
    │   ├── AuthController.java                      ⭐ Kimlik doğrulama API
    │   ├── SeferController.java                     (Mert)
    │   ├── BiletController.java                     (Elif Feyza)
    │   ├── AdminController.java                     ⭐ Admin paneli API (Ömer Faruk ile birlikte)
    │   └── ExportController.java                    (Ömer Faruk)
    └── exception/
        ├── ...                                      (Elif Feyza)
        └── GlobalExceptionHandler.java              (Elif Feyza)
```

> **Toplam sorumlu dosya sayısı:** 10 dosya

---

## 📅 Haftalık Görev Planı

### 🔴 HAFTA 1 — 7–13 Nisan (Temel Kurulum)

> **Bu hafta bitirmesi gereken görevler altyapı görevleridir. Ekibin çalışabilmesi için bunların hazır olması şart!**

| # | Dosya | Yapılacaklar | Öncelik |
|---|-------|-------------|---------|
| 1 | `pom.xml` | Tüm bağımlılıkları ekle: `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `h2`, `spring-boot-starter-validation`, `spring-boot-devtools` | 🔴 İLK İŞ |
| 2 | `application.properties` | H2 veritabanı ayarları (dosya tabanlı mod), JPA/Hibernate ayarları, H2 Console erişimi aktifleştirme | 🔴 İLK İŞ |
| 3 | `CityGoApplication.java` | `@SpringBootApplication` ile ana sınıfı yaz, uygulamayı başlat ve test et | 🔴 İLK İŞ |
| 4 | `WebConfig.java` | CORS konfigürasyonu — frontend (localhost:5173) erişimine izin ver | 🔴 İLK İŞ |
| 5 | — | GitHub'da branch stratejisi belirle, `dev` ve kişisel branch'ları oluştur | 🔴 İLK İŞ |

**Bu haftanın çıktısı:** Uygulama `mvn spring-boot:run` ile ayağa kalkmalı, H2 Console erişilebilir olmalı.

---

### 🟡 HAFTA 2 — 14–20 Nisan (Model & API Geliştirme)

| # | Dosya | Yapılacaklar | Detay |
|---|-------|-------------|-------|
| 6 | `Kullanici.java` | Abstract entity sınıfını yaz | `@Entity`, `@Inheritance(strategy=SINGLE_TABLE)`, `@DiscriminatorColumn(name="rol")`. Alanlar: id, ad, soyad, email, sifre, telefon. Tümü `private` + getter/setter. |
| 7 | `Yolcu.java` | Kullanici'dan miras alan sınıf | `@DiscriminatorValue("YOLCU")`. Ek alanlar: tcNo, biletler (`@OneToMany`). |
| 8 | `Admin.java` | Kullanici'dan miras alan sınıf | `@DiscriminatorValue("ADMIN")`. Ek alan: yetki. |
| 9 | `KullaniciRepository.java` | JPA Repository interface | `extends JpaRepository<Kullanici, Long>`. Metotlar: `findByEmail()`, `findByEmailAndSifre()`, `existsByEmail()`. |
| 10 | `KullaniciService.java` | İş mantığı servisi | `@Service`. Metotlar: `kayitOl()`, `girisYap()`, `kullaniciBul()`, `tumKullanicilariGetir()`. |
| 11 | `AuthController.java` | REST Controller | `@RestController`, `@RequestMapping("/api/auth")`. Endpoint'ler: `POST /register`, `POST /login`, `POST /logout`. |

**Bu haftanın çıktısı:** Postman'dan `/api/auth/register` ve `/api/auth/login` çalışmalı. H2 Console'da `kullanicilar` tablosu görülmeli.

**⚠️ Test:** Hafta sonuna kadar Postman ile tüm auth endpoint'lerini test et, ekibe çalıştığını göster.

---

### 🟠 HAFTA 3 — 21–23 Nisan (Milestone / Demo Teslimi)

| # | Görev | Detay |
|---|-------|-------|
| 12 | Frontend-Backend entegrasyonu | Ömer Faruk'un yaptığı Login/Register sayfalarının senin auth API'ne bağlanması |
| 13 | Diğer ekip arkadaşlarının kodlarını review et | Merge conflict'leri çöz, entegrasyonu sağla |

> **🚨 23 Nisan: ÇALIŞAN DEMO TESLİMİ — Auth sistemi kesinlikle hazır olmalı!**

---

### 🟢 HAFTA 4–5 — 24 Nisan – 10 Mayıs (Tamamlama)

| # | Dosya/Görev | Yapılacaklar |
|---|-------------|-------------|
| 14 | `AdminController.java` | Admin paneli backend endpoint'leri: `GET /api/admin/seferler`, `POST`, `PUT`, `DELETE` sefer CRUD. `GET /api/admin/kullanicilar`, `GET /api/admin/biletler`, `GET /api/admin/istatistikler`. Ömer Faruk ile birlikte çalışılacak. |
| 15 | `README.md` | Proje açıklaması, kurulum talimatları, teknoloji listesi (zaten oluşturuldu, güncellenecek) |
| 16 | Proje raporu | Tüm ekiple birlikte |
| 17 | Video sunum kaydı | Tüm ekiple birlikte |

> **🚨 10 Mayıs: FİNAL TESLİM**

---

## 🎯 Başlama Sırası (Nereden Başlamalıyım?)

```
1. pom.xml → bağımlılıkları ekle
2. application.properties → H2 + JPA ayarları
3. CityGoApplication.java → uygulamayı başlat
4. WebConfig.java → CORS aç
   ↓ (uygulama ayağa kalktı)
5. Kullanici.java → abstract model
6. Yolcu.java → alt sınıf
7. Admin.java → alt sınıf
   ↓ (modeller hazır, H2'de tablolar oluştu)
8. KullaniciRepository.java → veri erişim
9. KullaniciService.java → iş mantığı
10. AuthController.java → API endpoint'leri
    ↓  (auth sistemi çalışır durumda)
11. AdminController.java → admin API (Hafta 4-5)
```

---

## 📌 OOP Prensipleri (Senin Modülünde)

| Prensip | Nerede Uygulanıyor |
|---------|-------------------|
| **Inheritance** | `Kullanici` → `Yolcu`, `Admin` miras hiyerarşisi |
| **Abstraction** | `Kullanici` abstract class olarak tanımlanması |
| **Encapsulation** | Tüm alanlar `private`, getter/setter ile erişim |

---

## 💡 Faydalı Kaynaklar

- [Spring Boot + H2 Kurulumu](https://spring.io/guides/gs/accessing-data-jpa/)
- [Spring Data JPA Repository](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Spring Boot REST Controller](https://spring.io/guides/gs/rest-service/)
