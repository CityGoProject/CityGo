package com.citygo.service;

import com.citygo.model.Bilet;
import com.citygo.repository.BiletRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.citygo.interfaces.IExportable;
import java.nio.charset.StandardCharsets;
import java.util.List;

/*
 * =============================================================
 * ExportService.java — Dışa Aktarım İş Mantığı
 * =============================================================
 * Sorumlu: Ömer Faruk
 *
 * IExportable interface'ini implement eden veri dışa aktarım servisi.
 *
 * Anotasyonlar:
 * - @Service
 *
 * Implements:
 * - IExportable
 *
 * Bağımlılıklar:
 * - BiletRepository
 *
 * Metotlar:
 *
 * - exportJSON(): byte[]
 *     Akış:
 *     1. Tüm biletleri BiletRepository.findAll() ile getir
 *     2. ObjectMapper (Jackson) kullanarak JSON'a çevir
 *     3. JSON string'ini byte dizisine çevirip döndür
 *     Örnek: objectMapper.writeValueAsBytes(biletler)
 *
 * - exportCSV(): byte[]
 *     Akış:
 *     1. Tüm biletleri getir
 *     2. StringBuilder ile CSV formatı oluştur
 *        - İlk satır: başlıklar (ID, Yolcu, Sefer, Koltuk, Tutar, Tarih, Durum)
 *        - Sonraki satırlar: veri
 *     3. CSV string'ini byte dizisine çevirip döndür
 *
 * Not: Jackson kütüphanesi spring-boot-starter-web ile otomatik gelir.
 *      Ekstra bağımlılık eklemeye gerek yok.
 */

@Service
public class ExportService implements IExportable {

    private final BiletRepository biletRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // Constructor Injection
    public ExportService(BiletRepository biletRepository) {
        this.biletRepository = biletRepository;
    }

    @Override
    public byte[] exportJSON() {
        try {
            List<Bilet> biletler = biletRepository.findAll();
            return objectMapper.writeValueAsBytes(biletler);
        } catch (Exception e) {
            throw new RuntimeException("JSON dışa aktarılamadı", e);
        }
    }

    @Override
    public byte[] exportCSV() {
        try {
            List<Bilet> biletler = biletRepository.findAll();
            StringBuilder sb = new StringBuilder();

            // Başlık satırı
            sb.append("ID,Yolcu_ID,Sefer_ID,Koltuk_No,Tutar,Tarih,Durum\n");

            for (Bilet bilet : biletler) {
                sb.append(bilet.getId()).append(",");
                sb.append(bilet.getYolcu() != null ? bilet.getYolcu().getId() : "N/A").append(",");
                sb.append(bilet.getSefer() != null ? bilet.getSefer().getId() : "N/A").append(",");
                sb.append(bilet.getKoltuk() != null ? bilet.getKoltuk().getKoltukNo() : "N/A").append(",");
                sb.append(bilet.getOdenenTutar()).append(",");
                sb.append(bilet.getOlusturmaTarihi()).append(",");
                sb.append(bilet.getDurum()).append("\n");
            }
            return sb.toString().getBytes(StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("CSV dışa aktarılamadı", e);
        }
    }
}