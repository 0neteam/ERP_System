import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom"
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
import Stock from '@pages/stock/Index.jsx'
import Order from '@pages/order/Index.jsx'
import Transp from "@pages/transp/Index.jsx";
import Vehicle from "@pages/vehicle/Index.jsx"
import { useAuth } from '@hooks/AuthProvider.jsx'

const ProtectedRoute = ({ isLoggedIn, children }) => {
  if (!isLoggedIn) {
    return <Navigate to="/" replace />
  }
  return children
}

const RedirectUrl = () => {
  return <Navigate to="/" replace />
}

const Pages = () => {
  const { isAccess } = useAuth()
  const list = [
    {roles: ['ADMIN'],                    list: [{url: '/user', element: <User />},{url: '/dept', element: <Dept />}]},
    {roles: ['ADMIN','MFR'],              list: [{url: '/item/*', element: <Item />}]},
    {roles: ['ADMIN','STG'],              list: [{url: '/stock/*', element: <Stock />}]},
    {roles: ['ADMIN','STG','MFR','TRS'],  list: [{url: '/order/*', element: <Order />}]},
    {roles: ['ADMIN','TRS','DRI'],        list: [{url: '/transp/*', element: <Transp />},{url: '/vehicle/*', element: <Vehicle />}]},
  ]
  return (
    <Routes>
      <Route path="/" element={<Main />} />
      <Route path="/signIn" element={<SignIn />} />
      <Route path="/signUp" element={<SignUp />} />
      <Route path="/info" element={<Info />} />
      {list?.map((v) => {
        let cnt = 0
        for(const role of v.roles) {
          if(isAccess()?.roles?.search(role) >= 0) cnt += 1
        }
        return cnt > 0 && v.list?.map((row, i) => {
            return (
              <Route path={row.url} element={row.element} key={i}/>
            )
          }
        )
      })}
             
      <Route path="*" element={<RedirectUrl />} />
    </Routes>
  )
}

const App = () => {
  return (
    <CookiesProvider defaultSetOptions={{ path: '/' }}>
    <AuthProvider>
      <Header />
      <Nav />
      <Router>
        <Pages />
      </Router>
      <Footer />
    </AuthProvider>
    </CookiesProvider>
  )
}

export default App