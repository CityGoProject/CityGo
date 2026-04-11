# 👤 Mustafa Mert Çevik — Görev Planı

**Rol:** Ulaşım Modeli & Sefer Yönetimi  
**Öğrenci No:** 24118080086

---

## 📁 Sorumlu Olduğun Dosyalar (Project Tree)

Aşağıdaki ağaçta **⭐ işaretli dosyalar** senin sorumluluğunda:

```
backend/
├── pom.xml                                          (Muhammed)
├── src/main/resources/
│   └── application.properties                       (Muhammed)
└── src/main/java/com/citygo/
    ├── CityGoApplication.java                       (Muhammed)
    ├── config/
    │   ├── WebConfig.java                           (Muhammed)
    │   └── DataSeeder.java                          ⭐ Başlangıç verileri (seed data)
    ├── model/
    │   ├── UlasimAraci.java                         ⭐ Abstract ulaşım aracı sınıfı
    │   ├── Ucak.java                                ⭐ Uçak alt sınıfı
    │   ├── Tren.java                                ⭐ Tren alt sınıfı
    │   ├── Otobus.java                              ⭐ Otobüs alt sınıfı
    │   ├── Kullanici.java                           (Muhammed)
    │   ├── Yolcu.java                               (Muhammed)
    │   ├── Admin.java                               (Muhammed)
    │   ├── Sefer.java                               ⭐ Sefer entity sınıfı
    │   ├── Koltuk.java                              ⭐ Koltuk entity sınıfı
    │   ├── Bilet.java                               (Elif Feyza)
    │   ├── BiletDurumu.java                         (Elif Feyza)
    │   └── KoltukTipi.java                          ⭐ Koltuk tipi enum
    ├── interfaces/
    │   ├── IRezervasyon.java                        (Elif Feyza)
    │   ├── IAranabilir.java                         ⭐ Arama interface'i
    │   └── IExportable.java                         (Ömer Faruk)
    ├── repository/
    │   ├── KullaniciRepository.java                 (Muhammed)
    │   ├── SeferRepository.java                     ⭐ Sefer veri erişimi
    │   ├── KoltukRepository.java                    ⭐ Koltuk veri erişimi
    │   ├── BiletRepository.java                     (Elif Feyza)
    │   └── UlasimAraciRepository.java               ⭐ Araç veri erişimi
    ├── service/
    │   ├── KullaniciService.java                    (Muhammed)
    │   ├── AramaService.java                        ⭐ Sefer arama servisi
    │   ├── RezervasyonService.java                  (Elif Feyza)
    │   └── ExportService.java                       (Ömer Faruk)
    ├── controller/
    │   ├── AuthController.java                      (Muhammed)
    │   ├── SeferController.java                     ⭐ Sefer arama API
    │   ├── BiletController.java                     (Elif Feyza)
    │   ├── AdminController.java                     (Muhammed + Ömer Faruk)
    │   └── ExportController.java                    (Ömer Faruk)
    └── exception/
        └── ...                                      (Elif Feyza)
```

> **Toplam sorumlu dosya sayısı:** 14 dosya (en fazla dosya sende!)

---

## 📅 Haftalık Görev Planı

### 🔴 HAFTA 1 — 7–13 Nisan (Öğrenme)

| # | Görev | Detay |
|---|-------|-------|
| 1 | Spring Boot temel eğitim | Entity, Repository, Service, Controller kavramlarını öğren |
| 2 | JPA anotasyonlarını öğren | `@Entity`, `@Id`, `@GeneratedValue`, `@ManyToOne`, `@OneToMany`, `@Inheritance` |

**Bu hafta kod yazmana gerek yok.** Muhammed altyapıyı kurarken sen öğrenmeye odaklan.

---

### 🟡 HAFTA 2 — 14–20 Nisan (Model & API Geliştirme)

> **⚠️ Bu hafta en yoğun haftandır! Aşağıdaki sırayla ilerle:**

