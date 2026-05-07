# CityGo Frontend

React 19, Vite 8 ve MUI 9 ile geliştirilen CityGo kullanıcı arayüzüdür.

## Çalıştırma

```bash
npm install
npm run dev
```

Varsayılan adres: `http://localhost:5173`

## Ortam Değişkenleri

`.env.example` dosyasını `.env` olarak kopyalayıp backend adresini değiştirebilirsiniz:

```bash
VITE_API_BASE_URL=http://localhost:8080/api
```

## Scriptler

```bash
npm run dev      # geliştirme sunucusu
npm run build    # production build kontrolü
npm run lint     # ESLint kontrolü
```

Not: Bu projede ayrı bir frontend test script'i tanımlı değildir.
