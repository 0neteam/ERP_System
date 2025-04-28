import React, { useState, useEffect } from 'react'
import EmptyUser from '@assets/user/empty_user.png'
import { PATCH } from '@utils/Network.js'

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

export default UserDelModal