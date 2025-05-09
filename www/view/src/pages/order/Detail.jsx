import { useState, useEffect, useRef } from 'react'
import { useParams } from "react-router-dom"
import { useAuth } from '@hooks/AuthProvider.jsx'
import { POST } from '@utils/Network.js'
import Step1 from '@components/order/Step1.jsx'
import Step2 from '@components/order/Step2.jsx'
import Step3 from '@components/order/Step3.jsx'
import Step4 from '@components/order/Step4.jsx'
import Step5 from '@components/order/Step5.jsx'

const Detail = () => {
  const { id } = useParams()
  const [order, setOrder] = useState({})
  const [items, setItems] = useState([])
  const [outItems, setOutItems] = useState([])
  const [vehicleNo, setVehicleNo] = useState(0)
  const [licence, setLicence] = useState(0)
  const [status, setStatus] = useState(0)
  const [show, isShow] = useState(0)
  const { isAccess, styles } = useAuth()
  const [page, setPage] = useState(0)
  const checkRole = data => {
    let cnt = 0;
    if(isAccess().roles != null) {
      for(let role of data) {
        if(isAccess().roles?.search(role) >= 0) cnt += 1
      }
    }
    return cnt > 0 ? true : false
  }
  
  const changeStatus = s => {
    if(s === 1) return '발주요청'
    if(s === 2) return '발주진행'
    if(s === 3) return '발주완료'
    if(s === 4) return '발주취소'
  }
  const outEvent = data => {
    const c = outItems.filter(o => o.itemNo === data.itemNo)
    if(c.length === 0) {
      const item = {
        orderItemNo: data.no,
        itemNo: data.itemNo,
        name: data.name,
        qty: data.qty,
        maxQty: (data.qty - data.iqty),
        oqty: (data.qty - data.iqty)
      }
      setOutItems([...outItems, item].sort((a, b) => a.itemNo - b.itemNo))
    }
  }
  const checkData = () => {
    getData()
  }
  const getData = () => {
    POST(`/stg/order/${id}?page=${page}`, {}).then(res => {
      if(res.status) {
        setOrder(res.result.order)
        setStatus(res.result.order.status)
        setItems(res.result.items)
      }
    })
  }
  useEffect(() => {
    getData()
  }, [page])
  return (
    <section className="container" style={styles}>
      <div>
        <h2 className="mt-4 mb-4">발주 상세</h2>
        <h4><span className="badge bg-warning">{changeStatus(order.status)}</span></h4>
        <div className="d-flex align-items-center">
          <p>발주번호 : {order.no}</p>
          <p className="ms-auto">발주요청일 : {order.reqDate}</p>
        </div>
      </div>

      {/* STEP1 : 발주 품목 화면 */}
      <Step1 items={items} status={status} id={id} show={show} outEvent={outEvent} page={page} setPage={setPage} />

      <div className={status === 4 ? 'd-flex justify-content-end gap-2 mt-4 mb-2 p-1' : 'd-flex justify-content-center gap-2 mt-4 mb-2 p-1'}>
        {status < 3 && checkRole(['ADMIN','MFR']) &&
        <button type="button" className="btn btn-outline-success w-100" onClick={() => isShow(1)}>출고요청</button>
        }
        {status == 2 && checkRole(['ADMIN','TRS']) &&
        <button type="button" className="btn btn-outline-success w-100" onClick={() => isShow(2)}>운송관리</button>
        }
        {(status > 1 && status < 4) && checkRole(['ADMIN','MFR','STG','TRS']) &&
        <button type="button" className="btn btn-outline-success w-100" onClick={() => isShow(4)}>입출고관리</button>
        }
        <button type="button" className="btn btn-outline-success" style={{width: status === 4 ? 'auto' : '100%'}} onClick={() => document.location.href = '/order'}>발주목록</button>
      </div>

      {/* STEP2 : 출고요청 화면 */}
      {status < 4 && show == 1 && <Step2 id={id} outItems={outItems} setOutItems={setOutItems} setStatus={setStatus} />}
      {/* STEP3 : 운송관리 (차량 선택) */}
      {status < 4 && show == 2 && <Step3 id={id} isShow={isShow} setVehicleNo={setVehicleNo} setLicence={setLicence} />}
      {/* STEP4 : 운송관리 (기사 선택) */}
      {status < 4 && show == 3 && <Step4 id={id} isShow={isShow} vehicleNo={vehicleNo} licence={licence} />}
      {/* STEP5 : 입출고관리 화면 */}
      {status < 4 && show == 4 && <Step5 id={id} checkData={checkData} checkRole={checkRole}/>}
    </section>
      
  )
}

export default Detail