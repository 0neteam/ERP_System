import { useAuth } from '@hooks/AuthProvider.jsx'
import { POST } from '@utils/Network.js'

const Header = () => {
  const { isAccess, removeAccess } = useAuth()
  const logout = () => {
    POST('/oauth/user/logout', {}).then(
      res => {
        if(res.status) {
          removeAccess()
          document.location.href = "/"
        } else {
          console.log("정상적으로 로그아웃이 되지 않았습니다.")
        }
      },
      err => console.error(err)
    )
  }
  return (
    <header className="navbar">
      <div className="container">
      { isAccess().auth ?
        <div className="navbar-nav d-flex flex-row flex-nowrap align-items-center ms-auto">
            <a className="nav-item nav-link me-3" href="/info">마이페이지</a>
            <button type="button" className="nav-item nav-link me-3" onClick={logout}>로그아웃</button>
        </div>
        :
        <div className="navbar-nav d-flex flex-row flex-nowrap align-items-center ms-auto">
            <a className="nav-item nav-link me-3" href="/signIn">로그인</a>
            <a className="nav-item nav-link me-3" href="/signUp">회원가입</a>
        </div>
      }
      </div>
    </header>
  )
}

export default Header