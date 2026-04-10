package com.citygo.interfaces;

/*
 * =============================================================
 * IRezervasyon.java — Rezervasyon Interface (ABSTRACTION)
 * =============================================================
 * Sorumlu: Elif Feyza
 *
 * Rezervasyon işlemlerini tanımlayan interface.
 * RezervasyonService bu interface'i implement edecek.
 *
 * OOP Prensipleri:
 * - ABSTRACTION: İş mantığı soyutlanıyor, detaylar Service'te
 *
 * Metotlar:
 * - rezervasyonYap(Long yolcuId, Long seferId, int koltukNo): Bilet
 *     → Belirtilen yolcu için, belirtilen seferde koltuk rezerve eder
 *     → Koltuk doluysa KapasiteDoluException fırlatılmalı
 *     → Bilet nesnesi oluşturup döndürür
 *
 * - rezervasyonIptal(Long biletId): boolean
 *     → Bileti iptal eder (durum → IPTAL_EDILDI)
 *     → Koltuğu tekrar boşa çevirir
 *     → Bilet bulunamazsa BiletBulunamadiException fırlatılmalı
 *
 * - rezervasyonlariGetir(Long yolcuId): List<Bilet>
 *     → Bir yolcunun tüm biletlerini listeler
 */
