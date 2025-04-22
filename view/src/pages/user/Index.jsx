import { useState, useEffect } from 'react'
import EmptyUser from '@assets/user/empty_user.png'
import '@styles/user/user.css'
import '@styles/card.css'
import { GET, PATCH, DELETE } from '@utils/Network.js'

const Pagination = ({pagination, clickEvent, page, total}) => {
  const oneStepEvent = type => {
    if(type === 'p') {
      clickEvent(page == 0 ? page : page - 1)
    } 
    if(type === 'n') {
      clickEvent(page == total-1 ? page : page + 1)
    }
    if(type === 'P') {
      clickEvent(0)
    }
    if(type === 'N') {
      clickEvent(total-1)
    }
  }
  return (
    <div>
      <ul className="pagination justify-content-center">
        <li className="page-item">
          <button className="page-link" aria-label="First" onClick={() => oneStepEvent('P')}>
            <span aria-hidden="true">&laquo;</span>
          </button>
        </li>
        <li className="page-item">
          <button className="page-link" aria-label="Previous" onClick={() => oneStepEvent('p')}>
            <span aria-hidden="true">&lsaquo;</span>
          </button>
        </li>
        {
          pagination?.map((v, i) => {
            return (
              <li className="page-item" key={i} ><button className={v.active ? 'page-link active' : 'page-link'}  onClick={()=> clickEvent(i)}>{v.page}</button></li>
            )
          })
        }
        <li className="page-item">
          <button className="page-link" aria-label="Next" onClick={() => oneStepEvent('n')}>
            <span aria-hidden="true">&rsaquo;</span>
          </button>
        </li>
        <li className="page-item">
          <button className="page-link" aria-label="Last" onClick={() => oneStepEvent('N')}>
            <span aria-hidden="true">&raquo;</span>
          </button>
        </li>
      </ul>
    </div>
  )
}

