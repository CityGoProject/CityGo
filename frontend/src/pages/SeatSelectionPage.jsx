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
      setError(err.response?.data?.hata || err.response?.data?.mesaj || 'Bilet oluşturulamadı.')
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
              <Stack spacing={3}>
                <SeatMap
                  koltuklar={seats}
                  selectedSeat={selectedSeat}
                  onSeatSelect={setSelectedSeat}
                />

                <Stack
                  direction={{ xs: 'column', sm: 'row' }}
                  spacing={2}
                  sx={{ alignItems: 'center' }}
                >
                  <Typography color="text.secondary" sx={{ mr: 'auto' }}>
                    Seçili koltuk: {selectedSeat?.koltukNo || 'Yok'}
                  </Typography>
                  <Button
                    variant="contained"
                    disabled={!selectedSeat || saving}
                    onClick={handleCreateTicket}
                  >
                    {saving ? 'Bilet Alınıyor...' : 'Bileti Al'}
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
