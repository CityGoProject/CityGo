package com.citygo.config;

/*
 * =============================================================
 * WebConfig.java — CORS ve Web Yapılandırma
 * =============================================================
 * Sorumlu: Muhammed
 *
 * Frontend (React, port 5173) ile backend (Spring Boot, port 8080)
 * arasındaki cross-origin isteklere izin veren yapılandırma sınıfı.
 *
 * Bu sınıf olmadan frontend'den backend'e istek atamazsınız!
 * Tarayıcı CORS policy hatası verir.
 *
 * Anotasyonlar:
 * - @Configuration
 *
 * Implements:
 * - WebMvcConfigurer
 *
 * Override Edilecek Metot:
 *
 * addCorsMappings(CorsRegistry registry):
 *     registry.addMapping("/api/**")           → Tüm /api/ endpoint'lerine izin ver
 *             .allowedOrigins("http://localhost:5173")  → React dev server adresi
 *             .allowedMethods("GET", "POST", "PUT", "DELETE")  → İzin verilen HTTP metotları
 *             .allowedHeaders("*")              → Tüm header'lara izin ver
 *             .allowCredentials(true);           → Cookie/credential desteği
 *
 * Not: Production'da allowedOrigins'i gerçek domain ile değiştirin.
 *      Development için localhost:5173 yeterlidir.
 */
