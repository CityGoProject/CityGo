import React from 'react'
import { Link, useNavigate } from 'react-router-dom'
import api from '../../services/api'
import { clearStoredUser, getStoredUser } from '../../services/auth'
import './Menu.css'

function Menu() {
  const navigate = useNavigate()
  const user = getStoredUser()

  const handleLogout = async () => {
    try {
      await api.post('/auth/logout')
    } catch {
      // Logout endpoint erişilemese bile frontend oturumu temizlemek yeterli.
    } finally {
      clearStoredUser()
      navigate('/login')
    }
  }

  return (
    <div className="citygo-menu-wrapper">
      <nav>
        <ul>
          <li>
            <Link to="/">Anasayfa</Link>
          </li>
          <li>
            <a href="#hakkimizda">Hakkımızda</a>
          </li>
          <li>
            <a href="#projeler">Projelerimiz</a>
          </li>
          <li>
            <a href="#hizmetler">Hizmetlerimiz</a>
            <ul className="gizle">
              <li>
                <a href="#auth">Auth Akışı</a>
              </li>
            </ul>
          </li>
          <li>
            <a href="#iletisim">İletişim</a>
          </li>
          {!user && (
            <>
              <li>
                <Link to="/register">Kayıt Ol</Link>
              </li>
              <li>
                <Link to="/login">Giriş Yap</Link>
              </li>
            </>
          )}
          {user && (
            <>
              <li>
                <span className="menu-user">{user.ad}</span>
              </li>
              <li>
                <button type="button" className="menu-button" onClick={handleLogout}>
                  Çıkış Yap
                </button>
              </li>
            </>
          )}
        </ul>
      </nav>
    </div>
  )
}

export default Menu
