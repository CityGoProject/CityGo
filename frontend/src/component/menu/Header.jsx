import React from 'react'
import { Link } from "react-router-dom";

function Header() {
    return (
        <div className='header'>
            <Link className='link' to="/">Anasayfa</Link>
            <Link className='link' to="/login">Giriş Yap</Link>
            <Link className='link' to="/register">Kayıt Ol</Link>
        </div>
    )
}

export default Header
