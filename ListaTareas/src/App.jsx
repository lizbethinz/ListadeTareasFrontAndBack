import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import LoginRegistro from './loginRegistro'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Tareas from './Tareas/Tareas';
import ProtectedRoute from './ProtectedRoute';
import { AlertProvider } from './AlertContext'

function App() {
  const [title, setTitle] = useState("Default value")

  
  /*useEffect(() => {
    fetch("http://localhost:8080/home")
    .then(response=response.text())
    .then(text=>{
      return setTitle(text)
    })
    .catch(error=>console.log("Error " ,error))
  },[])*/

  return (
    <AlertProvider> 
      <Router> 
            <Routes>
                <Route path="/" element={<LoginRegistro />} />
                <Route element={<ProtectedRoute />}>
                    <Route path="/tareas" element={<Tareas />} /> 
                </Route>
                <Route path="*" element={<h1>404: Página no encontrada</h1>} />
            </Routes>
        </Router>
    </AlertProvider>
    
  )
}

export default App