const User = () => {
  const [depts, setDepts] = useState(null)
  const [licences, setLicences] = useState(null)
  const [user, setUser] = useState({})
  const [users, setUsers] = useState([])
  const [pagination, setPagination] = useState([])
  const [page, setPage] = useState(0)
  const [total, setTotal] = useState(4)
  const [show, isShow] = useState(false)
  const [info, isInfo] = useState(false)
  const [dri, isDri] = useState(false)
  const [edit, isEdit] = useState(false)
  const baseUrl = import.meta.env.VITE_APP_GATEWAY_URL + '/oauth/file/u/'
  const getFile = (fileNo) => {
    if(fileNo == null) return EmptyUser
    return baseUrl + fileNo
  }
  const changeDept = () => {
    setDepts(user?.depts?.map(d => d.deptName).join(', '))
  }
  const changeLicence = (data) => {
    const arr = []
    if(data.licence1 === 'Y') arr[arr.length] = '1종 특수'
    if(data.licence2 === 'Y') arr[arr.length] = '1종 대형'
    if(data.licence3 === 'Y') arr[arr.length] = '1종 보통'
    if(data.licence4 === 'Y') arr[arr.length] = '2종 보통'
    setLicences(arr?.map(l => l).join(', '))
  }
  const changeEvent = (e) => {
    const {name, value} = e.target
    setUser({...user, [name]: value})
  }
  const clickEvent = i => {
    setPage(i)
    const arr = []
    pagination.forEach((v, vi) => {
      if(i == vi) v.active = true
      else v.active = false
      arr[vi] = v
    })
    setPagination(arr)
  }
  const selectUserEvent = data => {
    data?.depts?.map(v => isDri(v.no === 5))
    setUser(data)
    setDepts(data?.depts?.map(d => d.deptName).join(', '))
    changeLicence(data)
    isInfo(true)
  }
  const submitEvent = e => {
    e.preventDefault()
    getUser({page, name: e.target.name.value})
  }
  const checkLicence = licence => (licence === 'Y') ? 'btn btn-outline-primary w-100 text-nowrap active' : 'btn btn-outline-primary w-100 text-nowrap'
  const licenceEvent = i => {
    const name = `licence${i}`
    const value = user[name] === 'Y' ? 'N' : 'Y'
    selectUserEvent({...user, [name]: value})
  }
  const deleteEvent = (no) => {
    DELETE(`/oauth/user/mng/${no}`, {}).then(res => {
      if(res.status) cancelEvent()
    })
  }
  const editEvent = () => {
    if(edit) {
      const params = {licence1: user.licence1, licence2: user.licence2, licence3: user.licence3, licence4: user.licence4}
      PATCH(`/oauth/user/mng/${user.no}`, params).then(res => {
        if(res.status) isEdit(!edit)
      })
    } else {
      isEdit(!edit)
    }    
  }
  const cancelEvent = () => {
    setUser({})
    isInfo(false)
    getUser({page})
  }
  const getUser = (params) => {
    GET('/oauth/user/mng', params).then(res => {
      if(res.status) {
        setUsers(res.result.list)
        const arr = []
        for(let i = 0; i < res.result.totalPages; i++) {
          const active = page == i
          arr[i] = {page: i+1, active}
        }
        setPagination(arr)
        setTotal(res.result.totalPages)
        isShow(true)
      }
    })
  }
  useEffect(() => {
    getUser({page})
  }, [page])
  return (
    <section className="container" style={{minHeight: '70vh'}}>
      <h2 className="mb-4">직원 관리</h2>
      <div className="row mb-2">
        <div className="col-12 col-md-5 col-lg-4 mb-2">
          <div className="section-box d-flex flex-column justify-content-between h-100">
            <div>
              <form className="input-group mb-2" onSubmit={submitEvent}>
                <input type="input" className="form-control" name="name" />
                <button type="submit" className="btn btn-outline-success">검색</button>
              </form>
              <ul className="list-group mb-2">
                {users?.map((v, i) => {
                  return (
                    <li className="list-group-item border-1 d-flex justify-content-between align-items-center" style={{cursor: 'pointer'}} key={i} onClick={() => selectUserEvent(v)}>
                      {v.name}
                    </li>
                  )
                })}
              </ul>
            </div>
            {show && 
              <Pagination pagination={pagination} clickEvent={clickEvent} page={page} total={total} />
            }
          </div>
        </div>

        {info && 
        <div className="col-12 col-md-7 col-lg-8 mb-2">
          <div className="section-box d-flex flex-column justify-content-between h-100">
            <div>
              <div className="d-flex justify-content-center align-items-center w-100 w-lg-50 mb-4">
                <img className="user" src={getFile(user.fileNo)} />
              </div>
              <div className="input-group mb-2">
                <span className="input-group-text">사번</span>
                <input type="text" id="no" name='no' className="form-control" placeholder="no" disabled value={user.no} onChange={changeEvent} />
              </div>

              <div className="input-group mb-2">
                <span className="input-group-text">이름</span>
                <input type="text" id="name" name="name" className="form-control" placeholder="name" disabled value={user.name} onChange={changeEvent} />
              </div>

              <div className="input-group mb-2">
                <span className="input-group-text">메일</span>
                <input type="text" id="email" name="email" className="form-control" placeholder="email@email.com" disabled value={user.email} onChange={changeEvent} />
              </div>
              
              <div className="input-group mb-2">
                <span className="input-group-text">부서</span>
                <input type="text" id="dept" className="form-control" placeholder="" disabled value={depts} onChange={changeDept} />
              </div>

              {dri && <>
                <div className="input-group mb-2">
                  <span className="input-group-text">면허</span>
                  <input type="text" id="licence" className="form-control" placeholder="" disabled value={licences} onChange={changeLicence}/>
                </div>
                {edit &&
                <div className="row row-cols-2 row-cols-md-4 g-2 mb-4">
                  <div className="col">
                    <button className={checkLicence(user.licence1)} onClick={() => licenceEvent(1)}>1종 특수</button>
                  </div>
                  <div className="col">
                    <button className={checkLicence(user.licence2)} onClick={() => licenceEvent(2)}>1종 대형</button>
                  </div>
                  <div className="col">
                    <button className={checkLicence(user.licence3)} onClick={() => licenceEvent(3)}>1종 보통</button>
                  </div>
                  <div className="col">
                    <button className={checkLicence(user.licence4)} onClick={() => licenceEvent(4)}>2종 보통</button>
                  </div>
                </div>
                }
              </>}

            </div>

            <div className="btns d-flex w-100 justify-content-between gap-2">
              <div>
                <button className="btn btn-outline-danger" onClick={() => deleteEvent(user.no)}>삭제</button>
              </div>
              <div className='d-flex gap-1'>
                {dri &&
                  <button className="btn btn-outline-success" onClick={editEvent}>{edit ? '저장' : '수정'}</button>
                }
                <button className="btn btn-outline-secondary" onClick={cancelEvent}>취소</button>
              </div>
            </div>

          </div>
        </div>
        }
      </div>
    </section>
  )
}

export default User