package com.citygo.interfaces;

/*
 * =============================================================
 * IAranabilir.java — Arama Interface (ABSTRACTION + POLYMORPHISM)
 * =============================================================
 * Sorumlu: Mert
 *
 * Sefer arama işlemlerini tanımlayan interface.
 * AramaService bu interface'i implement edecek.
 *
 * OOP Prensipleri:
 * - ABSTRACTION: Arama mantığı soyutlanıyor
 * - POLYMORPHISM (Overloading): Aynı isimde farklı parametreli metotlar
 *
 * Metotlar (3 farklı overloaded versiyon):
 *
 * - ara(String kalkis, String varis): List<Sefer>
 *     → Sadece kalkış ve varış noktasına göre arama
 *
 * - ara(String kalkis, String varis, LocalDate tarih): List<Sefer>
 *     → Kalkış, varış ve tarihe göre arama
 *
 * - ara(String kalkis, String varis, LocalDate tarih, String aracTipi): List<Sefer>
 *     → Kalkış, varış, tarih ve araç tipine göre arama
 *     → aracTipi: "UCAK", "TREN" veya "OTOBUS"
 *
 * NOT: Bu 3 metot method overloading örneğidir (Polymorphism).
 *      Hocaya gösterilecek önemli bir OOP prensip uygulaması!
 */
