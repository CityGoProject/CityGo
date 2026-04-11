# 👤 Elif Feyza Şengül — Görev Planı

**Rol:** Rezervasyon Sistemi & Hata Yönetimi  
**Öğrenci No:** 25118080004

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
    │   └── DataSeeder.java                          (Mert)
    ├── model/
    │   ├── UlasimAraci.java                         (Mert)
    │   ├── Ucak.java                                (Mert)
    │   ├── Tren.java                                (Mert)
    │   ├── Otobus.java                              (Mert)
    │   ├── Kullanici.java                           (Muhammed)
    │   ├── Yolcu.java                               (Muhammed)
    │   ├── Admin.java                               (Muhammed)
    │   ├── Sefer.java                               (Mert)
    │   ├── Koltuk.java                              (Mert)
    │   ├── Bilet.java                               ⭐ Bilet entity sınıfı
    │   ├── BiletDurumu.java                         ⭐ Bilet durumu enum
    │   └── KoltukTipi.java                          (Mert)
    ├── interfaces/
    │   ├── IRezervasyon.java                        ⭐ Rezervasyon interface'i
    │   ├── IAranabilir.java                         (Mert)
    │   └── IExportable.java                         (Ömer Faruk)
    ├── repository/
    │   ├── KullaniciRepository.java                 (Muhammed)
    │   ├── SeferRepository.java                     (Mert)
    │   ├── KoltukRepository.java                    (Mert)
    │   ├── BiletRepository.java                     ⭐ Bilet veri erişimi
    │   └── UlasimAraciRepository.java               (Mert)
    ├── service/
    │   ├── KullaniciService.java                    (Muhammed)
    │   ├── AramaService.java                        (Mert)
    │   ├── RezervasyonService.java                  ⭐ Rezervasyon iş mantığı
    │   └── ExportService.java                       (Ömer Faruk)
    ├── controller/
    │   ├── AuthController.java                      (Muhammed)
    │   ├── SeferController.java                     (Mert)
    │   ├── BiletController.java                     ⭐ Bilet API endpoint'leri
    │   ├── AdminController.java                     (Muhammed + Ömer Faruk)
    │   └── ExportController.java                    (Ömer Faruk)
    └── exception/                                   ⭐ TAMAMINI SEN YAPACAKSIN
        ├── KapasiteDoluException.java               ⭐ Koltuk dolu hatası
        ├── GecersizTarihException.java              ⭐ Geçersiz tarih hatası
        ├── BiletBulunamadiException.java            ⭐ Bilet bulunamadı hatası
        ├── KullaniciBulunamadiException.java        ⭐ Kullanıcı bulunamadı hatası
        ├── SeferBulunamadiException.java            ⭐ Sefer bulunamadı hatası
        └── GlobalExceptionHandler.java              ⭐ Merkezi hata yönetimi
