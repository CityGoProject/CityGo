import axios from 'axios';

const api = axios.create({
  // Duzeltme: Backend adresi kodun icine gomulu degil, ortam degiskeninden okunuyor.
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
});

api.interceptors.request.use((config) => {
  const rawUser = localStorage.getItem('user');

  if (rawUser) {
    try {
      const user = JSON.parse(rawUser);
      if (user?.id) {
        // Duzeltme: Backend admin endpointleri artik X-User-Id ile admin kontrolu yapiyor.
        config.headers['X-User-Id'] = user.id;
      }
    } catch {
      localStorage.removeItem('user');
    }
  }

  return config;
});

export default api;
