import { useState, useEffect } from 'react'
import '@styles/dept/dept.css'
import '@styles/card.css'
import EmptyUser from '@assets/user/empty_user.png'
import { GET, POST, PUT, PATCH, DELETE } from '@utils/Network.js'
import DeptModal from '@components/dept/DeptModal.jsx'

const UserRegModal = ({handleClose, dept}) => {
  const [selectUsers, setSelectUsers] = useState([])
  const [targetUsers, setTargetUsers] = useState([])
  const baseUrl = import.meta.env.VITE_APP_GATEWAY_URL + '/oauth/file/u/'
  const getFile = (fileNo) => {
    if(fileNo == null) return EmptyUser
    return baseUrl + fileNo
  }
  const selectEvent = (i) => {
    if (selectUsers?.some(user => user.no == targetUsers[i].no)) return
    setSelectUsers([...selectUsers, targetUsers[i]])
  }
  const saveEvent = () => {
    if(selectUsers.length == 0) return
    const users = []
    for(let user of selectUsers) {
      users[users.length] = user.no
    }
    PUT(`/oauth/dept/user/${dept}`, {users}).then(res => {
      if(res.status) closeEvent()
    })
  }
  const closeEvent = () => {
    setSelectUsers([])
    handleClose()
  }
  const selectUserEvent = u => {
    const arr = []
    for(let user of selectUsers) {
      if(u.no === user.no) continue
      arr[arr.length] = user
    }
    setSelectUsers([...arr])
  }
  const submitEvent = e => {
    e.preventDefault()
    console.log(e.target.name.value)
    getUser({name: e.target.name.value})
  }
  const getUser = param => {
    GET(`/oauth/dept/user/${dept}`, param).then(res => {
      if(res.status) {
        setTargetUsers(res.result.list)
      }
    })
  }
  useEffect(() => {
    setSelectUsers([])
    getUser({})
  }, [])
  return (
    <div className="modal show d-block" tabIndex="-1" role="dialog">
      <div className="modal-dialog modal-dialog-centered modal-xl .modal-fullscreen-xl-down">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title">직원 등록</h5>
              <button type="button" className="btn-close" onClick={closeEvent}></button>
            </div>
            <div className="modal-body">
              <div className="container-fluid">
                <div className="row">
                  <p>선택된 직원</p>

                  {selectUsers?.map((v, i) => {
                    return (
                      <div className="col-6 col-md-4 col-lg-2 mb-2" key={i} onClick={() => selectUserEvent(v)}>


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

                <div className="d-flex justify-content-end gap-2">
                  <button type="button" className="btn btn-outline-success" onClick={saveEvent}>저장</button>
                  <button type="button" className="btn btn-outline-secondary" onClick={closeEvent}>취소</button>
                </div>

                <hr />

                <div className="mb-4">
                  <form className="form" onSubmit={submitEvent}>
                    <div className="input-group">
                      <input type="input" className="form-control" name="name" placeholder='직원 이름을 입력해주세요.' />
                      <button type="submit" className="btn btn-outline-success">검색</button>
                    </div>
                  </form>
                </div>

                <div className="row mt-2">
                  {targetUsers?.map((v, i) => {
                    return (
                      <div className="col-6 col-md-4 col-lg-2 mb-2" key={i} onClick={()=> selectEvent(i)}>
                        <div className="card h-100">

                          <img src={getFile(v.fileNo)} className="card-img-top" />
                          <div className="card-body d-flex flex-column text-center">
                            <h6 className="card-title mt-2">{v.name}</h6>
                          </div>
                        </div>
                      </div>
                    )}
                  )}

                </div>
              </div>
            </div>
            
          </div>
      </div>
    </div>
  )
}

const UserDelModal = ({handleClose, deptUsers, dept}) => {
  const [selectUsers, setSelectUsers] = useState([])
  const [targetUsers, setTargetUsers] = useState([])
  const baseUrl = import.meta.env.VITE_APP_GATEWAY_URL + '/oauth/file/u/'
  const getFile = (fileNo) => {
    if(fileNo == null) return EmptyUser
    return baseUrl + fileNo
  }
  const selectEvent = (i) => {
    if (selectUsers?.some(user => user.no == targetUsers[i].no)) return
    setSelectUsers([...selectUsers, targetUsers[i]])
  }
  const saveEvent = () => {
    if(selectUsers.length == 0) return
    if(selectUsers.length == 0) return
    const users = []
    for(let user of selectUsers) {
      users[users.length] = user.no
    }
    PATCH(`/oauth/dept/user/${dept}`, {users}).then(res => {
      if(res.status) closeEvent()
    })
  }
  const closeEvent = () => {
    setSelectUsers([])
    handleClose()
  }
  useEffect(() => {
    setSelectUsers([])
    setTargetUsers([...deptUsers])
  }, [deptUsers])
  return (
    <div className="modal show d-block" tabIndex="-1" role="dialog">
      <div className="modal-dialog modal-dialog-centered modal-xl .modal-fullscreen-xl-down">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title">직원 삭제</h5>
              <button type="button" className="btn-close" onClick={closeEvent}></button>
            </div>
            <div className="modal-body">
              <div className="container-fluid">
                <div className="row">
                  <p>선택된 직원</p>

                  {selectUsers?.map((v, i) => {
                    return (
                      <div className="col-6 col-md-4 col-lg-2 mb-2" key={i}>
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
                <hr />
                <div className="row mt-2">
                  {targetUsers?.map((v, i) => {
                    return (
                      <div className="col-6 col-md-4 col-lg-2 mb-2" key={i} onClick={()=> selectEvent(i)}>
                        <div className="card h-100">
                          <img src={getFile(v.fileNo)} className="card-img-top" />
                          <div className="card-body d-flex flex-column text-center">
                            <h6 className="card-title mt-2">{v.name}</h6>
                          </div>
                        </div>
                      </div>
                    )}
                  )}

                </div>
              </div>
            </div>
            <div className="modal-footer">
              <button type="button" className="btn btn-outline-success" onClick={saveEvent}>저장</button>
              <button type="button" className="btn btn-outline-secondary" onClick={closeEvent}>취소</button>
            </div>
          </div>
      </div>
    </div>
  )
}

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