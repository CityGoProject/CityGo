import { AppBar, Box, Button, Toolbar, Typography } from '@mui/material'
import { Link as RouterLink, useNavigate } from 'react-router-dom'
import api from '../../services/api'
import { clearStoredUser, getStoredUser, isAdminUser } from '../../services/auth'

/*
 * Eski `src/component/menu/Menu.jsx` statik site menüsü gibiydi.
 * Bu dosya uygulama içi gerçek navigasyon için yeni standarttır.
 */
function Navbar() {
  const navigate = useNavigate()
  const user = getStoredUser()

  const handleLogout = async () => {
    try {
      await api.post('/auth/logout')
    } catch {
      // Backend logout cevap vermezse bile frontend oturumunu temizlemek yeterli.
    } finally {
      clearStoredUser()
      navigate('/login')
    }
  }

  return (
    <AppBar position="static" color="default" elevation={1}>
      <Toolbar sx={{ gap: 2, flexWrap: 'wrap' }}>
        <Typography
          component={RouterLink}
          to="/"
          variant="h6"
          sx={{ color: 'primary.main', fontWeight: 800, textDecoration: 'none', mr: 'auto' }}
        >
          CityGo
        </Typography>

        {user ? (
          <>
            <Button component={RouterLink} to="/" color="inherit">
              Sefer Ara
            </Button>
            <Button component={RouterLink} to="/my-tickets" color="inherit">
              Biletlerim
            </Button>
            {isAdminUser(user) && (
              <Button component={RouterLink} to="/admin" color="inherit">
                Admin
              </Button>
            )}
            <Box sx={{ color: 'text.secondary', px: 1 }}>
              {user.ad}
            </Box>
            <Button variant="outlined" onClick={handleLogout}>
              Çıkış Yap
            </Button>
          </>
        ) : (
          <>
            <Button component={RouterLink} to="/login" color="inherit">
              Giriş Yap
            </Button>
            <Button component={RouterLink} to="/register" variant="contained">
              Kayıt Ol
            </Button>
          </>
        )}
      </Toolbar>
    </AppBar>
  )
}

export default Navbar
