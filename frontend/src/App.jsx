import { Routes, Route } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import Menu from './component/menu/Menu'
import './App.css'

function App() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/" element={
        <div>
          <Menu />
          {/* Gelecekte HomePage buraya gelecek */}
        </div>
      } />
    </Routes>
  )
}

export default App
