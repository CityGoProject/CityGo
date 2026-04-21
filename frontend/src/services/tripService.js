import api from './api'

/*
 * =============================================================
 * tripService.js — Sefer API Servisi
 * =============================================================
 * Sorumlu: Ömer Faruk
 *
 * Neden eklendi?
 * Sayfaların içinde tek tek "/seferler/ara" gibi endpoint yazarsak kod
 * dağılır. Bu dosya seferle ilgili API çağrılarını tek yerde toplar.
 */

export function searchTrips({ kalkis, varis, tarih, tip }) {
  return api.get('/seferler/ara', {
    // Query parametrelerini axios'a böyle verirsek boş alanları kolayca yönetiriz.
    params: {
      kalkis,
      varis,
      tarih: tarih || undefined,
      tip: tip || undefined,
    },
  })
}

export function getTripById(seferId) {
  return api.get(`/seferler/${seferId}`)
}

export function getTripSeats(seferId) {
  return api.get(`/seferler/${seferId}/koltuklar`)
}
