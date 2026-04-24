package com.citygo.interfaces;

/*
 * =============================================================
 * IExportable.java — Dışa Aktarım Interface (ABSTRACTION)
 * =============================================================
 * Sorumlu: Ömer Faruk
 *
 * Verileri farklı formatlarda dışa aktarma işlemlerini tanımlayan interface.
 * ExportService bu interface'i implement edecek.
 *
 * OOP Prensipleri:
 * - ABSTRACTION: Export mantığı soyutlanıyor
 *
 * Metotlar:
 * - exportJSON(): byte[]
 *     → Bilet verilerini JSON formatında byte dizisi olarak döndürür
 *     → Frontend'den dosya indirme için kullanılacak
 *
 * - exportCSV(): byte[]
 *     → Bilet verilerini CSV formatında byte dizisi olarak döndürür
 *     → Excel'de açılabilecek format
 *
 * Not: byte[] döndürülmesinin sebebi, Controller'da ResponseEntity<byte[]>
 *      ile dosya indirme (download) response'u oluşturabilmektir.
 */
public interface IExportable {
    public byte[] exportJSON();

    public byte[] exportCSV();
}