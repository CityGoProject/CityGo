import React, { useState } from 'react';
import { Box, TextField, Button, Typography, Alert, Paper, Link as MuiLink } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import api from '../../services/api';
import { setStoredUser } from '../../services/auth';

const Login = () => {
  const [email, setEmail] = useState('');
  const [sifre, setSifre] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setError('');
    
    try {
      const response = await api.post('/auth/login', { email, sifre });
      // Basarili giris sonrasi kullanici bilgisini sakliyoruz
      setStoredUser(response.data);
      navigate('/'); // Ana sayfaya yonlendir
    } catch (err) {
      setError(err.response?.data?.hata || 'Giriş yapılamadı. E-posta veya şifre hatalı olabilir.');
    }
  };

  return (
    <Paper elevation={10} sx={{ p: 5, borderRadius: 3, width: '100%', maxWidth: 400, bgcolor: 'rgba(255, 255, 255, 0.95)' }}>
      <Typography variant="h4" fontWeight="bold" color="primary" gutterBottom align="center">
        CityGo
      </Typography>
      <Typography variant="body1" color="textSecondary" align="center" sx={{ mb: 3 }}>
        Hesabınıza giriş yapın
      </Typography>

      {error && <Alert severity="error" sx={{ mb: 3 }}>{error}</Alert>}

      <form onSubmit={handleLogin}>
        <TextField
          fullWidth
          label="E-posta"
          variant="outlined"
          type="email"
          margin="normal"
          required
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          autoComplete="email"
        />
        <TextField
          fullWidth
          label="Şifre"
          variant="outlined"
          type="password"
          margin="normal"
          required
          value={sifre}
          onChange={(e) => setSifre(e.target.value)}
          autoComplete="current-password"
          sx={{ mb: 3 }}
        />
        
        <Button
          type="submit"
          fullWidth
          variant="contained"
          size="large"
          sx={{ 
              py: 1.5, 
              mb: 3, 
              borderRadius: 2, 
              textTransform: 'none', 
              fontSize: '1.1rem',
              boxShadow: 4,
              '&:hover': {
                transform: 'translateY(-2px)',
                boxShadow: 6,
                transition: 'all 0.2s'
              }
          }}
        >
          Giriş Yap
        </Button>
        
        <Typography align="center" variant="body2">
          Hesabınız yok mu?{' '}
          <MuiLink component={Link} to="/register" color="primary" underline="hover">
            Kayıt Ol
          </MuiLink>
        </Typography>
      </form>
    </Paper>
  );
};

export default Login;