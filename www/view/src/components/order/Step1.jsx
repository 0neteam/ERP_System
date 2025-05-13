import React, { useState, useEffect, useRef } from 'react'
import { GET, PUT, DELETE } from '@utils/Network.js'
import Pagination from '@components/commons/Pagination.jsx'

const Step1 = ({id, items, status, show, outEvent, page, setPage}) => {
  const [orderItems, setOrderItems] = useState([])
  const [pagination, setPagination] = useState([])
  
  const [total, setTotal] = useState(4)
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
  const itemEvent = data => {
    if(show === 1) {
      if(data.qty == data.iqty) return
      if((data.qty - data.oqty) + data.pqty == 0) return
      if((data.iqty === 0 && data.pqty === 0) && data.qty <= data.oqty) return
      if((data.oqty - data.pqty) == data.iqty ? data.qty == data.iqty : false) return
      outEvent(data)
    }
  }
  const deleteEvent = () => {
    DELETE(`/stg/order/${id}`).then(res => {
      if(res.status) document.location.href = '/order'
    })
  }
  useEffect(() => {
    setOrderItems(items.content)
    const arr = []
    for(let i = 0; i < items.totalPages; i++) {
      const active = page == i
      arr[i] = {page: i+1, active}
    }
    setPagination(arr)
    setTotal(items.totalPages)
  }, [items])
  return (
    <>
      <div className="overflow-y-auto mb-3">
        <table>
          <thead>
            <tr>
              <th className="text-nowrap">품목코드</th>
              <th className="text-nowrap">단위수량</th>
              <th className="text-nowrap">발주량</th>
              <th className="text-nowrap">출고량</th>
              <th className="text-nowrap">입고량</th>
              <th className="text-nowrap">불량</th>
            </tr>
          </thead>
          <tbody>
            {orderItems?.map((v, i) => {
              return (
                <tr style={{cursor: show === 1 ? 'pointer' : ''}} key={i} onClick={() => itemEvent(v)}>
                  <td>{v.itemNo}</td>
                  <td>{v.bundle}</td>
                  <td>{v.qty}</td>
                  <td>{v.oqty}</td>
                  <td>{v.iqty}</td>
                  <td>{v.pqty}</td>
                </tr>
              )
            })}

          </tbody>
        </table>
      </div>
      

      {status == 1 &&
      <div className="d-flex justify-content-end gap-2 mt-2">
        <a className="btn btn-outline-success" href={`/order/edit/${id}`}>발주수정</a>
        <button type="button" className="btn btn-outline-secondary" onClick={deleteEvent}>발주취소</button>
      </div>
      }
      {orderItems?.length > 0 && (
      <Pagination pagination={pagination} clickEvent={clickEvent} page={page} total={total} />
      )}
    </>
  )
}

export default Step1