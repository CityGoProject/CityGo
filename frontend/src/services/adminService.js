import api from './api'

/*
 * =============================================================
 * adminService.js — Admin API Servisi
 * =============================================================
 * Sorumlu: Muhammed + Ömer Faruk
 *
 * Neden eklendi?
 * AdminPanel çok fazla endpoint kullanacak. Endpointleri burada toplayınca
 * panel dosyasında sadece ekran mantığı kalır.
 */

export function getAdminStats() {
  return api.get('/admin/istatistikler')
}

export function getAllTrips() {
  return api.get('/admin/seferler')
}

export function createTrip(payload) {
  return api.post('/admin/seferler', payload)
}

export function updateTrip(seferId, payload) {
  return api.put(`/admin/seferler/${seferId}`, payload)
}

export function deleteTrip(seferId) {
  return api.delete(`/admin/seferler/${seferId}`)
}

export function getAllUsers() {
  return api.get('/admin/kullanicilar')
}

export function getAllTickets() {
  return api.get('/admin/biletler')
}
