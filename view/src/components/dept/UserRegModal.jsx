import React, { useState, useEffect } from 'react'
import EmptyUser from '@assets/user/empty_user.png'
import { GET, PUT } from '@utils/Network.js'

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

export default UserRegModal