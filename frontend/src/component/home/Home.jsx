import React, { useState } from 'react';
import { 
  Box, 
  Paper, 
  Typography, 
  Button, 
  Autocomplete, 
  TextField, 
  IconButton,
  Stack
} from '@mui/material';
import SwapHorizIcon from '@mui/icons-material/SwapHoriz';
import DirectionsBusIcon from '@mui/icons-material/DirectionsBus';
import { cities } from '../../services/cities';

const Home = () => {
  const [fromCity, setFromCity] = useState('İstanbul');
  const [toCity, setToCity] = useState('Ankara');

  const handleSwap = () => {
    setFromCity(toCity);
    setToCity(fromCity);
  };

  const handleSearch = () => {
    alert(`Bilet Aranıyor: ${fromCity} -> ${toCity}`);
  };

  return (
    <Box sx={{ 
      display: 'flex', 
      flexDirection: 'column', 
      alignItems: 'center', 
      justifyContent: 'center',
      minHeight: '80vh',
      p: 2
    }}>
      <Paper elevation={10} sx={{ 
        p: { xs: 3, md: 5 }, 
        borderRadius: 4, 
        width: '100%', 
        maxWidth: 700, 
        bgcolor: 'rgba(255, 255, 255, 0.95)',
        backdropFilter: 'blur(10px)'
      }}>
        <Stack spacing={4}>
          <Box sx={{ textAlign: 'center' }}>
            <DirectionsBusIcon color="primary" sx={{ fontSize: 48, mb: 1 }} />
            <Typography variant="h4" fontWeight="bold" color="primary" gutterBottom>
              Nereye Gitmek İstersiniz?
            </Typography>
            <Typography variant="body1" color="textSecondary">
              En uygun otobüs bileti fiyatlarını sizin için bulalım
            </Typography>
          </Box>

          <Stack 
            direction={{ xs: 'column', md: 'row' }} 
            spacing={2} 
            alignItems="center"
            justifyContent="center"
          >
            <Autocomplete
              fullWidth
              value={fromCity}
              onChange={(event, newValue) => setFromCity(newValue)}
              options={cities}
              renderInput={(params) => (
                <TextField {...params} label="Nereden" variant="outlined" />
              )}
            />

            <IconButton 
              onClick={handleSwap} 
              color="primary" 
              sx={{ 
                bgcolor: 'primary.light', 
                color: 'white',
                '&:hover': { bgcolor: 'primary.main' },
                display: { xs: 'none', md: 'flex' }
              }}
            >
              <SwapHorizIcon />
            </IconButton>

            {/* Mobile swap icon */}
            <IconButton 
              onClick={handleSwap} 
              color="primary" 
              sx={{ 
                bgcolor: 'primary.light', 
                color: 'white',
                '&:hover': { bgcolor: 'primary.main' },
                display: { xs: 'flex', md: 'none' },
                alignSelf: 'center'
              }}
            >
              <SwapHorizIcon sx={{ transform: 'rotate(90deg)' }} />
            </IconButton>

            <Autocomplete
              fullWidth
              value={toCity}
              onChange={(event, newValue) => setToCity(newValue)}
              options={cities}
              renderInput={(params) => (
                <TextField {...params} label="Nereye" variant="outlined" />
              )}
            />
          </Stack>

          <Button
            variant="contained"
            size="large"
            fullWidth
            onClick={handleSearch}
            sx={{ 
              py: 2, 
              fontSize: '1.2rem', 
              fontWeight: 'bold',
              borderRadius: 3,
              textTransform: 'none',
              boxShadow: 4,
              '&:hover': {
                transform: 'translateY(-2px)',
                boxShadow: 6
              },
              transition: 'all 0.2s'
            }}
          >
            Bilet Bul
          </Button>
        </Stack>
      </Paper>
    </Box>
  );
};

export default Home;
