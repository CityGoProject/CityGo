# CityGo Frontend Görev Planı

Bu dosya, backend tarafındaki `Sorumlu + yapılacaklar` yorum düzeninin frontend karşılığıdır.
Frontend tarafında yeni standart `src/components`, `src/pages` ve `src/services` yapısıdır.

## Genel Kural

- Frontend React + Vite + MUI ile geliştirilecek.
- Backend bağlantıları `http://localhost:8080/api` tabanı üzerinden yapılacak.
- Sayfa dosyaları `src/pages/` altında tutulacak.
- Tekrar kullanılabilir UI parçaları `src/components/` altında tutulacak.
- API çağrıları domain bazlı servis dosyalarına ayrılacak.
- Var olan `src/component/` klasörü eski deneme yapısı gibi duruyor; yeni geliştirmelerde `src/components/` kullanılmalı.

## Sorumluluk Dağılımı

| Alan | Dosyalar | Sorumlu |
|------|----------|---------|
| Auth ekranları | `LoginPage.jsx`, `RegisterPage.jsx` | Muhammed |
| Ana sayfa ve arama | `HomePage.jsx`, `SearchResultsPage.jsx`, `TripCard.jsx` | Ömer Faruk |
| Koltuk seçimi | `SeatSelectionPage.jsx`, `SeatMap.jsx` | Ömer Faruk |
| Biletlerim | `MyTicketsPage.jsx`, `TicketCard.jsx` | Ömer Faruk |
| Admin paneli | `AdminPanel.jsx` | Muhammed + Ömer Faruk |
| Layout | `Navbar.jsx`, `Footer.jsx` | Ömer Faruk |
| API servisleri | `auth`, `trip`, `ticket`, `admin`, `export` servisleri | İlgili modül sorumluları |

## Hedef Sayfa Akışı

1. Kullanıcı `/login` veya `/register` ile giriş yapar.
2. Giriş yapan kullanıcı `/` sayfasında sefer arar.
3. Arama sonucu `/search-results` sayfasında listelenir.
4. Kullanıcı bir sefer seçip `/seat-selection/:seferId` sayfasında koltuk seçer.
5. Bilet alındıktan sonra `/my-tickets` sayfasında biletlerini görür.
6. Admin kullanıcı `/admin` sayfasında sefer, kullanıcı, bilet ve istatistik yönetimi yapar.

## Backend Endpoint Referansı

| İşlem | Endpoint |
|-------|----------|
| Giriş | `POST /api/auth/login` |
| Kayıt | `POST /api/auth/register` |
| Çıkış | `POST /api/auth/logout` |
| Sefer arama | `GET /api/seferler/ara?kalkis=...&varis=...&tarih=...&tip=...` |
| Sefer detayı | `GET /api/seferler/{id}` |
| Koltuk listesi | `GET /api/seferler/{id}/koltuklar` |
| Bilet alma | `POST /api/biletler` |
| Biletlerim | `GET /api/biletler/benim?yolcuId=...` |
| Bilet iptal | `PUT /api/biletler/{id}/iptal` |
| Admin seferler | `GET/POST/PUT/DELETE /api/admin/seferler` |
| Admin kullanıcılar | `GET /api/admin/kullanicilar` |
| Admin biletler | `GET /api/admin/biletler` |
| Admin istatistik | `GET /api/admin/istatistikler` |
| Export JSON | `GET /api/export/biletler/json` |
| Export CSV | `GET /api/export/biletler/csv` |

## Güncel Durum Notu

- İlk düzenlemede yeni dosyalar sadece Türkçe talimat içeriyordu.
- Son düzenlemede bu dosyalar temel çalışan React bileşenlerine ve servis fonksiyonlarına çevrildi.
- Kod değişen yerlerde, arkadaşların neden değişiklik yapıldığını anlayabilmesi için kısa Türkçe yorumlar bırakıldı.
- Backend'de bazı controller dosyaları henüz yorum/taslak seviyesinde olduğu için frontend ekranları API hatalarını kullanıcıya anlaşılır şekilde gösterir.
