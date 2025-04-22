import { createContext, useContext, useState, useEffect } from "react"
import { useCookies } from 'react-cookie';
import { encode, decode } from '@utils/Base64.js'

export const AuthContext = createContext()

const AuthProvider = ({children}) => {
  const [access, setAccess] = useState({auth: false, roles: null});
  const [cookies, setCookie, removeCookie] = useCookies(['ck']);

  useEffect(() => {
    if(cookies.auth) checkAccess( decode(cookies.auth) )
  }, [])

  const checkAccess = (auth) => {
    setCookie('auth', encode(auth))
    setAccess(auth)
  }

  const isAccess = () => {
    return access
  }

  const removeAccess = () => {
    removeCookie("auth")
  }
  
  return (
    <AuthContext.Provider value={{ access, isAccess, checkAccess, removeAccess }}>
      {children}
    </AuthContext.Provider>
  )

}

export const useAuth = () => useContext(AuthContext);

export default AuthProvider