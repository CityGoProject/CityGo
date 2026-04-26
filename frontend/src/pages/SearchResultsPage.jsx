import { Alert, Box, Button, CircularProgress, Paper, Stack, Typography } from '@mui/material'
import { useEffect, useState } from 'react'
import { Link as RouterLink, useNavigate, useSearchParams } from 'react-router-dom'
import TripCard from '../components/trips/TripCard'
import { searchTrips } from '../services/tripService'

function SearchResultsPage() {
  const [searchParams] = useSearchParams()
  const navigate = useNavigate()
  const [trips, setTrips] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const kalkis = searchParams.get('kalkis') || ''
  const varis = searchParams.get('varis') || ''
  const tarih = searchParams.get('tarih') || ''
  const tip = searchParams.get('tip') || ''

  useEffect(() => {
    async function loadTrips() {
      if (!kalkis || !varis) {
        navigate('/', { replace: true })
        return
      }

      setLoading(true)
      setError('')

      try {
        const response = await searchTrips({ kalkis, varis, tarih, tip })
        setTrips(Array.isArray(response.data) ? response.data : [])
      } catch (err) {
        setError(err.response?.data?.hata || 'Seferler getirilemedi. Backend endpoint hazır olmayabilir.')
      } finally {
        setLoading(false)
      }
    }

    loadTrips()
  }, [kalkis, varis, tarih, tip, navigate])

  return (
    <Box sx={{ minHeight: '100vh', display: 'flex', flexDirection: 'column', backgroundColor: '#f6f8fb' }}>
      <Box sx={{ flex: 1, maxWidth: 980, mx: 'auto', px: 3, py: 5 }}>
        <Stack spacing={3}>
          <Paper sx={{ p: 3, borderRadius: 3 }} variant="outlined">
            {/* Arama kriterleri URL'den okunuyor; sayfa yenilense bile sonuç ekranı aynı kalır. */}
            <Typography variant="h4" fontWeight={800}>
              Arama Sonuçları
            </Typography>
            <Typography color="text.secondary">
              {kalkis} → {varis}
              {tarih ? ` · ${tarih}` : ''}
              {tip ? ` · ${tip}` : ''}
            </Typography>
          </Paper>

          {loading && (
            <Stack sx={{ py: 6, alignItems: 'center' }}>
              <CircularProgress />
            </Stack>
          )}

          {error && <Alert severity="error">{error}</Alert>}

          {!loading && !error && trips.length === 0 && (
            <Alert severity="info">
              Bu kriterlere uygun sefer bulunamadı.
              <Button component={RouterLink} to="/" sx={{ ml: 1 }}>
                Yeni arama yap
              </Button>
            </Alert>
          )}

          {!loading && trips.map((trip) => (
            <TripCard
              key={trip.id}
              sefer={trip}
              onSelect={(selectedTrip) => navigate(`/seat-selection/${selectedTrip.id}`)}
            />
          ))}
        </Stack>
      </Box>
    </Box>
  )
}

export default SearchResultsPage
