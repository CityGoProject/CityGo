import {
  Alert,
  Box,
  Button,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Paper,
  Stack,
  Tab,
  Tabs,
  TextField,
  Typography,
} from '@mui/material'
import { useCallback, useEffect, useState } from 'react'
import {
  createTrip,
  deleteTrip,
  getAdminStats,
  getAllTickets,
  getAllTrips,
  getAllUsers,
  updateTrip,
} from '../services/adminService'
import { exportTicketsAsCsv, exportTicketsAsJson } from '../services/exportService'

const emptyTripForm = {
  aracId: '',
  kalkisNoktasi: '',
  varisNoktasi: '',
  kalkisZamani: '',
  varisZamani: '',
}

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
  const [success, setSuccess] = useState('')
  const [tripDialogOpen, setTripDialogOpen] = useState(false)
  const [editingTrip, setEditingTrip] = useState(null)
  const [tripForm, setTripForm] = useState(emptyTripForm)

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

  const openCreateTripDialog = () => {
    setEditingTrip(null)
    setTripForm(emptyTripForm)
    setTripDialogOpen(true)
  }

  const openEditTripDialog = (trip) => {
    /*
     * datetime-local input saniye istemez. Backend LocalDateTime kabul ettiği için
     * "2026-04-24T09:30" formatını direkt gönderiyoruz.
     */
    setEditingTrip(trip)
    setTripForm({
      aracId: trip.arac?.id || '',
      kalkisNoktasi: trip.kalkisNoktasi || '',
      varisNoktasi: trip.varisNoktasi || '',
      kalkisZamani: (trip.kalkisZamani || '').slice(0, 16),
      varisZamani: (trip.varisZamani || '').slice(0, 16),
    })
    setTripDialogOpen(true)
  }

  const closeTripDialog = () => {
    setTripDialogOpen(false)
    setEditingTrip(null)
    setTripForm(emptyTripForm)
  }

  const handleTripFormChange = (event) => {
    setTripForm({
      ...tripForm,
      [event.target.name]: event.target.value,
    })
  }

  const handleSaveTrip = async (event) => {
    event.preventDefault()
    setError('')
    setSuccess('')

    const payload = {
      ...tripForm,
      // Backend yorumunda aracId sayısal bekleniyor. Boşsa hiç göndermiyoruz.
      aracId: tripForm.aracId ? Number(tripForm.aracId) : undefined,
    }

    try {
      if (editingTrip) {
        await updateTrip(editingTrip.id, payload)
        setSuccess('Sefer güncellendi.')
      } else {
        await createTrip(payload)
        setSuccess('Sefer eklendi.')
      }

      closeTripDialog()
      await loadAdminData()
    } catch (err) {
      setError(err.response?.data?.hata || 'Sefer kaydedilemedi. AdminController henüz kodlanmamış olabilir.')
    }
  }

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
      setSuccess('Sefer silindi.')
      await loadAdminData()
    } catch (err) {
      setError(err.response?.data?.hata || 'Sefer silinemedi.')
    }
  }

  return (
    <Box sx={{ minHeight: '100vh', backgroundColor: '#f6f8fb' }}>
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
            <Stack sx={{ py: 6, alignItems: 'center' }}>
              <CircularProgress />
            </Stack>
          )}

          {error && <Alert severity="error">{error}</Alert>}
          {success && <Alert severity="success">{success}</Alert>}

          {!loading && (
            <Paper sx={{ p: 3, borderRadius: 3 }} variant="outlined">
              {activeTab === 0 && (
                <Stack spacing={2}>
                  <Typography variant="h6" fontWeight={800}>
                    İstatistikler
                  </Typography>
                  <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2}>
                    <Paper sx={{ p: 2, flex: 1 }} variant="outlined">
                      <Typography color="text.secondary">Toplam Sefer</Typography>
                      <Typography variant="h5" fontWeight={800}>
                        {stats?.toplamSefer ?? trips.length}
                      </Typography>
                    </Paper>
                    <Paper sx={{ p: 2, flex: 1 }} variant="outlined">
                      <Typography color="text.secondary">Toplam Kullanıcı</Typography>
                      <Typography variant="h5" fontWeight={800}>
                        {stats?.toplamKullanici ?? users.length}
                      </Typography>
                    </Paper>
                    <Paper sx={{ p: 2, flex: 1 }} variant="outlined">
                      <Typography color="text.secondary">Toplam Bilet</Typography>
                      <Typography variant="h5" fontWeight={800}>
                        {stats?.toplamBilet ?? tickets.length}
                      </Typography>
                    </Paper>
                  </Stack>
                  <JsonPreview data={stats} emptyText="İstatistik bilgisi bulunamadı." />
                </Stack>
              )}

              {activeTab === 1 && (
                <Stack spacing={2}>
                  <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2} sx={{ alignItems: 'center' }}>
                    <Typography variant="h6" fontWeight={800} sx={{ mr: 'auto' }}>
                      Seferler
                    </Typography>
                    <Button variant="contained" onClick={openCreateTripDialog}>
                      Yeni Sefer
                    </Button>
                  </Stack>
                  {!trips.length && <Alert severity="info">Sefer bulunamadı.</Alert>}
                  {trips.map((trip) => (
                    <Paper key={trip.id} sx={{ p: 2 }} variant="outlined">
                      <Stack
                        direction={{ xs: 'column', sm: 'row' }}
                        spacing={2}
                        sx={{ alignItems: 'center' }}
                      >
                        <Box sx={{ mr: 'auto' }}>
                          <Typography fontWeight={700}>
                            #{trip.id} · {trip.kalkisNoktasi} → {trip.varisNoktasi}
                          </Typography>
                          <Typography variant="body2" color="text.secondary">
                            {trip.kalkisZamani || 'Kalkış tarihi bekleniyor'} - {trip.varisZamani || 'varış tarihi bekleniyor'}
                          </Typography>
                        </Box>
                        <Stack direction="row" spacing={1}>
                          <Button variant="outlined" onClick={() => openEditTripDialog(trip)}>
                            Düzenle
                          </Button>
                          <Button color="error" variant="outlined" onClick={() => handleDeleteTrip(trip)}>
                            Sil
                          </Button>
                        </Stack>
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
                <Stack spacing={2} sx={{ alignItems: 'flex-start' }}>
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

      <Dialog open={tripDialogOpen} onClose={closeTripDialog} fullWidth maxWidth="sm">
        <DialogTitle>{editingTrip ? 'Sefer Düzenle' : 'Yeni Sefer Ekle'}</DialogTitle>
        <Box component="form" onSubmit={handleSaveTrip}>
          <DialogContent>
            <Stack spacing={2} sx={{ pt: 1 }}>
              <TextField
                label="Araç ID"
                name="aracId"
                type="number"
                value={tripForm.aracId}
                onChange={handleTripFormChange}
                required
                fullWidth
              />
              <TextField
                label="Kalkış Noktası"
                name="kalkisNoktasi"
                value={tripForm.kalkisNoktasi}
                onChange={handleTripFormChange}
                required
                fullWidth
              />
              <TextField
                label="Varış Noktası"
                name="varisNoktasi"
                value={tripForm.varisNoktasi}
                onChange={handleTripFormChange}
                required
                fullWidth
              />
              <TextField
                label="Kalkış Zamanı"
                name="kalkisZamani"
                type="datetime-local"
                value={tripForm.kalkisZamani}
                onChange={handleTripFormChange}
                InputLabelProps={{ shrink: true }}
                required
                fullWidth
              />
              <TextField
                label="Varış Zamanı"
                name="varisZamani"
                type="datetime-local"
                value={tripForm.varisZamani}
                onChange={handleTripFormChange}
                InputLabelProps={{ shrink: true }}
                required
                fullWidth
              />
            </Stack>
          </DialogContent>
          <DialogActions>
            <Button onClick={closeTripDialog}>Vazgeç</Button>
            <Button type="submit" variant="contained">
              Kaydet
            </Button>
          </DialogActions>
        </Box>
      </Dialog>
    </Box>
  )
}

export default AdminPanel
