import { Button, Card, CardActions, CardContent, Chip, Stack, Typography } from '@mui/material'

function formatDateTime(value) {
  if (!value) {
    return 'Tarih bekleniyor'
  }

  return new Intl.DateTimeFormat('tr-TR', {
    dateStyle: 'medium',
    timeStyle: 'short',
  }).format(new Date(value))
}

// Araç tipine göre renk döndüren fonksiyon (Dışarıda tanımlandı)
const getVehicleColor = (type) => {
  switch (type?.toUpperCase()) {
    case 'UCAK':
      return '#0284C7' // Mavi
    case 'TREN':
      return '#10B981' // Yeşil
    case 'OTOBUS':
      return '#F59E0B' // Turuncu
    default:
      return 'pink'    // Diğerleri için pembe
  }
}

// ... (üst kısımdaki fonksiyonlar aynı kalıyor)

function TripCard({ sefer, onSelect }) {
  const arac = sefer?.arac || {}
  const price = sefer?.biletFiyati ?? arac.biletFiyati
  const vehicleType = arac.aracTipi || arac.tip || 'SEFER'

  return (
    <Card variant="outlined" sx={{ borderRadius: 2 }}>
      <CardContent>
        {/* Ana Stack'e alignItems="center" ekleyerek tüm elemanları yatayda ortaladık */}
        <Stack spacing={2} sx={{ alignItems: 'center', textAlign: 'center' }}>

          <Stack direction="row" spacing={1} sx={{ alignItems: 'center', justifyContent: 'center', flexWrap: 'wrap' }}>
            <Chip
              label={vehicleType}
              sx={{
                bgcolor: getVehicleColor(vehicleType),
                color: 'white',
                fontWeight: 'bold'
              }}
              size="small"
            />
            <Typography variant="body2" color="text.secondary">
              {[arac.firma, arac.model].filter(Boolean).join(' · ') || 'Araç bilgisi bekleniyor'}
            </Typography>
          </Stack>

          <Typography variant="h6" fontWeight={800}>
            {sefer?.kalkisNoktasi || '-'} → {sefer?.varisNoktasi || '-'}
          </Typography>

          <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2} sx={{ justifyContent: 'center' }}>
            <Typography variant="body2" color="text.secondary">
              Kalkış: {formatDateTime(sefer?.kalkisZamani)}
            </Typography>
            <Typography variant="body2" color="text.secondary">
              Varış: {formatDateTime(sefer?.varisZamani)}
            </Typography>
          </Stack>

          <Typography variant="h6" color="primary" fontWeight={800}>
            {price ? `${price} TL` : 'Fiyat bilgisi bekleniyor'}
          </Typography>
        </Stack>
      </CardContent>

      {/* Butonu da ortalamak için justifyContent="center" ekledik */}
      <CardActions sx={{ px: 2, pb: 2, justifyContent: 'center' }}>
        <Button variant="contained" onClick={() => onSelect?.(sefer)}>
          Koltuk Seç
        </Button>
      </CardActions>
    </Card>
  )
}

export default TripCard