| # | Dosya | Yapılacaklar | Detay |
|---|-------|-------------|-------|
| 3 | `UlasimAraci.java` | Abstract entity sınıfını yaz | `@Entity`, `@Inheritance(strategy=SINGLE_TABLE)`, `@DiscriminatorColumn(name="arac_tipi")`. **Alanlar (private):** id (Long), firma (String), model (String), kapasite (int), biletFiyati (double). **Abstract metotlar:** `hesaplaToplamFiyat(double temelFiyat)` ve `getAracTipi()`. Tüm alanlar getter/setter ile. |
| 4 | `Ucak.java` | UlasimAraci'dan miras al | `@DiscriminatorValue("UCAK")`. **Ek alanlar:** havaalaniVergiOrani (double), havaalani (String). **Override:** `hesaplaToplamFiyat()` → `temelFiyat + (temelFiyat * havaalaniVergiOrani)`. `getAracTipi()` → `"UCAK"`. |
| 5 | `Tren.java` | UlasimAraci'dan miras al | `@DiscriminatorValue("TREN")`. **Ek alanlar:** vagonTipi (String: "EKONOMI"/"BUSINESS"), hatTipi (String: "YHT"/"NORMAL"). **Override:** `hesaplaToplamFiyat()` → Business ise *1.5, YHT ise +%20. `getAracTipi()` → `"TREN"`. |
| 6 | `Otobus.java` | UlasimAraci'dan miras al | `@DiscriminatorValue("OTOBUS")`. **Ek alanlar:** ikramVar (boolean), ikramBedeli (double). **Override:** `hesaplaToplamFiyat()` → ikramVar ise `temelFiyat + ikramBedeli`. `getAracTipi()` → `"OTOBUS"`. |
| 7 | `KoltukTipi.java` | Enum yaz | Değerler: `STANDART`, `PREMIUM`. |
| 8 | `Koltuk.java` | Entity sınıfını yaz | **Alanlar:** id (Long), koltukNo (int), dolu (boolean, varsayılan false), tip (KoltukTipi), sefer (Sefer — `@ManyToOne`). |
| 9 | `Sefer.java` | Entity sınıfını yaz | **Alanlar:** id (Long), arac (UlasimAraci — `@ManyToOne`), kalkisNoktasi (String), varisNoktasi (String), kalkisZamani (LocalDateTime), varisZamani (LocalDateTime), koltuklar (List\<Koltuk\> — `@OneToMany(mappedBy="sefer", cascade=ALL)`). |
| 10 | `UlasimAraciRepository.java` | JPA Repository | `extends JpaRepository<UlasimAraci, Long>`. |
| 11 | `SeferRepository.java` | JPA Repository | `extends JpaRepository<Sefer, Long>`. Metotlar: `findByKalkisNoktasiAndVarisNoktasi()`, tarih aralığıyla arama. |
| 12 | `KoltukRepository.java` | JPA Repository | `extends JpaRepository<Koltuk, Long>`. Metotlar: `findBySefer_Id()`, `findBySefer_IdAndDolu()`, `findBySefer_IdAndKoltukNo()`. |
| 13 | `IAranabilir.java` | Interface tanımla | 3 overloaded `ara()` metodu: `ara(kalkis, varis)`, `ara(kalkis, varis, tarih)`, `ara(kalkis, varis, tarih, aracTipi)`. |
| 14 | `AramaService.java` | IAranabilir'i implement et | `@Service`, `implements IAranabilir`. 3 overloaded metodu repository çağrılarıyla doldur. Ek metotlar: `seferDetay()`, `tumSeferleriGetir()`. |
| 15 | `SeferController.java` | REST Controller | `@RestController`, `@RequestMapping("/api/seferler")`. Endpoint'ler: `GET /ara`, `GET /{id}`, `GET /{id}/koltuklar`. |

**Bu haftanın çıktısı:** Postman'dan `/api/seferler/ara?kalkis=Istanbul&varis=Ankara` çalışmalı. H2 Console'da `ulasim_araclari`, `seferler`, `koltuklar` tabloları görülmeli.

---

### 🟠 HAFTA 3 — 21–23 Nisan (Milestone / Demo Teslimi)

| # | Görev | Detay |
|---|-------|-------|
| 16 | Sefer arama API'si çalışır durumda | 3 overloaded arama metodu Postman'da test edilmiş olmalı |
| 17 | Frontend entegrasyonu | Ömer Faruk'un Arama Sonuçları sayfasıyla entegrasyon |

> **🚨 23 Nisan: ÇALIŞAN DEMO TESLİMİ — Sefer arama kesinlikle çalışmalı!**

---

### 🟢 HAFTA 4–5 — 24 Nisan – 10 Mayıs (Tamamlama)

| # | Dosya/Görev | Yapılacaklar |
|---|-------------|-------------|
| 18 | `DataSeeder.java` | Uygulama başladığında otomatik çalışan seed data. **Oluşturulacaklar:** Admin kullanıcı (admin@citygo.com), Yolcu kullanıcı (yolcu@citygo.com), 5-6 ulaşım aracı (2 uçak, 2 tren, 2 otobüs), 8-10 sefer (farklı güzergahlar, gelecekteki tarihlerle), Her sefer için koltuklar. `if (repository.count() == 0)` kontrolü yap! |
| 19 | Proje raporu | Tüm ekiple birlikte |
| 20 | Video sunum kaydı | Tüm ekiple birlikte |

> **🚨 10 Mayıs: FİNAL TESLİM**

---

## 🎯 Başlama Sırası (Nereden Başlamalıyım?)

```
1. UlasimAraci.java → abstract model (HER ŞEYİN TEMELİ)
2. Ucak.java → alt sınıf
3. Tren.java → alt sınıf
4. Otobus.java → alt sınıf
   ↓ (araç hiyerarşisi tamam)
5. KoltukTipi.java → enum
6. Koltuk.java → entity
7. Sefer.java → entity
   ↓ (tüm modeller tamam, H2'de tablolar oluştu)
8. UlasimAraciRepository.java → veri erişim
9. SeferRepository.java → veri erişim
10. KoltukRepository.java → veri erişim
    ↓ (veri erişim katmanı tamam)
11. IAranabilir.java → interface tanımı
12. AramaService.java → iş mantığı
13. SeferController.java → API endpoint'leri
    ↓ (sefer arama sistemi çalışıyor!)
14. DataSeeder.java → demo veriler (Hafta 4-5)
```

---

## 📌 OOP Prensipleri (Senin Modülünde)

| Prensip | Nerede Uygulanıyor |
|---------|-------------------|
| **Inheritance** | `UlasimAraci` → `Ucak`, `Tren`, `Otobus` miras hiyerarşisi |
| **Abstraction** | `UlasimAraci` abstract class, `IAranabilir` interface |
| **Polymorphism (Override)** | Her alt sınıfta `hesaplaToplamFiyat()` farklı çalışıyor |
| **Polymorphism (Overloading)** | `AramaService` → 3 farklı `ara()` metodu |
| **Encapsulation** | Tüm alanlar `private`, getter/setter ile erişim |

> 🎯 **Senin modülün projedeki en fazla OOP prensibini barındıran modül!**  
> Hocaya gösterirken bunları vurgula.

---

## 💡 Faydalı Kaynaklar

- [JPA Inheritance Stratejileri](https://www.baeldung.com/hibernate-inheritance)
- [Spring Data JPA Custom Queries](https://www.baeldung.com/spring-data-derived-queries)
- [Method Overloading in Java](https://www.baeldung.com/java-method-overloading-overriding)
