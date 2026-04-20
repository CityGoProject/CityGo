import React, { useState } from 'react';
import { Box, TextField, Button, Typography, Alert, Paper, Link as MuiLink, Grid } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import api from '../services/api';
import { setStoredUser } from '../services/auth';

const RegisterPage = () => {
  const [formData, setFormData] = useState({
    ad: '',
    soyad: '',
    email: '',
    sifre: '',
    telefon: '',
    tcNo: ''
  });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    setError('');
    
    try {
      await api.post('/auth/register', formData);
      // Kayit basariliysa otomatik giris yapalim
      const loginResponse = await api.post('/auth/login', {
          email: formData.email,
          sifre: formData.sifre
      });
      setStoredUser(loginResponse.data);
      navigate('/'); // Ana sayfaya yonlendir
    } catch (err) {
      setError(err.response?.data?.hata || 'Kayıt sırasında bir hata oluştu. Bilgileri kontrol edin.');
    }
  };

  return (
    <Box sx={{
      minHeight: '100vh',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      py: 4,
      background: 'linear-gradient(135deg, #1e3c72 0%, #2a5298 100%)'
    }}>
      <Paper elevation={10} sx={{ p: 5, borderRadius: 3, width: '100%', maxWidth: 500, bgcolor: 'rgba(255, 255, 255, 0.95)' }}>
        <Typography variant="h4" fontWeight="bold" color="primary" gutterBottom align="center">
          CityGo
        </Typography>
        <Typography variant="body1" color="textSecondary" align="center" sx={{ mb: 3 }}>
          Yeni bir hesap oluşturun
        </Typography>

        {error && <Alert severity="error" sx={{ mb: 3 }}>{error}</Alert>}

        <form onSubmit={handleRegister}>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Ad"
                name="ad"
                required
                value={formData.ad}
                onChange={handleChange}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Soyad"
                name="soyad"
                required
                value={formData.soyad}
                onChange={handleChange}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="E-posta"
                name="email"
                type="email"
                required
                value={formData.email}
                onChange={handleChange}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="Şifre"
                name="sifre"
                type="password"
                required
                value={formData.sifre}
                onChange={handleChange}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Telefon"
                name="telefon"
                required
                value={formData.telefon}
                onChange={handleChange}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="TC Kimlik No"
                name="tcNo"
                required
                value={formData.tcNo}
                onChange={handleChange}
              />
            </Grid>
          </Grid>
          
          <Button
            type="submit"
            fullWidth
            variant="contained"
            size="large"
            sx={{ 
                mt: 4, 
                mb: 3, 
                py: 1.5,
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
            Kayıt Ol
          </Button>
          
          <Typography align="center" variant="body2">
            Zaten hesabınız var mı?{' '}
            <MuiLink component={Link} to="/login" color="primary" underline="hover">
              Giriş Yap
            </MuiLink>
          </Typography>
        </form>
      </Paper>
    </Box>
  );
};

export default RegisterPage;
