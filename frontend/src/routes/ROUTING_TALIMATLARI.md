# Routing Talimatları

Bu klasör, ileride route düzeni `App.jsx` içinden ayrılırsa kullanılmak üzere oluşturuldu.
Şu an mevcut `App.jsx` dosyasına dokunulmadı.

## Hedef Route Listesi

| Path | Sayfa | Erişim |
|------|-------|--------|
| `/login` | `LoginPage` | Public only |
| `/register` | `RegisterPage` | Public only |
| `/` | `HomePage` | Protected |
| `/search-results` | `SearchResultsPage` | Protected |
| `/seat-selection/:seferId` | `SeatSelectionPage` | Protected |
| `/my-tickets` | `MyTicketsPage` | Protected |
| `/admin` | `AdminPanel` | Admin only |

## Route Guard Notu

- `ProtectedRoute`: Oturum yoksa `/login` sayfasına yönlendirmeli.
- `PublicOnlyRoute`: Oturum varsa `/` sayfasına yönlendirmeli.
- `AdminRoute`: Kullanıcının admin rolü yoksa `/` sayfasına yönlendirmeli.

## Dikkat

React Router sürümü package dosyasında yeni görünüyor. Route API'si kullanılmadan önce mevcut sürümün dokümantasyonu kontrol edilmeli.
