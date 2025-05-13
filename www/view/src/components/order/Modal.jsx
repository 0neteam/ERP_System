import React, { useState, useEffect, useRef } from 'react'
import { GET, PUT, PATCH } from '@utils/Network.js'

const Modal = ({handleClose, transp, checkRole}) => {
  const searchRef = useRef(null)
  const [releaseItems, setReleaseItems] = useState([])
  const [driInfo, setDriInfo] = useState([])
  const [point, setPoint] = useState(0)
  const closeEvent = () => {
    searchRef.current.value = ''
    handleClose()
  }
  const getData = () => {
    GET(`/mfr/release/${transp.transpNo}`, {}).then(res => {
      if(res.status) {
        setDriInfo(res.result[0])
        setReleaseItems(res.result[1].list)
      }
    })
  }
  const changeEvent = (e, data) => {
    setReleaseItems(prev => prev.filter(item => {
      if(item.itemNo === data.itemNo) {
        item.iqty = Number(e.target.value)
        item.edit = true
      }
      return true
    }))
  }
  const submitEvent = (e) => {
    e.preventDefault()
    setReleaseItems(prev => prev.filter(item => {
      if(item.itemNo === Number(e.target.itemNo.value)) item.search = true
      else if(e.target.itemNo.value === '') item.search = true
      else item.search = false
      return true
    }))
  }
  const checkEvent = (data) => {
    if(data.search === false) return false
    else return true
  }
  const itemsEvent = () => {
    PATCH(`/mfr/release/${transp.transpNo}`, {releases: releaseItems, point: point, orderNo: transp.orderNo}).then(res => {
      if(res.status) closeEvent()
    })
  }
  useEffect(() => {
    if(point !== 0) itemsEvent()
  },[point])
  useEffect(() => {
    getData()
  }, [])
  return (
    <div className="modal show d-block" id="IOModal">
      <div className="modal-dialog modal-xl modal-dialog-scrollable">
        <div className="modal-content">
          <div className="modal-header">
              <h3>입출고 관리</h3>
          </div>
          <div className="modal-body">
            <div className="container">
              <div className="d-flex flex-column flex-md-row justify-content-between align-items-center gap-2 mb-2">
                <div className="d-flex gap-2 order-md-2 justify-content-center">
                  {checkRole(['ADMIN','MFR']) &&
                  <button type="button" className="btn btn-outline-success" onClick={() => setPoint(1)} disabled={transp.depDate}>출고 완료</button>
                  }
                  {checkRole(['ADMIN','STG']) &&
                  <button type="button" className="btn btn-outline-success" onClick={() => setPoint(2)} disabled={transp.depDate == null || (transp.depDate != null && driInfo.depDate == null) || transp.arrDate}>입고 완료</button>
                  }
                  <button type="button" className="btn btn-outline-secondary" onClick={closeEvent}>닫기</button>
                </div>
                <div className="d-flex align-items-center order-md-1 text-center">
                  <p className="mb-0">발주번호 : {transp.orderNo}</p>
                </div>
              </div>
                <div className="row g-2 mb-2">
                  <div className="col-12 col-md-6">
                    <div className="input-group">
                        <span className="input-group-text" style={{width: '95px'}}>운송자이름</span>
                        <input type="text" className="form-control" defaultValue={driInfo.userName} readOnly />
                    </div>
                  </div>
                  <div className="col-12 col-md-6">
                    <div className="input-group">
                      <span className="input-group-text" style={{width: '95px'}}>연락처</span>
                      <input type="text" className="form-control" defaultValue={driInfo.userEmail} readOnly />
                    </div>
                  </div>
                </div>
                <div className="row g-2">
                  <div className="col-12 col-md-6">
                    <div className="input-group">
                      <span className="input-group-text" style={{width: '95px'}}>출발일</span>
                      <input type="text" className="form-control" defaultValue={driInfo.depDate} readOnly />
                    </div>
                  </div>
                  <div className="col-12 col-md-6">
                    <div className="input-group">
                      <span className="input-group-text" style={{width: '95px'}}>도착일</span>
                      <input type="text" className="form-control" defaultValue={driInfo.arrDate} readOnly />
                    </div>
                  </div>
                </div>
                <form className="input-group mt-2 mb-2" onSubmit={submitEvent}>
                  <input type="number" className="form-control" name='itemNo' ref={searchRef} />
                  <button type="submit" className="btn btn-outline-success">검색</button>
                </form>
                <div className="overflow-y-auto tableHeight">
                  <table className="mb-2">
                    <thead style={{position: 'sticky', top: '0', zIndex: '1'}}>
                      <tr>
                        <th className="text-nowrap">품목코드</th>
                        <th className="text-nowrap">출고수량</th>
                        <th className={(transp.arrDate === null && transp.depDate === null) ? 'd-none' : 'd-block text-nowrap'}>입고량</th>
                      </tr>
                    </thead>
                    <tbody>
                      {releaseItems?.map((v, i) => {
                        if(v.iqty === 0 && v.edit === undefined) v.iqty = v.oqty
                        return checkEvent(v) &&
                          (
                          <tr key={i}>
                            <td>{v.itemNo}</td>
                            <td>{v.oqty}</td>
                            <td className={(transp.arrDate === null && transp.depDate === null) ? 'd-none' : 'd-block'}><input type="number" style={{width: '100%'}} value={v.iqty} onChange={(e) => changeEvent(e, v)} max={v.oqty} min={0} disabled={transp.arrDate != null}/></td>
                          </tr>
                        )
                      })}
                    </tbody>
                  </table>
                </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Modal