import { Alert, Box, CircularProgress, Paper, Stack, Typography } from '@mui/material'
import { useCallback, useEffect, useState } from 'react'
import Footer from '../components/layout/Footer'
import Navbar from '../components/layout/Navbar'
import TicketCard from '../components/tickets/TicketCard'
import { getStoredUser } from '../services/auth'
import { cancelTicket, getMyTickets } from '../services/ticketService'

function MyTicketsPage() {
  const user = getStoredUser()
  const [tickets, setTickets] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  const loadTickets = useCallback(async () => {
    setLoading(true)
    setError('')

    try {
      const response = await getMyTickets(user.id)
      setTickets(Array.isArray(response.data) ? response.data : [])
    } catch (err) {
      setError(err.response?.data?.hata || 'Biletler getirilemedi. Backend endpoint hazır olmayabilir.')
    } finally {
      setLoading(false)
    }
  }, [user.id])

  useEffect(() => {
    loadTickets()
  }, [loadTickets])

  const handleCancel = async (ticket) => {
    /*
     * Basit onay kullanıyoruz. İleride MUI Dialog'a çevrilebilir.
     * Şimdilik arkadaşların akışı hızlıca test etmesi daha önemli.
     */
    const confirmed = window.confirm(`#${ticket.id} numaralı bilet iptal edilsin mi?`)

    if (!confirmed) {
      return
    }

    try {
      await cancelTicket(ticket.id)
      await loadTickets()
    } catch (err) {
      setError(err.response?.data?.hata || 'Bilet iptal edilemedi.')
    }
  }

  return (
    <Box sx={{ minHeight: '100vh', backgroundColor: '#f6f8fb' }}>
      <Navbar />
      <Box sx={{ maxWidth: 980, mx: 'auto', px: 3, py: 5 }}>
        <Stack spacing={3}>
          <Paper sx={{ p: 3, borderRadius: 3 }} variant="outlined">
            <Typography variant="h4" fontWeight={800}>
              Biletlerim
            </Typography>
            <Typography color="text.secondary">
              Aldığın biletleri buradan görüntüleyebilir ve iptal edebilirsin.
            </Typography>
          </Paper>

          {loading && (
            <Stack sx={{ py: 6, alignItems: 'center' }}>
              <CircularProgress />
            </Stack>
          )}

          {error && <Alert severity="error">{error}</Alert>}

          {!loading && !error && tickets.length === 0 && (
            <Alert severity="info">Henüz biletin bulunmuyor.</Alert>
          )}

          {!loading && tickets.map((ticket) => (
            <TicketCard key={ticket.id} bilet={ticket} onCancel={handleCancel} />
          ))}
        </Stack>
      </Box>
      <Footer />
    </Box>
  )
}

export default MyTicketsPage
