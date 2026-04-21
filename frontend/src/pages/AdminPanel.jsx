import {
  Alert,
  Box,
  Button,
  CircularProgress,
  Paper,
  Stack,
  Tab,
  Tabs,
  Typography,
} from '@mui/material'
import { useCallback, useEffect, useState } from 'react'
import Footer from '../components/layout/Footer'
import Navbar from '../components/layout/Navbar'
import {
  deleteTrip,
  getAdminStats,
  getAllTickets,
  getAllTrips,
  getAllUsers,
} from '../services/adminService'
import { exportTicketsAsCsv, exportTicketsAsJson } from '../services/exportService'

function JsonPreview({ data, emptyText }) {
  if (!data || (Array.isArray(data) && data.length === 0)) {
    return <Alert severity="info">{emptyText}</Alert>
  }

  return (
    <Box
      component="pre"
      sx={{
        bgcolor: '#111827',
        color: '#e5e7eb',
        p: 2,
        borderRadius: 2,
        overflow: 'auto',
        maxHeight: 420,
      }}
    >
      {JSON.stringify(data, null, 2)}
    </Box>
  )
}

function AdminPanel() {
  const [activeTab, setActiveTab] = useState(0)
  const [stats, setStats] = useState(null)
  const [trips, setTrips] = useState([])
  const [users, setUsers] = useState([])
  const [tickets, setTickets] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  const loadAdminData = useCallback(async () => {
    setLoading(true)
    setError('')

    try {
      /*
       * Admin panelde birkaç liste aynı anda lazım. Promise.all ile hepsini
       * paralel çekiyoruz; biri hazır değilse kullanıcıya net hata gösteriyoruz.
       */
      const [statsResponse, tripsResponse, usersResponse, ticketsResponse] = await Promise.all([
        getAdminStats(),
        getAllTrips(),
        getAllUsers(),
        getAllTickets(),
      ])

      setStats(statsResponse.data)
      setTrips(Array.isArray(tripsResponse.data) ? tripsResponse.data : [])
      setUsers(Array.isArray(usersResponse.data) ? usersResponse.data : [])
      setTickets(Array.isArray(ticketsResponse.data) ? ticketsResponse.data : [])
    } catch (err) {
      setError(err.response?.data?.hata || 'Admin verileri getirilemedi. AdminController henüz kodlanmamış olabilir.')
    } finally {
      setLoading(false)
    }
  }, [])

  useEffect(() => {
    loadAdminData()
  }, [loadAdminData])

  const handleDeleteTrip = async (trip) => {
    const confirmed = window.confirm(`#${trip.id} numaralı sefer silinsin mi?`)

    if (!confirmed) {
      return
    }

    try {
      await deleteTrip(trip.id)
      await loadAdminData()
    } catch (err) {
      setError(err.response?.data?.hata || 'Sefer silinemedi.')
    }
  }

  return (
    <Box sx={{ minHeight: '100vh', backgroundColor: '#f6f8fb' }}>
      <Navbar />
      <Box sx={{ maxWidth: 1100, mx: 'auto', px: 3, py: 5 }}>
        <Stack spacing={3}>
          <Paper sx={{ p: 3, borderRadius: 3 }} variant="outlined">
            <Typography variant="h4" fontWeight={800}>
              Admin Paneli
            </Typography>
            <Typography color="text.secondary">
              Sefer, kullanıcı, bilet ve dışa aktarma işlemleri için yönetim ekranı.
            </Typography>
          </Paper>

          <Paper sx={{ borderRadius: 3 }} variant="outlined">
            <Tabs
              value={activeTab}
              onChange={(_, value) => setActiveTab(value)}
              variant="scrollable"
              scrollButtons="auto"
            >
              <Tab label="İstatistikler" />
              <Tab label="Seferler" />
              <Tab label="Kullanıcılar" />
              <Tab label="Biletler" />
              <Tab label="Dışa Aktarma" />
            </Tabs>
          </Paper>

          {loading && (
            <Stack alignItems="center" sx={{ py: 6 }}>
              <CircularProgress />
            </Stack>
          )}

          {error && <Alert severity="error">{error}</Alert>}

          {!loading && (
            <Paper sx={{ p: 3, borderRadius: 3 }} variant="outlined">
              {activeTab === 0 && (
                <Stack spacing={2}>
                  <Typography variant="h6" fontWeight={800}>
                    İstatistikler
                  </Typography>
                  <JsonPreview data={stats} emptyText="İstatistik bilgisi bulunamadı." />
                </Stack>
              )}

              {activeTab === 1 && (
                <Stack spacing={2}>
                  <Typography variant="h6" fontWeight={800}>
                    Seferler
                  </Typography>
                  {!trips.length && <Alert severity="info">Sefer bulunamadı.</Alert>}
                  {trips.map((trip) => (
                    <Paper key={trip.id} sx={{ p: 2 }} variant="outlined">
                      <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2} alignItems="center">
                        <Box sx={{ mr: 'auto' }}>
                          <Typography fontWeight={700}>
                            #{trip.id} · {trip.kalkisNoktasi} → {trip.varisNoktasi}
                          </Typography>
                          <Typography variant="body2" color="text.secondary">
                            {trip.kalkisZamani || 'Tarih bekleniyor'}
                          </Typography>
                        </Box>
                        <Button color="error" variant="outlined" onClick={() => handleDeleteTrip(trip)}>
                          Sil
                        </Button>
                      </Stack>
                    </Paper>
                  ))}
                </Stack>
              )}

              {activeTab === 2 && (
                <Stack spacing={2}>
                  <Typography variant="h6" fontWeight={800}>
                    Kullanıcılar
                  </Typography>
                  <JsonPreview data={users} emptyText="Kullanıcı bulunamadı." />
                </Stack>
              )}

              {activeTab === 3 && (
                <Stack spacing={2}>
                  <Typography variant="h6" fontWeight={800}>
                    Biletler
                  </Typography>
                  <JsonPreview data={tickets} emptyText="Bilet bulunamadı." />
                </Stack>
              )}

              {activeTab === 4 && (
                <Stack spacing={2} alignItems="flex-start">
                  <Typography variant="h6" fontWeight={800}>
                    Dışa Aktarma
                  </Typography>
                  <Typography color="text.secondary">
                    Backend export endpointleri hazır olduğunda bilet verileri indirilecek.
                  </Typography>
                  <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2}>
                    <Button variant="contained" onClick={exportTicketsAsJson}>
                      JSON İndir
                    </Button>
                    <Button variant="outlined" onClick={exportTicketsAsCsv}>
                      CSV İndir
                    </Button>
                  </Stack>
                </Stack>
              )}
            </Paper>
          )}
        </Stack>
      </Box>
      <Footer />
    </Box>
  )
}

export default AdminPanel
