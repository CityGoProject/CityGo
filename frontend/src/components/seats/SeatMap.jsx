import { Box, Button, Stack, Typography } from '@mui/material'

function getLayout(vehicleType) {
  // Duzeltme: Tum araclar 2+2 gibi cizilmiyor; ucak icin 3+3 duzen kullaniliyor.
  if (vehicleType === 'UCAK') {
    return {
      rowSize: 6,
      leftGroup: 3,
      rows: '44px 44px 44px 20px 44px 44px 44px',
    }
  }

  return {
    rowSize: 4,
    leftGroup: 2,
    rows: '44px 44px 20px 44px 44px',
  }
}

function chunkSeats(seats, rowSize) {
  const sortedSeats = [...seats].sort((a, b) => a.koltukNo - b.koltukNo)
  const rows = []

  for (let index = 0; index < sortedSeats.length; index += rowSize) {
    rows.push(sortedSeats.slice(index, index + rowSize))
  }

  return rows
}

/*
 * Koltuk haritası sadece kendisine verilen listeyi çizer.
 * Backend çağrısı sayfada yapılır; bu bileşen saf UI parçası olarak kalır.
 */
function SeatMap({ koltuklar = [], selectedSeat, onSeatSelect, vehicleType }) {
  if (!koltuklar.length) {
    return (
      <Typography color="text.secondary">
        Bu sefer için koltuk bilgisi henüz gelmedi.
      </Typography>
    )
  }

  const layout = getLayout(vehicleType)

  return (
    <Box
      sx={{
        overflowX: 'auto',
        py: 2,
        display: 'flex',
        justifyContent: 'flex-start',
        bgcolor: 'rgba(0,0,0,0.02)',
        borderRadius: 4,
      }}
    >
      <Stack direction="row" spacing={1.5} sx={{ minWidth: 'fit-content', px: 4 }}>
        <Box sx={{ display: 'flex', alignItems: 'center', pr: 2, borderRight: '2px dashed #ccc' }}>
          <Typography variant="overline" sx={{ transform: 'rotate(-90deg)', fontWeight: 800 }}>
            ÖN
          </Typography>
        </Box>

        {chunkSeats(koltuklar, layout.rowSize).map((row, rowIndex) => (
          <Box
            key={rowIndex}
            sx={{
              display: 'grid',
              gridTemplateRows: layout.rows,
              gap: 1,
              alignContent: 'center',
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
                    gridRow: seatIndex >= layout.leftGroup ? seatIndex + 2 : seatIndex + 1,
                    minWidth: 44,
                    height: 44,
                    p: 0,
                    borderRadius: 2,
                    fontSize: '0.75rem',
                    fontWeight: 700,
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
