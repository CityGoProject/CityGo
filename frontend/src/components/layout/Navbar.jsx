import { AppBar, Box, Button, Toolbar, Typography, Avatar } from '@mui/material'
import { Link as RouterLink, useNavigate } from 'react-router-dom'
import { clearStoredUser, getStoredUser, isAdminUser, logoutUser } from '../../services/auth'

function Navbar() {
  const navigate = useNavigate()
  const user = getStoredUser()

  const handleLogout = async () => {
    try {
      await logoutUser()
    } catch {
      // Backend logout cevap vermezse bile frontend oturumunu temizlemek yeterli.
    } finally {
      clearStoredUser()
      navigate('/login')
    }
  }

  return (
    <AppBar
      position="static"
      sx={{
        bgcolor: '#0f172a', // Footer ile aynı derin lacivert
        boxShadow: 'none',
        borderBottom: '1px solid rgba(255,255,255,0.05)'
      }}
    >
      <Toolbar sx={{ gap: 1, px: { xs: 2, md: 4 } }}>

        {/* LOGO VE İSİM ALANI - Pastel Tonlar */}
        <Box
          component={RouterLink}
          to="/"
          sx={{
            display: 'flex',
            alignItems: 'center',
            gap: 1.2,
            textDecoration: 'none',
            mr: 'auto'
          }}
        >
          <Box
            component="img"
            src="/favicon.png"
            alt="CityGo Logo"
            sx={{
              height: 35,
              width: 'auto',
              opacity: 0.9,
              filter: 'drop-shadow(0px 0px 8px rgba(96, 165, 250, 0.2))'
            }}
          />
          <Typography
            variant="h5"
            sx={{
              fontWeight: 800,
              letterSpacing: '-1px',
              display: { xs: 'none', sm: 'block' },
              lineHeight: 1
            }}
          >
            <Box component="span" sx={{ color: '#60a5fa' }}>City</Box>
            <Box component="span" sx={{ color: '#fb923c' }}>Go</Box>
          </Typography>
        </Box>

        {/* MENÜ BUTONLARI - Soluk Gri Tonları */}
        {user ? (
          <>
            <Button
              component={RouterLink}
              to="/"
              sx={{ color: '#94a3b8', fontWeight: 600, '&:hover': { color: '#e2e8f0' } }}
            >
              Sefer Ara
            </Button>
            <Button
              component={RouterLink}
              to="/my-tickets"
              sx={{ color: '#94a3b8', fontWeight: 600, '&:hover': { color: '#e2e8f0' } }}
            >
              Biletlerim
            </Button>

            {isAdminUser(user) && (
              <Button
                component={RouterLink}
                to="/admin"
                sx={{ color: '#FCD34D', fontWeight: 700, opacity: 0.8, '&:hover': { opacity: 1 } }}
              >
                Admin
              </Button>
            )}

            {/* PROFİL KISMI */}
            <Box sx={{
              display: 'flex',
              alignItems: 'center',
              gap: 1.2,
              px: 2,
              ml: 1,
              borderLeft: '1px solid rgba(255,255,255,0.1)',
            }}>
              <Avatar
                sx={{
                  width: 30,
                  height: 30,
                  bgcolor: '#fb923c', // Pastel turuncu
                  fontSize: '0.85rem',
                  fontWeight: 'bold',
                  color: '#0f172a', // Arka planla aynı lacivert (kontrast için)
                  opacity: 0.9
                }}
              >
                {user.ad?.charAt(0).toUpperCase()}
              </Avatar>
              <Typography sx={{
                color: '#94a3b8',
                fontWeight: 600,
                fontSize: '0.9rem',
                display: { xs: 'none', md: 'block' }
              }}>
                {user.ad}
              </Typography>
            </Box>

            <Button
              variant="outlined"
              onClick={handleLogout}
              size="small"
              sx={{
                color: '#ef4444',
                borderColor: 'rgba(239, 68, 68, 0.3)',
                fontWeight: 'bold',
                ml: 1,
                fontSize: '0.75rem',
                '&:hover': {
                  borderColor: '#ef4444',
                  bgcolor: 'rgba(239, 68, 68, 0.05)'
                }
              }}
            >
              Çıkış
            </Button>
          </>
        ) : (
          <>
            <Button
              component={RouterLink}
              to="/login"
              sx={{ color: '#94a3b8', fontWeight: 600, '&:hover': { color: '#e2e8f0' } }}
            >
              Giriş Yap
            </Button>
            <Button
              component={RouterLink}
              to="/register"
              variant="contained"
              sx={{
                bgcolor: '#0284C7',
                color: 'white',
                fontWeight: 700,
                borderRadius: '6px',
                px: 2.5,
                textTransform: 'none',
                opacity: 0.9,
                '&:hover': { bgcolor: '#0369A1', opacity: 1 }
              }}
            >
              Kayıt Ol
            </Button>
          </>
        )}
      </Toolbar>
    </AppBar>
  )
}

export default Navbar
