package com.citygo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS (Cross-Origin Resource Sharing) Yapılandırması
 *
 * Problem: Tarayıcılar güvenlik gereği, bir siteden (localhost:5173)
 * başka bir siteye (localhost:8080) istek atmayı ENGELLER.
 *
 * Çözüm: Bu sınıf, React frontend'in (port 5173) backend API'ye
 * (port 8080) erişmesine izin verir.
 *
 * Bu sınıf olmadan frontend "CORS policy error" hatası alır!
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")                          // /api/ ile başlayan tüm endpoint'ler
                .allowedOrigins("http://localhost:5173")         // React dev server adresi
                .allowedMethods("GET", "POST", "PUT", "DELETE") // İzin verilen HTTP metotları
                .allowedHeaders("*")                            // Tüm header'lara izin ver
                .allowCredentials(true);                        // Cookie/credential desteği
    }
}
