import {
  Autocomplete,
  Box,
  Button,
  IconButton,
  MenuItem,
  Paper,
  Stack,
  TextField,
  Typography,
} from '@mui/material'
import SwapHorizIcon from '@mui/icons-material/SwapHoriz'
import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import Footer from '../components/layout/Footer'
import Navbar from '../components/layout/Navbar'
import { cities } from '../services/cities'
import { getStoredUser } from '../services/auth'

const vehicleOptions = [
  { value: '', label: 'Tümü' },
  { value: 'UCAK', label: 'Uçak' },
  { value: 'TREN', label: 'Tren' },
  { value: 'OTOBUS', label: 'Otobüs' },
]

function HomePage() {
  const navigate = useNavigate()
  const user = getStoredUser()
  const fullName = [user?.ad, user?.soyad].filter(Boolean).join(' ')
  const [formData, setFormData] = useState({
    kalkis: 'İstanbul',
    varis: 'Ankara',
    tarih: '',
    tip: '',
  })

  const handleChange = (event) => {
    setFormData((current) => ({
      ...current,
      [event.target.name]: event.target.value,
    }))
  }

  const handleCityChange = (fieldName, value) => {
    setFormData((current) => ({
      ...current,
      [fieldName]: value || '',
    }))
  }

  const handleSwapCities = () => {
    setFormData((current) => ({
      ...current,
      kalkis: current.varis,
      varis: current.kalkis,
    }))
  }

  const handleSearch = (event) => {
    event.preventDefault()

    const params = new URLSearchParams()
    params.set('kalkis', formData.kalkis)
    params.set('varis', formData.varis)

    if (formData.tarih) {
      params.set('tarih', formData.tarih)
    }

    if (formData.tip) {
      params.set('tip', formData.tip)
    }

    navigate(`/search-results?${params.toString()}`)
  }

  return (
    <Box
      sx={{
        minHeight: '100vh',
        background: 'linear-gradient(135deg, #1e3c72 0%, #2a5298 100%)',
      }}
    >
      <Navbar />

      <Box sx={{ px: 3, py: 6 }}>
        <Paper
          elevation={8}
          sx={{
            maxWidth: 980,
            mx: 'auto',
            p: { xs: 3, md: 5 },
            borderRadius: 3,
            bgcolor: 'rgba(255, 255, 255, 0.95)',
          }}
        >
          <Stack spacing={3}>
            <Box>
              <Typography variant="overline" color="primary" fontWeight={700}>
                CityGo
              </Typography>
              <Typography variant="h3" fontWeight={800}>
                Hoş geldin{fullName ? `, ${fullName}` : ''}
              </Typography>
              <Typography variant="body1" color="text.secondary">
                Gitmek istediğin güzergahı seç, uygun seferleri listeleyelim.
              </Typography>
            </Box>

            <Box component="form" onSubmit={handleSearch}>
              {/* Bu form HomePage'i placeholder olmaktan çıkarıp gerçek sefer arama ekranı yapar. */}
              <Stack spacing={2}>
                <Stack direction={{ xs: 'column', md: 'row' }} spacing={2} alignItems="center">
                  <Autocomplete
                    fullWidth
                    value={formData.kalkis}
                    options={cities}
                    onChange={(_, value) => handleCityChange('kalkis', value)}
                    renderInput={(params) => (
                      <TextField {...params} required label="Kalkış" />
                    )}
                  />

                  <IconButton
                    color="primary"
                    onClick={handleSwapCities}
                    sx={{
                      bgcolor: 'primary.light',
                      color: 'white',
                      '&:hover': { bgcolor: 'primary.main' },
                    }}
                  >
                    <SwapHorizIcon sx={{ transform: { xs: 'rotate(90deg)', md: 'none' } }} />
                  </IconButton>

                  <Autocomplete
                    fullWidth
                    value={formData.varis}
                    options={cities}
                    onChange={(_, value) => handleCityChange('varis', value)}
                    renderInput={(params) => (
                      <TextField {...params} required label="Varış" />
                    )}
                  />
                </Stack>

                <Box
                  sx={{
                    display: 'grid',
                    gridTemplateColumns: { xs: '1fr', md: '1fr 1fr auto' },
                    gap: 2,
                    alignItems: 'center',
                  }}
                >
                  <TextField
                    label="Tarih"
                    name="tarih"
                    type="date"
                    value={formData.tarih}
                    onChange={handleChange}
                    InputLabelProps={{ shrink: true }}
                  />

                  <TextField
                    select
                    label="Araç Tipi"
                    name="tip"
                    value={formData.tip}
                    onChange={handleChange}
                  >
                    {vehicleOptions.map((option) => (
                      <MenuItem key={option.value || 'all'} value={option.value}>
                        {option.label}
                      </MenuItem>
                    ))}
                  </TextField>

                  <Button type="submit" variant="contained" size="large" sx={{ height: 56 }}>
                    Sefer Ara
                  </Button>
                </Box>
              </Stack>
            </Box>
          </Stack>
        </Paper>
      </Box>
      <Footer />
    </Box>
  )
}

export default HomePage