```

> **Toplam sorumlu dosya sayısı:** 12 dosya

---

## 📅 Haftalık Görev Planı

### 🔴 HAFTA 1 — 7–13 Nisan (Öğrenme)

| # | Görev | Detay |
|---|-------|-------|
| 1 | Spring Boot temel eğitim | Entity, Repository, Service, Controller kavramlarını öğren |
| 2 | Exception Handling öğren | Java'da custom exception yazma, `@ControllerAdvice`, `@ExceptionHandler` |

**Bu hafta kod yazmana gerek yok.** Muhammed altyapıyı kurarken sen öğrenmeye odaklan.

---

### 🟡 HAFTA 2 — 14–20 Nisan (Model & Rezervasyon Sistemi)

> **⚠️ Senin modüllerin Muhammed ve Mert'in modüllerine bağımlı. Onlar Yolcu, Sefer, Koltuk sınıflarını yazdıktan sonra senin işin daha kolay olacak. Ama paralel çalışabilirsin — model + interface + exception aynı anda yazılabilir.**

| # | Dosya | Yapılacaklar | Detay |
|---|-------|-------------|-------|
| 3 | `BiletDurumu.java` | Enum yaz | 3 değer: `AKTIF`, `IPTAL_EDILDI`, `KULLANILDI`. Çok basit, 10 satır kod. |
| 4 | `Bilet.java` | Entity sınıfını yaz | `@Entity`. **Alanlar (private):** `id` (Long, @Id, @GeneratedValue), `yolcu` (Yolcu, @ManyToOne), `sefer` (Sefer, @ManyToOne), `koltuk` (Koltuk, @OneToOne), `odenenTutar` (double), `olusturmaTarihi` (LocalDate), `durum` (BiletDurumu, @Enumerated(STRING)). **Not:** `olusturmaTarihi`'ne `@PrePersist` ile otomatik tarih atanabilir. `yolcu` alanında `@JsonBackReference` kullan (sonsuz döngü önleme). |
| 5 | `IRezervasyon.java` | Interface tanımla | 3 metot: `rezervasyonYap(Long yolcuId, Long seferId, int koltukNo): Bilet`, `rezervasyonIptal(Long biletId): boolean`, `rezervasyonlariGetir(Long yolcuId): List<Bilet>`. |
| 6 | `BiletRepository.java` | JPA Repository | `extends JpaRepository<Bilet, Long>`. Metotlar: `findByYolcu_Id(Long yolcuId)`, `findByYolcu_IdAndDurum(Long yolcuId, BiletDurumu durum)`, `findBySefer_Id(Long seferId)`, `countByDurum(BiletDurumu durum)`. |
| 7 | `RezervasyonService.java` | IRezervasyon'u implement et | `@Service`, `implements IRezervasyon`. Bu dosya en önemli dosyan! Aşağıda detaylı akış var. |
| 8 | `BiletController.java` | REST Controller | `@RestController`, `@RequestMapping("/api/biletler")`. Endpoint'ler: `POST /` (bilet al), `GET /benim?yolcuId=...` (biletlerini getir), `PUT /{id}/iptal` (bilet iptal). |

#### 🔎 RezervasyonService Detaylı Akış

```
rezervasyonYap(yolcuId, seferId, koltukNo):
│
├─ 1. KullaniciRepository.findById(yolcuId)
│     └─ Bulunamazsa → throw KullaniciBulunamadiException
│
├─ 2. SeferRepository.findById(seferId)
│     └─ Bulunamazsa → throw SeferBulunamadiException
│
├─ 3. KoltukRepository.findBySefer_IdAndKoltukNo(seferId, koltukNo)
│     └─ Koltuk dolu mu kontrol et
│     └─ Dolu ise → throw KapasiteDoluException
│
├─ 4. Koltuk.setDolu(true)  →  koltuğu dolu işaretle
│
├─ 5. sefer.getArac().hesaplaToplamFiyat(biletFiyati)
│     └─ ★ POLYMORPHISM: Uçak ise vergi ekler, otobüs ise ikram ekler!
│
├─ 6. Bilet nesnesi oluştur:
│     ├─ yolcu = bulunan yolcu
│     ├─ sefer = bulunan sefer
│     ├─ koltuk = seçilen koltuk
│     ├─ odenenTutar = hesaplanan fiyat
│     ├─ olusturmaTarihi = LocalDate.now()
│     └─ durum = BiletDurumu.AKTIF
│
└─ 7. BiletRepository.save(bilet)  →  kaydet ve döndür
```

**Bu haftanın çıktısı:** Postman'dan `/api/biletler` POST ile bilet alınabilmeli, `/api/biletler/benim?yolcuId=1` ile biletler listelenebilmeli.

---

### 🟠 HAFTA 3 — 21–23 Nisan (Milestone / Demo Teslimi)

| # | Görev | Detay |
|---|-------|-------|
| 9 | Rezervasyon akışı çalışır durumda | Bilet al → Biletleri listele → Bilet iptal et akışı Postman'da test edilmiş olmalı |
| 10 | Frontend entegrasyonu | Ömer Faruk'un SeatSelection ve MyTickets sayfalarıyla entegrasyon |

> **🚨 23 Nisan: ÇALIŞAN DEMO TESLİMİ — Bilet alma ve iptal akışı kesinlikle çalışmalı!**

---

### 🟢 HAFTA 4–5 — 24 Nisan – 10 Mayıs (Exception Handling Tamamlama)

| # | Dosya | Yapılacaklar | Detay |
|---|-------|-------------|-------|
| 11 | `KapasiteDoluException.java` | Custom exception yaz | `extends RuntimeException`. Constructor: `super(mesaj)`. Örnek: `"Koltuk 12 zaten dolu!"` |
| 12 | `GecersizTarihException.java` | Custom exception yaz | `extends RuntimeException`. Geçmiş tarih seçimi, yanlış format vb. |
| 13 | `BiletBulunamadiException.java` | Custom exception yaz | `extends RuntimeException`. ID ile bilet bulunamadığında. |
| 14 | `KullaniciBulunamadiException.java` | Custom exception yaz | `extends RuntimeException`. Email/ID ile kullanıcı bulunamadığında. |
| 15 | `SeferBulunamadiException.java` | Custom exception yaz | `extends RuntimeException`. ID ile sefer bulunamadığında. |
| 16 | `GlobalExceptionHandler.java` | Merkezi hata yönetimi | Aşağıda detaylı anlatım var. |
| 17 | Validasyon ekleme | Tüm servislere input validasyonu | Tarih geçmişte mi? Email formatı doğru mu? Kapasite aşıldı mı? |
| 18 | Proje raporu | Tüm ekiple birlikte |
| 19 | Video sunum | Tüm ekiple birlikte |

#### 🔎 GlobalExceptionHandler Detaylı Yapısı

```java
// @RestControllerAdvice ile sınıfı işaretle
// Her exception tipi için bir handler metodu yaz:

