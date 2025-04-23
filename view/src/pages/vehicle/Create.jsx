import { useState, useRef } from 'react'
import { PUT } from '@utils/Network.js'

const Create = () => {
  const [vehicle, setVehicle] = useState({regNumber: '', type: 0, name: '', licence: 0})
  const fileRef = useRef(null);
  const blockStyle = {width: '120px'}
  const changeEvent = (e) => {
    const {name, value} = e.target
    setVehicle({...vehicle, [name]: value})
  }
  const submitEvent = e => {
    e.preventDefault()
    const form = new FormData();
    if(fileRef.current.files.length > 0) {
      form.append('file', fileRef.current.files[0])
    }
    form.append('regNumber', vehicle.regNumber)
    form.append('type', vehicle.type)
    form.append('name', vehicle.name)
    form.append('licence', vehicle.licence)
    PUT('/trs/vehicle', form).then(res => {
      if(res.status) document.location.href = '/vehicle'
    })

  }
  return (
    <section className="container d-flex justify-content-center align-items-center" style={{minHeight: '70vh'}}>
      <div className="d-flex flex-column flex-md-row w-100 justify-content-center align-items-center gap-3">

        <form className="item-container w-100 w-mb-50" onSubmit={submitEvent}>
          <div>
            <div className="input-group mb-2">
              <span className="input-group-text" style={blockStyle}>등록번호</span>
              <input type="text" className="form-control" placeholder="차량 번호를 입력하세요." name="regNumber" value={vehicle.regNumber} onChange={changeEvent} />
            </div>
            <div className="input-group mb-2">
              <span className="input-group-text" style={blockStyle}>차종</span>
              <select className="form-select" required name="type" value={vehicle.type} onChange={changeEvent}>
                <option value="0">차종을 선택하세요.</option>
                <option value="1">소형화물</option>
                <option value="2">카고</option>
                <option value="3">탑차</option>
                <option value="4">윙바디</option>
                <option value="5">트레일러</option>
                </select>
            </div>
            <div className="input-group mb-2">
              <span className="input-group-text" style={blockStyle}>차량명</span>
              <input type="text" className="form-control" placeholder="차량명을 입력하세요." name="name" value={vehicle.name} onChange={changeEvent} />
            </div>
            
            <div className="input-group mb-2">
              <span className="input-group-text" style={blockStyle}>면허</span>
              <select className="form-select" name="licence" value={vehicle.licence} onChange={changeEvent}>
                <option value="0">면허증을 선택하세요.</option>
                <option value="1">1종 특수</option>
                <option value="2">1종 대형</option>
                <option value="3">1종 보통</option>
                <option value="4">2종 보통</option>
              </select>
            </div>

            <div className="input-group mb-3">
              <label className="input-group-text text-center" style={blockStyle}>자동차등록증</label>
              <input type="file" className="form-control" ref={fileRef} />
            </div>
          </div>

          <div className="btns d-flex w-100 justify-content-end gap-2">
            <button type="submit" className="btn btn-outline-primary">저장</button>
            <a className="btn btn-outline-secondary" href="/vehicle">취소</a>
          </div>
        </form>
      </div>
    </section>
  )
}

export default Create