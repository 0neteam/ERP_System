import React, { useState } from 'react'
import { PUT } from '@utils/Network.js'

const DeptModal = ({handleClose}) => {
  const [dept, setDept] = useState({deptName: '', name: ''})
  const changeEvent = (e) => {
    const {name, value} = e.target
    setDept({...dept, [name]: value})
  }
  const saveEvent = () => {
    if(dept.name === '') return
    PUT('/oauth/dept', dept).then(res => {
      if(res.status) {
        closeEvent()
      }
    })
  }
  const closeEvent = () => {
    setDept({deptName: '', name: ''})
    handleClose()
  }
  return (
    <div className="modal show d-block" tabIndex="-1" role="dialog">
      <div className="modal-dialog modal-dialog-centered">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">부서 추가</h5>
          </div>
          <div className="modal-body">
            <div className="input-group mb-3">
              <span className="input-group-text" style={{width: '90px'}}>부서명</span>
              <input type="text" className="form-control" name="deptName" value={dept.deptName} onChange={changeEvent} />
            </div>
            <div className="input-group mb-3">
              <span className="input-group-text" style={{width: '90px'}}>부서코드</span>
              <input type="text" className="form-control" name="name" value={dept.name} onChange={changeEvent} />
            </div>
          </div>
          <div className="modal-footer">
            <button type="button" className="btn btn-outline-success" onClick={saveEvent}>부서 추가</button>
            <button type="button" className="btn btn-outline-secondary" onClick={closeEvent}>취소</button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default DeptModal