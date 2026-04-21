package com.citygo.interfaces;

import com.citygo.model.Sefer;

import java.time.LocalDate;
import java.util.List;

public interface IAranabilir {

    List<Sefer> ara(String kalkis, String varis);

    List<Sefer> ara(String kalkis, String varis, LocalDate tarih);

    List<Sefer> ara(String kalkis, String varis, LocalDate tarih, String aracTipi);
}

/*
 * =============================================================
 * IAranabilir.java — Arama Interface (ABSTRACTION + POLYMORPHISM)
 * =============================================================
 * Sorumlu: Mert
 *
 * Sefer arama işlemlerini tanımlayan interface.
 * AramaService bu interface'i implement eder.
 *
 * Not:
 * Bu dosya daha önce sadece yorum içeriyordu. Frontend /api/seferler/ara
 * endpoint'ine istek atınca backend tarafında gerçek arama kontratı olmadığı
 * için endpoint zinciri tamamlanamıyordu. Bu yüzden interface gerçek metot
 * imzalarıyla dolduruldu.
 */
