import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import { CookiesProvider } from 'react-cookie'
import AuthProvider from '@hooks/AuthProvider.jsx'
import Header from '@components/layouts/Header.jsx'
import Nav from '@components/layouts/Nav.jsx'
import Footer from '@components/layouts/Footer.jsx'
import Main from '@pages/main/Main.jsx'
import SignIn from '@pages/auth/SignIn.jsx'
import SignUp from '@pages/auth/SignUp.jsx'
import Info from '@pages/auth/Info.jsx'
import User from '@pages/user/Index.jsx'
import Dept from '@pages/dept/Index.jsx'
import Item from '@pages/item/Index.jsx'

function App() {
  return (
    <CookiesProvider defaultSetOptions={{ path: '/' }}>
    <AuthProvider>
      <Header />
      <Nav />
      <Router>
          <Routes>
            {/* 유저관리 */}
            <Route path="/signIn" element={<SignIn />} />
            <Route path="/signUp" element={<SignUp />} />
            <Route path="/info" element={<Info />} />

            {/* 인사관리 */}
            <Route path="/user" element={<User />} />
            <Route path="/dept" element={<Dept />} />

            {/* 메인화면 (기본) */}
            <Route path="*" element={<Main />} />

            {/* 품목관리 */}
            <Route path="/item/*" element={<Item />} />
          </Routes>
      </Router>
      <Footer />
    </AuthProvider>
    </CookiesProvider>
  )
}

export default App
