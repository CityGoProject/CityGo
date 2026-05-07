import { Box, Button, Stack, Typography } from '@mui/material'

function chunkSeats(seats) {
  const sortedSeats = [...seats].sort((a, b) => a.koltukNo - b.koltukNo)
  const rows = []

  for (let index = 0; index < sortedSeats.length; index += 4) {
    rows.push(sortedSeats.slice(index, index + 4))
  }

  return rows
}

/*
 * Koltuk haritası sadece kendisine verilen listeyi çizer.
 * Backend çağrısı sayfada yapılır; bu bileşen saf UI parçası olarak kalır.
 */
function SeatMap({ koltuklar = [], selectedSeat, onSeatSelect }) {
  if (!koltuklar.length) {
    return (
      <Typography color="text.secondary">
        Bu sefer için koltuk bilgisi henüz gelmedi.
      </Typography>
    )
  }

  return (
    <Box sx={{ 
      overflowX: 'auto', 
      py: 2, 
      display: 'flex', 
      justifyContent: 'flex-start',
      bgcolor: 'rgba(0,0,0,0.02)', 
      borderRadius: 4,
    }}>
      <Stack direction="row" spacing={1.5} sx={{ minWidth: 'fit-content', px: 4 }}>
        {/* Şoför/Ön taraf simgesi */}
        <Box sx={{ display: 'flex', alignItems: 'center', pr: 2, borderRight: '2px dashed #ccc' }}>
          <Typography variant="overline" sx={{ transform: 'rotate(-90deg)', fontWeight: 800 }}>ÖN</Typography>
        </Box>

        {chunkSeats(koltuklar).map((row, rowIndex) => (
          <Box
            key={rowIndex}
            sx={{
              display: 'grid',
              gridTemplateRows: '44px 44px 20px 44px 44px',
              gap: 1,
              alignContent: 'center'
            }}
          >
            {row.map((seat, seatIndex) => {
              const selected = selectedSeat?.koltukNo === seat.koltukNo
              const disabled = Boolean(seat.dolu)

              return (
                <Button
                  key={seat.id || seat.koltukNo}
                  variant={selected ? 'contained' : 'outlined'}
                  color={disabled ? 'inherit' : selected ? 'primary' : 'success'}
                  disabled={disabled}
                  onClick={() => onSeatSelect?.(selected ? null : seat)}
                  sx={{
                    gridRow: seatIndex >= 2 ? seatIndex + 2 : seatIndex + 1,
                    minWidth: 44,
                    height: 44,
                    p: 0,
                    borderRadius: 2,
                    fontSize: '0.75rem',
                    fontWeight: 700
                  }}
                >
                  {seat.koltukNo}
                </Button>
              )
            })}
          </Box>
        ))}
      </Stack>
    </Box>
  )
}

export default SeatMap
