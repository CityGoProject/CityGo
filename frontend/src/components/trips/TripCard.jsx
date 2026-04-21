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

/*
 * Bu bileşen sadece seferi gösterir. API çağrısı burada yapılmaz; böylece
 * aynı kart hem arama sonuçlarında hem admin ekranında tekrar kullanılabilir.
 */
function TripCard({ sefer, onSelect }) {
  const arac = sefer?.arac || {}
  const price = arac.biletFiyati ?? sefer?.biletFiyati
  const vehicleType = arac.aracTipi || arac.tip || 'SEFER'

  return (
    <Card variant="outlined" sx={{ borderRadius: 2 }}>
      <CardContent>
        <Stack spacing={2}>
          <Stack direction="row" spacing={1} alignItems="center" flexWrap="wrap">
            <Chip label={vehicleType} color="primary" size="small" />
            <Typography variant="body2" color="text.secondary">
              {[arac.firma, arac.model].filter(Boolean).join(' · ') || 'Araç bilgisi bekleniyor'}
            </Typography>
          </Stack>

          <Typography variant="h6" fontWeight={800}>
            {sefer?.kalkisNoktasi || '-'} → {sefer?.varisNoktasi || '-'}
          </Typography>

          <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2}>
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
      <CardActions sx={{ px: 2, pb: 2 }}>
        <Button variant="contained" onClick={() => onSelect?.(sefer)}>
          Koltuk Seç
        </Button>
      </CardActions>
    </Card>
  )
}

export default TripCard
