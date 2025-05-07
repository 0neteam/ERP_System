import { useAuth } from '@hooks/AuthProvider.jsx'
import Logo  from '@assets/logo_white.png'

const NavMenu = ({i, v}) => {
  const { isAccess } = useAuth()
  const checkRole = data => {
    let cnt = 0;
    if(isAccess().roles != null) {
      for(let role of data) {
        if(isAccess().roles?.search(role) >= 0) cnt += 1
      }
    }
    return cnt > 0 ? true : false
  }
  const show = checkRole(v.roles)
  return (
    <>
      {show &&
        <li className="nav-item dropdown" key={i}>
        {v.type ? 
          <>
            <a className="nav-link dropdown-toggle text-dark" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false">{v.name}</a>
            <ul className="dropdown-menu">
              {v?.list?.map((cv, ci) => {
                return (
                  <li key={ci}><a className="dropdown-item" href={cv.url}>{cv.name}</a></li>
                )
              })}
            </ul>
          </>
          :
          <a className="nav-link text-dark" href={v.url}>{v.name}</a>
        }
        </li>
      }
    </>
  )
}

const Nav = () => {
  
  const list = [
    {type: true, name: '인사관리', roles: ['ADMIN'], list: [{url: '/user', name: '직원관리'}, {url: '/dept', name: '부서관리'}]},
    {type: true, name: '품목관리', roles: ['ADMIN','MFR'], list: [{url: '/item', name: '품목목록'}, {url: '/item/create', name: '품목등록'}]},
    {type: false, name: '재고관리', roles: ['ADMIN','STG'], url: '/stock', list: []},
    {type: true, name: '발주관리', roles: ['ADMIN','STG','MFR','TRS'], list: [{url: '/order', name: '발주목록'}, {url: '/order/create', name: '발주신청'}]},
    {type: true, name: '운송관리', roles: ['ADMIN','TRS','DRI'], list: [{url: '/transp', name: '운송목록'}, {url: '/vehicle', name: '차량목록'}, {url: '/vehicle/create', name: '차량등록'}]},
  ]
  return (
    <nav className="navbar navbar-expand-md bg-light">
      <div className="container">
        <div className="navbar-brand d-flex align-items-center">
          <a className="me-4" href="/">
            <img src={Logo} width="120" height="40px" />
          </a>
        </div>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
          aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav nav-fill gap-6 fs-5 me-1">
            {list?.map((v, pi) => {
              return (
                <NavMenu v={v} i={pi} key={pi}/>
              )
            })}
          </ul>
        </div>
      </div>
    </nav>
  )
}

export default Nav