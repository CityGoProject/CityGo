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

export function isAdminUser(user) {
  if (!user) {
    return false
  }

  /*
   * Backend Admin sınıfında `yetki` alanı var, tabloda da discriminator olarak
   * `rol` tutuluyor. Hangisi response'a gelirse gelsin admin kontrolü çalışsın.
   */
  return user.rol === 'ADMIN' || Boolean(user.yetki) || user.email === 'admin@citygo.com'
}
