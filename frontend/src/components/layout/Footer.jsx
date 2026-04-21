import { Box, Typography } from '@mui/material'

/*
 * Ortak footer: Her sayfada aynı küçük proje bilgisini göstermek için var.
 * Böylece her sayfaya ayrı ayrı footer metni yazmamıza gerek kalmaz.
 */
function Footer() {
  return (
    <Box component="footer" sx={{ borderTop: '1px solid #e6eaf0', py: 3, textAlign: 'center' }}>
      <Typography variant="body2" color="text.secondary">
        © 2026 CityGo · CENG106 Nesne Yönelimli Programlama Projesi
      </Typography>
    </Box>
  )
}

export default Footer
