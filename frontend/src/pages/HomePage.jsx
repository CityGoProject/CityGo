import { Box, Button, Paper, Stack, Typography } from '@mui/material'
import Menu from '../component/menu/Menu'
import { getStoredUser } from '../services/auth'

function HomePage() {
  const user = getStoredUser()
  const fullName = [user?.ad, user?.soyad].filter(Boolean).join(' ')

  return (
    <Box
      sx={{
        minHeight: '100vh',
        background: 'linear-gradient(180deg, #eef4ff 0%, #ffffff 100%)',
      }}
    >
      <Menu />

      <Box sx={{ px: 3, py: 6 }}>
        <Paper
          elevation={6}
          sx={{
            maxWidth: 760,
            mx: 'auto',
            p: { xs: 3, md: 5 },
            borderRadius: 4,
          }}
        >
          <Stack spacing={2}>
            <Typography variant="overline" color="primary" fontWeight={700}>
              CityGo
            </Typography>
            <Typography variant="h3" fontWeight={800}>
              Hoş geldin{fullName ? `, ${fullName}` : ''}
            </Typography>
            <Typography variant="body1" color="text.secondary">
              Auth akışı başarıyla bağlandı. Giriş yapan kullanıcı bilgisi
              localStorage içinde saklanıyor ve bu sayfa yalnızca oturum açıkken
              gösteriliyor.
            </Typography>
            <Typography variant="body1" color="text.secondary">
              Sonraki adımda buraya sefer arama ve kullanıcıya özel içerikler
              eklenebilir.
            </Typography>
            <Button variant="contained" size="large" sx={{ alignSelf: 'flex-start', mt: 1 }}>
              Seferleri Yakında İncele
            </Button>
          </Stack>
        </Paper>
      </Box>
    </Box>
  )
}

export default HomePage
