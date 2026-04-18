import { useState } from 'react'
import Login from './component/login/Login'
import Menu from './component/menu/Menu'
import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <div>
      {/*<Login />*/}
      <Menu />
    </div>
  )
}

export default App
