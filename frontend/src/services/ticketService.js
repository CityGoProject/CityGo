import api from './api'

/*
 * =============================================================
 * ticketService.js — Bilet API Servisi
 * =============================================================
 * Sorumlu: Ömer Faruk + Elif Feyza entegrasyonu
 *
 * Neden eklendi?
 * Bilet alma, bilet listeleme ve iptal işlemleri aynı backend modülüne
 * ait. Bu fonksiyonlar sayfaların daha temiz kalması için burada duruyor.
 */

export function createTicket({ yolcuId, seferId, koltukNo }) {
  return api.post('/biletler', { yolcuId, seferId, koltukNo })
}

export function getMyTickets(yolcuId) {
  return api.get('/biletler/benim', {
    params: { yolcuId },
  })
}

export function cancelTicket(biletId) {
  return api.put(`/biletler/${biletId}/iptal`)
}
