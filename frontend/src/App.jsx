import { Navigate, Route, Routes } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import HomePage from './pages/HomePage'
import SearchResultsPage from './pages/SearchResultsPage'
import SeatSelectionPage from './pages/SeatSelectionPage'
import MyTicketsPage from './pages/MyTicketsPage'
import AdminPanel from './pages/AdminPanel'
import { getStoredUser, isAdminUser } from './services/auth'
import './App.css'

function ProtectedRoute({ children }) {
  const user = getStoredUser()

  if (!user) {
    // Oturum yoksa auth sayfasına yönlendirerek entegrasyonu görünür kılıyoruz.
    return <Navigate to="/login" replace />
  }

  return children
}

function PublicOnlyRoute({ children }) {
  const user = getStoredUser()

  if (user) {
    return <Navigate to="/" replace />
  }

  return children
}

function AdminRoute({ children }) {
  const user = getStoredUser()

  /*
   * Admin paneli ayrı guard ile korunuyor. Backend güvenliği ayrıca yazılmalı;
   * bu kontrol frontend'de yanlışlıkla panelin görünmesini engeller.
   */
  if (!user) {
    return <Navigate to="/login" replace />
  }

  if (!isAdminUser(user)) {
    return <Navigate to="/" replace />
  }

  return children
}

function App() {
  /*
   * Rebase sırasında uzak repoda eski Menu/Footer layout'u geldi.
   * Burada yeni sayfa bazlı routing korunuyor; Navbar/Footer artık sayfaların içinde.
   */
  return (
    <Routes>
      <Route
        path="/login"
        element={
          <PublicOnlyRoute>
            <LoginPage />
          </PublicOnlyRoute>
        }
      />
      <Route
        path="/register"
        element={
          <PublicOnlyRoute>
            <RegisterPage />
          </PublicOnlyRoute>
        }
      />
      <Route
        path="/"
        element={
          <ProtectedRoute>
            <HomePage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/search-results"
        element={
          <ProtectedRoute>
            <SearchResultsPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/seat-selection/:seferId"
        element={
          <ProtectedRoute>
            <SeatSelectionPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/my-tickets"
        element={
          <ProtectedRoute>
            <MyTicketsPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/admin"
        element={
          <AdminRoute>
            <AdminPanel />
          </AdminRoute>
        }
      />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  )
}

export default App
