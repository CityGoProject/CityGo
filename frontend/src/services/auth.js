// Auth verisini tek yerde toplamak tekrar eden localStorage kodunu azaltır.
export function getStoredUser() {
  const rawUser = localStorage.getItem('user')

  if (!rawUser) {
    return null
  }

  try {
    return JSON.parse(rawUser)
  } catch {
    localStorage.removeItem('user')
    return null
  }
}

export function setStoredUser(user) {
  localStorage.setItem('user', JSON.stringify(user))
}

export function clearStoredUser() {
  localStorage.removeItem('user')
}
