package com.citygo.service;

/*
 * =============================================================
 * RezervasyonService.java — Rezervasyon İş Mantığı
 * =============================================================
 * Sorumlu: Elif Feyza
 *
 * IRezervasyon interface'ini implement eden bilet/rezervasyon servisi.
 *
 * Anotasyonlar:
 * - @Service
 *
 * Implements:
 * - IRezervasyon
 *
 * Bağımlılıklar:
 * - BiletRepository
 * - KoltukRepository
 * - SeferRepository
 * - KullaniciRepository
 *
 * Metotlar (IRezervasyon'dan geliyor):
 *
 * - rezervasyonYap(Long yolcuId, Long seferId, int koltukNo): Bilet
 *     Akış:
 *     1. Yolcu'yu ID ile bul (yoksa KullaniciBulunamadiException)
 *     2. Sefer'i ID ile bul (yoksa SeferBulunamadiException)
 *     3. Koltuk'u sefer + koltukNo ile bul
 *     4. Koltuk dolu mu kontrol et (doluysa KapasiteDoluException)
 *     5. Koltuğu "dolu" olarak işaretle
 *     6. UlasimAraci.hesaplaToplamFiyat() ile fiyat hesapla (POLYMORPHISM!)
 *     7. Bilet nesnesi oluştur (durum: AKTIF)
 *     8. Bilet'i kaydet ve döndür
 *
 * - rezervasyonIptal(Long biletId): boolean
 *     Akış:
 *     1. Bilet'i ID ile bul (yoksa BiletBulunamadiException)
 *     2. Bilet durumunu IPTAL_EDILDI yap
 *     3. İlgili koltuğu tekrar "boş" yap
 *     4. Kaydet ve true döndür
 *
 * - rezervasyonlariGetir(Long yolcuId): List<Bilet>
 *     → BiletRepository.findByYolcu_Id(yolcuId) çağır
 *
 * ÖNEMLI: hesaplaToplamFiyat() çağrısında araç tipine göre farklı
 *         hesaplama çalışacak — bu Polymorphism (Override) örneğidir!
 */
