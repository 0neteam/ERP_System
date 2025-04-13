import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import './index.css'
import App from '@/App.jsx'
import RouteTest from '@rt/routeTest.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
        <Routes>
          <Route path="/" element={<App />} />
          <Route path="/routeTest" element={<RouteTest />} />

          <Route path="*" element={<App />} />
        </Routes>
    </BrowserRouter>
  </StrictMode>,
)