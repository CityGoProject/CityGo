package com.citygo.model;

public enum BiletDurumu{
    AKTIF, // bilet aktif, kullanılabilir durumda
    IPTAL_EDILDI, //kullanıcı aldı ama iptal etti
    KULLANILDI, // sefer tamamlandı bilet gecersiz durumda
}

/*
 * =============================================================
 * BiletDurumu.java — Bilet Durumu Enum
 * =============================================================
 * Sorumlu: Elif Feyza
 *
 * Bir biletin mevcut durumunu belirleyen enum sabitleri.
 *
 * Değerler:
 * - AKTIF         → Bilet aktif, kullanılabilir durumda
 * - IPTAL_EDILDI  → Bilet iptal edilmiş
 * - KULLANILDI    → Bilet kullanılmış (yolculuk tamamlanmış)
 *
 * Kullanımı: Bilet entity'sinde @Enumerated(EnumType.STRING) ile
 */
