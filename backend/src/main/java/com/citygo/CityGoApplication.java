package com.citygo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CityGo Uygulamasının Giriş Noktası
 *
 * @SpringBootApplication anotasyonu 3 şeyi birden yapar:
 * 1. @Configuration    → "Bu sınıf ayar içerir"
 * 2. @EnableAutoConfiguration → "Spring, bağımlılıklara bakarak kendini otomatik ayarla"
 * 3. @ComponentScan    → "com.citygo paketi altındaki tüm sınıfları tara ve bul"
 */
@SpringBootApplication
public class CityGoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CityGoApplication.class, args);
    }
}
