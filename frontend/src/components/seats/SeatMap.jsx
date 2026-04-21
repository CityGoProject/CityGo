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
    <Stack spacing={1.5}>
      {chunkSeats(koltuklar).map((row, rowIndex) => (
        <Box
          key={rowIndex}
          sx={{
            display: 'grid',
            gridTemplateColumns: '48px 48px 24px 48px 48px',
            gap: 1,
            justifyContent: 'center',
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
                  gridColumn: seatIndex >= 2 ? seatIndex + 2 : seatIndex + 1,
                  minWidth: 48,
                  height: 44,
                  p: 0,
                }}
              >
                {seat.koltukNo}
              </Button>
            )
          })}
        </Box>
      ))}
    </Stack>
  )
}

export default SeatMap
