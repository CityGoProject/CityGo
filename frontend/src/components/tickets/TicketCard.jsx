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
 * Bilet kartı sadece gösterim yapar. İptal API çağrısı üst sayfada kalır;
 * böylece bu kart başka yerde de kolayca kullanılabilir.
 */
function TicketCard({ bilet, onCancel }) {
  const sefer = bilet?.sefer || {}
  const koltuk = bilet?.koltuk || {}
  const isActive = bilet?.durum === 'AKTIF'

  return (
    <Card variant="outlined" sx={{ borderRadius: 2 }}>
      <CardContent>
        <Stack spacing={1.5}>
          <Stack direction="row" spacing={1} sx={{ alignItems: 'center' }}>
            <Chip
              label={bilet?.durum || 'DURUM BEKLENIYOR'}
              color={isActive ? 'success' : 'default'}
              size="small"
            />
            <Typography variant="body2" color="text.secondary">
              Bilet #{bilet?.id}
            </Typography>
          </Stack>

          <Typography variant="h6" fontWeight={800}>
            {sefer.kalkisNoktasi || '-'} → {sefer.varisNoktasi || '-'}
          </Typography>

          <Typography variant="body2" color="text.secondary">
            Kalkış: {formatDateTime(sefer.kalkisZamani)}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Koltuk: {koltuk.koltukNo || '-'} · Tutar: {bilet?.odenenTutar ?? '-'} TL
          </Typography>
        </Stack>
      </CardContent>
      {isActive && (
        <CardActions sx={{ px: 2, pb: 2 }}>
          <Button color="error" variant="outlined" onClick={() => onCancel?.(bilet)}>
            İptal Et
          </Button>
        </CardActions>
      )}
    </Card>
  )
}

export default TicketCard
