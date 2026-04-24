        package com.citygo.service;
        import java.time.LocalDateTime;
        import com.citygo.interfaces.IAranabilir;
        import com.citygo.model.Sefer;
        import com.citygo.repository.SeferRepository;
        import org.springframework.stereotype.Service;
        import java.util.List;
        import java.util.stream.Collectors;

        /*
        * =============================================================
        * AramaService.java — Sefer Arama İş Mantığı (POLYMORPHISM - Overloading)
        * =============================================================
        * Sorumlu: Mert
        *
        * IAranabilir interface'ini implement eden sefer arama servisi.
        * 3 farklı overloaded ara() metodu ile POLYMORPHISM gösterilecek.
        *
        * Anotasyonlar:
        * - @Service
        *
        * Implements:
        * - IAranabilir
        *
        * Bağımlılıklar:
        * - SeferRepository
        *
        * Metotlar (hepsi IAranabilir'den geliyor):
        *
        * - ara(String kalkis, String varis): List<Sefer>
        *     → SeferRepository.findByKalkisNoktasiAndVarisNoktasi() çağır
        *
        * - ara(String kalkis, String varis, LocalDate tarih): List<Sefer>
        *     → Tarih filtresini de ekleyerek arama yap
        *     → Tarih: o günün 00:00 - 23:59 arasındaki seferleri getir
        *
        * - ara(String kalkis, String varis, LocalDate tarih, String aracTipi): List<Sefer>
        *     → Tarih + araç tipi filtreleri ile arama yap
        *     → Sonuçları araç tipine göre filtrele (stream().filter() kullanılabilir)
        *
        * Ek Metotlar:
        * - seferDetay(Long seferId): Sefer
        *     → Tek bir seferin detaylarını getir
        *
        * - tumSeferleriGetir(): List<Sefer>
        *     → Admin paneli için tüm seferleri listele
        *
        * ÖNEMLI: Bu 3 overloaded metot projedeki Polymorphism (Overloading)
        *         örneğinin temelini oluşturuyor!
        */


        @Service
        public class AramaService implements IAranabilir {

            private final SeferRepository seferRepository;

            // Constructor injection
            public AramaService(SeferRepository seferRepository) {
                this.seferRepository = seferRepository;
            }

            // Sadece güzergaha göre arama
            @Override
            public List<Sefer> ara(String kalkis, String varis) {
                return seferRepository.findByKalkisNoktasiAndVarisNoktasi(kalkis, varis);
            }

            // Güzergah ve tarihe göre arama
            @Override
            public List<Sefer> ara(String kalkis, String varis, LocalDateTime tarih) {
                return seferRepository.findByKalkisNoktasiAndVarisNoktasiAndKalkisZamani( kalkis, varis, tarih);
                }

            // Güzergah, tarih ve araç tipine göre arama
            @Override
            public List<Sefer> ara(String kalkis, String varis, LocalDateTime tarih, String aracTipi) {
                return seferRepository
                    .findByKalkisNoktasiAndVarisNoktasiAndKalkisZamani(kalkis, varis, tarih)
                    .stream()
                    .filter(sefer -> sefer.getArac().getAracTipi().equals(aracTipi))
                    .collect(Collectors.toList());
            }

            // Tek bir seferin detaylarını getirir
            public Sefer seferDetay(Long seferId) {
                return seferRepository.findById(seferId)
                    .orElseThrow(() -> new RuntimeException("Sefer bulunamadi: " + seferId));
            }

            // Tüm seferleri listeler
            public List<Sefer> tumSeferleriGetir() {
                return seferRepository.findAll();
            } 
        }