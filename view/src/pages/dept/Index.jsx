import { useState, useEffect } from 'react'
import '@styles/dept/dept.css'
import '@styles/card.css'
import EmptyUser from '@assets/user/empty_user.png'
import { GET, POST, PUT, PATCH, DELETE } from '@utils/Network.js'
import DeptModal from '@components/dept/DeptModal.jsx'
import UserRegModal from '@components/dept/UserRegModal.jsx'
import UserDelModal from '@components/dept/UserDelModal.jsx'


const Dept = () => {
  const [selectDept, setSelectDept] = useState(null)
  const [selectedU, setselectedU] = useState([])
  const [users, setUsers] = useState([])
  const [depts, setDepts] = useState([])
  const [showModal1, setShowModal1] = useState(false)
  const [showModal2, setShowModal2] = useState(false)
  const [showModal3, setShowModal3] = useState(false)
  const handleOpen1 = () => setShowModal1(true)
  const handleClose1 = () => {
    setShowModal1(false)
    getDept()
  }
  const handleOpen2 = () => setShowModal2(true)
  const handleClose2 = () => {
    setShowModal2(false)
    selectDeptEvent({no: selectDept})
  }
  const handleOpen3 = () => setShowModal3(true)
  const handleClose3 = () => {
    setShowModal3(false)
    selectDeptEvent({no: selectDept})
  }
  const baseUrl = import.meta.env.VITE_APP_GATEWAY_URL + '/oauth/file/u/'
  const getFile = (fileNo) => {
    if(fileNo == null) return EmptyUser
    return baseUrl + fileNo
  }
  const selectDeptEvent = (data) => {
    setUsers([])
    POST('/oauth/dept/user/' + data.no, {}).then(res => {
      if(res.status) {
        const arr = []
        for(let dept of res.result.list) {
          arr[arr.length] = dept.user
        }
        setUsers([...arr])
        setSelectDept(data.no)
      } else {
        setSelectDept(null)
      }
    })
  }
  const delEvent = () => {
    if(selectDept == null) return
    DELETE(`/oauth/dept/${selectDept}`, {}).then(res => {
      if(res.status) {
        document.location.reload()
      }
    })
  }
  const setStyle = no => {
    return selectDept == no ? {borderBottom: '2px solid #1f1f1f'} : {borderBottom: '0px'}
  }
  const getDept = () => {
    GET('/oauth/dept', {}).then(res => {
      if(res.status) {
        setDepts(res.result.list)
      }
    })
  }
  useEffect(() => {
    getDept()
  }, [])
  return (
    <>
      <section className="container" style={{minHeight: '70vh'}}>
        <div className="d-flex justify-content-between align-items-center mb-4">
          <h2 className="mb-0">부서 관리</h2>
        </div>

        <div className="d-flex flex-wrap align-items-center justify-content-between gap-2 p-1 mb-2">
          <ul className="d-flex menu dept-dropdown align-center expanded text-center mb-0" style={{minWidth: 'calc(100% - 150px)'}}>
              {depts?.map((v, i) => {
                return (
                  <li className='menu-item lg-up' key={i} style={setStyle(v.no)}><a onClick={() => selectDeptEvent(v)}>{v.deptName}</a></li>    
                )
              })}
              <li className="menu-item dropdown dropdown-hover sm-only md-only">
                <a href="#" className="dropdown-toggle more-label" data-bs-toggle="dropdown" role="button" aria-expanded="false">
                  More ▾
                </a>
                <ul className="dropdown-menu more-menu text-center">
                  {depts?.map((v, i) => {
                    return i < 4 ? (
                      <li className="menu-item sm-only md-only" key={i} style={setStyle(v.no)}><a className="dropdown-item" onClick={() => selectDeptEvent(v)}>{v.deptName}</a></li>
                    ) : <li className="menu-item" key={i} style={setStyle(v.no)}><a className="dropdown-item" onClick={() => selectDeptEvent(v)}>{v.deptName}</a></li>
                  })}
                </ul>
              </li>
            </ul>
            <ul className="menu d-inline-block text-center mb-0">
              <li className=''>
                <a onClick={handleOpen1}>부서 추가 +</a>
              </li>
          </ul>
        </div>
        
        {selectDept &&
        <div className="container col-12 p-4 justify-content-between" style={{backgroundColor: 'white', border: '1px solid rgb(44, 44, 44)', borderRadius: '10px'}} >
          <div className="d-flex justify-content-between mb-4">
              <div>
              {selectDept != 1 && <button className="btn btn-outline-secondary" onClick={delEvent}>부서 삭제</button>}
              </div>
              <div className="d-flex gap-2">
                <a className="btn btn-outline-success" onClick={handleOpen2}>직원 등록</a>
                <a className="btn btn-outline-secondary" onClick={handleOpen3} >직원 삭제</a>
              </div>
          </div>
          <div className="row g-4 mb-2">
            {users?.map((v, i) => {
              if (!v) return null;
              return (
                <div className="col-6 col-md-4 col-lg-2" key={i}>
                  <div className="card h-100">
                    <img src={getFile(v.fileNo)} className="card-img-top" />
                    <div className="card-body d-flex flex-column text-center">
                      <h6 className="card-title mt-2">{v.name}</h6>
                    </div>
                  </div>
                </div>
              )
            })}
        </div>
      </div>
      }
    </section>
    {showModal1 && <DeptModal handleClose={handleClose1} /> }
    {showModal2 && <UserRegModal handleClose={handleClose2} dept={selectDept} /> }
    {showModal3 && <UserDelModal handleClose={handleClose3} deptUsers={users} dept={selectDept} /> }
  </>
  )
}

export default Dept