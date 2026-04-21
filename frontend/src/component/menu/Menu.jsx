import React from 'react';
import './Menu.css';
import { Routes, Route } from 'react-router-dom';
import LoginPage from '../../pages/LoginPage';
import RegisterPage from '../../pages/RegisterPage';
import Home from '../../pages/Home.jsx';
import Header from './Header';
import Footer from './footer/Footer';

function Menu() {
  return (
    <div className="citygo-menu-wrapper">
      <nav className='menunav'>
        <Header />
      </nav>
      {/* Route alanını bir div içerisine alıyoruz */}
      <div className="menubirim">
        <Routes>
          <Route path='/login' element={<LoginPage />} />
          <Route path='/register' element={<RegisterPage />} />
          <Route path='/' element={<Home />} />
        </Routes>
      </div>
      
    </div>
  );
}

export default Menu;
