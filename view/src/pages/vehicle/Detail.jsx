import { useState, useEffect, useRef } from 'react'
import { useParams } from "react-router-dom"
import { POST, PATCH } from '@utils/Network.js'

const Detail = () => {
  const { id } = useParams()
  const [edit, isEdit] = useState(true)
  const [vehicle, setVehicle] = useState({no: '', regNumber: '', type: '', name: '', licence: 0, status: 0, regDate: ''})
  const fileRef = useRef(null);
  const baseUrl = import.meta.env.VITE_APP_GATEWAY_URL + '/oauth/file/'
  const blockStyle = {width: '100px'}
  const getLicence = licence => {
    if(licence === 1) return '1종 특수'
    if(licence === 2) return '1종 대형'
    if(licence === 3) return '1종 보통'
    if(licence === 4) return '2종 보통'
    return ''
  }
  const checkStatus1 = s => {
    if(s==1) return false
    if(s==2) return true
    if(s==3) return false
    if(s==4) return false
  }
  const checkStatus2 = s => {
    if(s==1) return false
    if(s==2) return true
    if(s==3) return false
    if(s==4 && edit) {
      return true
    } else {
      return false
    }
  }
  const changeEvent = (e) => {
    const {name, value} = e.target
    setVehicle({...vehicle, [name]: value})
  }
  const fileChange = () => {

  }
  const fileEvent = () => {
    if(!edit) {
      fileRef.current.click()
    } else {
      try {
        const link = document.createElement('a')
        link.href = baseUrl + vehicle.fileNo
        document.body.appendChild(link)
        link.click()
        link.remove()
      } catch(error) {
        console.error(error)
      }
    }
  }
  const editEvent = () => {
    if(!edit) {
      const form = new FormData();
      if(fileRef.current.files.length > 0) {
        form.append('file', fileRef.current.files[0])
      } 
      form.append('status', vehicle.status)
      PATCH(`/trs/vehicle/${id}`, form).then(res => {
      })
    }
    isEdit(!edit)
  }
  const cancelEvent = () => {
    document.location.href = "/vehicle"
  }
  const getData = () => {
    POST(`/trs/vehicle/${id}`, {}).then(res => {
      if(res.status) {
        setVehicle(res.result)
      }
    })
  }
  useEffect(() => {
    getData()
  }, [])
  return (
    <section className="container d-flex justify-content-center align-items-center" style={{minHeight: '70vh'}}>
      <div className="d-flex flex-column flex-md-row w-100 justify-content-center align-items-center gap-3">

          <div className="item-container w-100 w-mb-50">
              <div>
                  <div className="input-group mb-2">
                      <span className="input-group-text text-nowrap" style={blockStyle}>No</span>
                      <input type="text" className="form-control" name="no" value={vehicle.no} disabled onChange={changeEvent}/>
                  </div>
                  <div className="input-group mb-2">
                      <span className="input-group-text text-nowrap" style={blockStyle}>등록번호</span>
                      <input type="text" className="form-control" name="regNumber" value={vehicle.regNumber} disabled onChange={changeEvent}/>
                  </div>
                  <div className="input-group mb-2">
                      <span className="input-group-text text-nowrap" style={blockStyle}>차종</span>
                      <input type="text" className="form-control" name="type" value={vehicle.type} disabled onChange={changeEvent}/>
                  </div>
                  <div className="input-group mb-2">
                      <span className="input-group-text text-nowrap" style={blockStyle}>차량명</span>
                      <input type="text" className="form-control" name="name" value={vehicle.name} disabled onChange={changeEvent}/>
                  </div>
                  <div className="input-group mb-2">
                      <span className="input-group-text text-nowrap" style={blockStyle}>면허</span>
                      <input type="text" className="form-control" name="licence" defaultValue={getLicence(vehicle.licence)} disabled />
                  </div>
                  <div className="input-group mb-2">
                      <span className="input-group-text text-nowrap" style={blockStyle}>상태</span>
                      <select className="form-select" disabled={edit} name="status" value={vehicle.status} onChange={changeEvent}>
                          <option value="1">대기</option>
                          {checkStatus1(vehicle.status) &&
                          <option value="2">운송</option>
                          }
                          <option value="3">점검</option>
                          <option value="4">폐차</option>
                      </select>
                  </div>
                  <div className="input-group mb-2">
                      <span className="input-group-text text-nowrap" style={blockStyle}>등록일자</span>
                      <input type="date" className="form-control" name="regDate" value={vehicle.regDate} disabled onChange={changeEvent}/>
                  </div>
                  <div className="mb-2">
                    {(vehicle.fileNo != null && edit && vehicle.status != 4) &&
                      <button className="btn btn-success" onClick={fileEvent}>자동차등록증 다운로드</button>
                    }
                    {!edit &&
                      <button className="btn btn-success" onClick={fileEvent}>자동차등록증 업로드</button>                    
                    }
                    <input type="file" className="file-input d-none" ref={fileRef} onChange={fileChange}/>
                  </div>
              </div>

              <div className="btns d-flex w-100 justify-content-end gap-2">
                  <div className='d-flex gap-1'>
                      <button className="btn btn-outline-success" onClick={editEvent} disabled={checkStatus2(vehicle.status)}>{edit ? '수정' : '저장'}</button>
                      <button className="btn btn-outline-secondary" onClick={cancelEvent}>취소</button>
                  </div>
              </div>
          </div>
      </div>
  </section>
  )
}

export default Detail