@ExceptionHandler(KapasiteDoluException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
→ return Map.of(
    "hata", "Kapasite Dolu",
    "mesaj", ex.getMessage(),
    "zaman", LocalDateTime.now()
  )

@ExceptionHandler(GecersizTarihException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)  // 400

@ExceptionHandler(BiletBulunamadiException.class)
@ResponseStatus(HttpStatus.NOT_FOUND)  // 404

@ExceptionHandler(KullaniciBulunamadiException.class)
@ResponseStatus(HttpStatus.NOT_FOUND)  // 404

@ExceptionHandler(SeferBulunamadiException.class)
@ResponseStatus(HttpStatus.NOT_FOUND)  // 404

@ExceptionHandler(Exception.class)
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
→ Beklenmeyen hatalar için genel handler
```

> **🚨 10 Mayıs: FİNAL TESLİM**

---

## 🎯 Başlama Sırası (Nereden Başlamalıyım?)

```
 ── HAFTA 2 (Paralel çalış!) ──
1. BiletDurumu.java → enum (çok basit, 5 dk)
2. Bilet.java → entity sınıfı
3. IRezervasyon.java → interface tanımı
   ↓ (model + interface hazır)
4. BiletRepository.java → veri erişim
5. RezervasyonService.java → İŞ MANTIĞI (en önemli dosya!)
6. BiletController.java → API endpoint'leri
   ↓ (rezervasyon sistemi çalışıyor!)
 ── HAFTA 3 (Demo) ──
7. Postman ile test et
8. Frontend entegrasyonu
   ↓ (demo hazır)
 ── HAFTA 4-5 ──
9.  KapasiteDoluException.java
10. GecersizTarihException.java
11. BiletBulunamadiException.java
12. KullaniciBulunamadiException.java
13. SeferBulunamadiException.java
    ↓ (custom exception'lar hazır)
14. GlobalExceptionHandler.java → hepsini yakala
15. Servislere validasyon ekle
```

> **💡 İpucu:** Exception sınıflarını Hafta 2'de bile yazabilirsin — çok kısa dosyalar (5-10 satır). Ama `GlobalExceptionHandler` ve validasyon mantığını Hafta 4-5'e bırakabilirsin.

---

## 📌 OOP Prensipleri (Senin Modülünde)

| Prensip | Nerede Uygulanıyor |
|---------|-------------------|
| **Abstraction** | `IRezervasyon` interface tanımı — soyut rezervasyon kontratı |
| **Encapsulation** | `Bilet` entity — tüm alanlar `private`, getter/setter |
| **Polymorphism (dolaylı)** | `RezervasyonService` → `hesaplaToplamFiyat()` çağrısında araç tipine göre farklı hesaplama çalışır (Mert'in yazdığı override'lar) |
| **Exception Handling** | 5 özel exception + GlobalExceptionHandler |

---

## ⚠️ Bağımlılıkların (Kimin Neyi Bitirmesini Bekliyorsun?)

| Bağımlılık | Kim Yapacak | Ne Zaman Hazır |
|-----------|-------------|----------------|
| `Yolcu.java` | Muhammed | Hafta 2 başı |
| `Sefer.java`, `Koltuk.java` | Mert | Hafta 2 başı |
| `KullaniciRepository` | Muhammed | Hafta 2 |
| `SeferRepository`, `KoltukRepository` | Mert | Hafta 2 |

> **💡 İpucu:** Model ve interface dosyalarını (BiletDurumu, Bilet, IRezervasyon) Muhammed ve Mert'i beklemeden yazabilirsin. Import'lar kırmızı çıkabilir ama onlar dosyalarını pushladığında düzelir.

---

## 💡 Faydalı Kaynaklar

- [Spring Boot Exception Handling](https://www.baeldung.com/exception-handling-for-rest-with-spring)
- [@ControllerAdvice Kullanımı](https://www.baeldung.com/spring-controllers)
- [JPA Entity İlişkileri](https://www.baeldung.com/jpa-hibernate-associations)
- [Custom Exception Yazma](https://www.baeldung.com/java-new-custom-exception)
