import React from 'react'
import './Menu.css'

function Menu() {
  return (
    <div className="citygo-menu-wrapper">
      <nav>
        <ul>
          <li>
            <a href="#">Anasayfa</a>
          </li>
          <li>
            <a href="#">Hakkımızda</a>
          </li>
          <li>
            <a href="#">Projelerimiz</a>
          </li>
          <li>
            <a href="#">Hizmetlerimiz</a>
            <ul className="gizle">
              <li>
                <a href="#">HTML</a>
              </li>
            </ul>
          </li>
          <li>
            <a href="#">İletişim</a>
          </li>
          <li>
            <a href="#">Kayıt ol</a>
          </li>
          <li>
            <a href="#">Giriş yap</a>
          </li>
        </ul>
      </nav>
    </div>
  )
}

export default Menu