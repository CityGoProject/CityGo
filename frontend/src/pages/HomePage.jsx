import React from 'react';
import { Box } from '@mui/material';
import Menu from '../component/menu/Menu';
import Home from '../component/home/Home';

function HomePage() {
  return (
    <Box
      sx={{
        minHeight: '100vh',
        background: 'linear-gradient(135deg, #1e3c72 0%, #2a5298 100%)', // Consistency with Login
      }}
    >
      <Menu />
      <Box sx={{ mt: 4 }}>
        <Home />
      </Box>
    </Box>
  );
}

export default HomePage;

