import { Alert, Box, Button, CircularProgress, Paper, Stack, Typography } from '@mui/material'
import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import SeatMap from '../components/seats/SeatMap'
import { getStoredUser } from '../services/auth'
import { createTicket } from '../services/ticketService'
import { getTripById, getTripSeats } from '../services/tripService'

function SeatSelectionPage() {
  const { seferId } = useParams()
  const navigate = useNavigate()
  const user = getStoredUser()
  const [trip, setTrip] = useState(null)
  const [seats, setSeats] = useState([])
  const [selectedSeat, setSelectedSeat] = useState(null)
  const [loading, setLoading] = useState(true)
  const [saving, setSaving] = useState(false)
  const [error, setError] = useState('')

  useEffect(() => {
    async function loadSeatPage() {
      setLoading(true)
      setError('')

      try {
        /*
         * Sefer detayı ve koltukları aynı sayfada gerekiyor. İkisini beraber
         * çekince ekran daha hızlı hazır olur.
         */
        const [tripResponse, seatsResponse] = await Promise.all([
          getTripById(seferId),
          getTripSeats(seferId),
        ])
        setTrip(tripResponse.data)
        setSeats(Array.isArray(seatsResponse.data) ? seatsResponse.data : [])
      } catch (err) {
        setError(err.response?.data?.hata || 'Koltuk bilgileri getirilemedi. Backend endpoint hazır olmayabilir.')
      } finally {
        setLoading(false)
      }
    }

    loadSeatPage()
  }, [seferId])

  const handleCreateTicket = async () => {
    if (!selectedSeat) {
      setError('Lütfen bir koltuk seçin.')
      return
    }

    setSaving(true)
    setError('')

    try {
      await createTicket({
        yolcuId: user.id,
        seferId: Number(seferId),
        koltukNo: selectedSeat.koltukNo,
      })
      navigate('/my-tickets')
    } catch (err) {
      const errorMsg = err.response?.data?.hata || err.response?.data?.mesaj || 'Bilet oluşturulamadı.'
      if (errorMsg.includes('Yolcu Bulunamadi')) {
        setError('Oturumunuzun süresi dolmuş veya veritabanı sıfırlanmış olabilir. Lütfen ÇIKIŞ yapıp tekrar GİRİŞ yapın.')
      } else {
        setError(errorMsg)
      }
    } finally {
      setSaving(false)
    }
  }

  return (
    <Box sx={{ minHeight: '100vh', display: 'flex', flexDirection: 'column', backgroundColor: '#f6f8fb' }}>
      <Box sx={{ flex: 1, maxWidth: 980, mx: 'auto', px: 3, py: 5 }}>
        <Stack spacing={3}>
          <Paper sx={{ p: 3, borderRadius: 3 }} variant="outlined">
            <Typography variant="h4" fontWeight={800}>
              Koltuk Seçimi
            </Typography>
            <Typography color="text.secondary">
              {trip ? `${trip.kalkisNoktasi} → ${trip.varisNoktasi}` : 'Sefer bilgisi yükleniyor'}
            </Typography>
          </Paper>

          {loading && (
            <Stack sx={{ py: 6, alignItems: 'center' }}>
              <CircularProgress />
            </Stack>
          )}

          {error && <Alert severity="error">{error}</Alert>}

          {!loading && (
            <Paper sx={{ p: 3, borderRadius: 3 }} variant="outlined">
              <Stack spacing={4}>
                <Box>
                  <Typography variant="h6" gutterBottom fontWeight={700}>
                    Koltuk Planı
                  </Typography>
                  <SeatMap
                    koltuklar={seats}
                    selectedSeat={selectedSeat}
                    onSeatSelect={setSelectedSeat}
                    vehicleType={trip?.arac?.aracTipi}
                  />
                </Box>

                <Stack
                  direction={{ xs: 'column', md: 'row' }}
                  spacing={3}
                  sx={{ 
                    alignItems: 'center', 
                    p: 2, 
                    bgcolor: 'primary.main', 
                    color: 'white', 
                    borderRadius: 3,
                    boxShadow: '0 4px 20px rgba(2, 132, 199, 0.2)'
                  }}
                >
                  <Box sx={{ mr: 'auto' }}>
                    <Typography variant="body2" sx={{ opacity: 0.9 }}>Seçili Koltuk</Typography>
                    <Typography variant="h5" fontWeight={900}>
                      {selectedSeat?.koltukNo || '--'}
                    </Typography>
                  </Box>
                  
                  <Box sx={{ textAlign: { xs: 'center', md: 'right' } }}>
                    <Typography variant="body2" sx={{ opacity: 0.9 }}>Toplam Tutar</Typography>
                    <Typography variant="h5" fontWeight={900}>
                      {selectedSeat ? `${trip?.arac?.biletFiyati || 0} TL` : '0 TL'}
                    </Typography>
                  </Box>

                  <Button
                    variant="contained"
                    size="large"
                    disabled={!selectedSeat || saving}
                    onClick={handleCreateTicket}
                    sx={{ 
                      bgcolor: 'white', 
                      color: 'primary.main',
                      px: 4,
                      fontWeight: 800,
                      '&:hover': { bgcolor: '#f0f0f0' },
                      '&.Mui-disabled': { bgcolor: 'rgba(255,255,255,0.3)', color: 'white' }
                    }}
                  >
                    {saving ? 'Bilet Alınıyor...' : 'ÖDEME YAP VE BİLETİ AL'}
                  </Button>
                </Stack>
              </Stack>
            </Paper>
          )}
        </Stack>
      </Box>
    </Box>
  )
}

export default SeatSelectionPage
