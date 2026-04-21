import api from './api'

/*
 * =============================================================
 * exportService.js — Dışa Aktarma API Servisi
 * =============================================================
 * Sorumlu: Ömer Faruk
 *
 * Neden eklendi?
 * JSON/CSV indirme işlemi normal JSON cevabından farklıdır; blob olarak
 * alınır ve tarayıcıda dosya gibi indirilir.
 */

function downloadBlob(response, fallbackName) {
  const url = window.URL.createObjectURL(new Blob([response.data]))
  const link = document.createElement('a')
  link.href = url
  link.setAttribute('download', fallbackName)
  document.body.appendChild(link)
  link.click()
  link.remove()
  window.URL.revokeObjectURL(url)
}

export async function exportTicketsAsJson() {
  const response = await api.get('/export/biletler/json', { responseType: 'blob' })
  downloadBlob(response, 'citygo-biletler.json')
}

export async function exportTicketsAsCsv() {
  const response = await api.get('/export/biletler/csv', { responseType: 'blob' })
  downloadBlob(response, 'citygo-biletler.csv')
}
