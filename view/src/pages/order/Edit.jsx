import { useState, useEffect } from 'react'
import { useParams } from "react-router-dom"
import { GET, POST, PUT, PATCH } from '@utils/Network.js'
import Pagination from '@components/commons/Pagination.jsx'
import ItemModal from '@components/order/ItemModal.jsx'

const Edit = () => {
  const { id } = useParams()
  const [orders, setOrders] = useState([])
  const [allChecked, setAllChecked] = useState(false)
  const [checkedItems, setCheckedItems] = useState([])
  const [showModal, setShowModal] = useState(false)
  const handleOpen = () => setShowModal(true)
  const handleClose = () => setShowModal(false)
  const addOrderEvent = order => {
    const c = orders.filter(o => o.itemNo === order.no)
    if(c.length === 0) {
      const item = {
        itemNo: order.no,
        itemName: order.name,
        bundle: order.bundle,
        price: order.price,
        qty: 0
      }
      setOrders([...orders, item].sort((a, b) => b.itemNo - a.itemNo))
    }
  }
  const deleteEvent = () => {
    checkedItems?.map(no => setOrders(prev => prev.filter(order => order.itemNo !== no)))
    setCheckedItems([])
    setAllChecked(false)
  }
  const orderEvent = () => {
    PATCH(`/stg/order/${id}`, {items: orders, status: 1}).then(res => {
      if(res.status) document.location.href = `/order/${id}`
    })
  }
  const isChecked = no => checkedItems.includes(no)
  const handleRowClick = (no) => {
    setCheckedItems(prev => (prev.includes(no)) 
      ? prev.filter(itemNo => itemNo !== no)
      : [...prev, no]
    )
  }
  const changeEvent = (e, data) => {
    setOrders(prev => prev.filter(order => {
      if(order.itemNo === data.itemNo) {
        order.qty = e.target.value
      }
      return true
    }))
  }
  const allCheckedEvent = checked => {
    setAllChecked(checked)
    setCheckedItems([])
    if(checked) {
      const arr = []
      orders?.map(item => arr[arr.length] = item.itemNo)
      setCheckedItems([...arr])
    }
  }
  const getData = () => {
    POST(`/stg/order/edit/${id}`, {}).then(res => {
      console.log(res)
      if(res.status) {
        setOrders(res.result)
      }
    })
  }
  useEffect(() => {
    getData()
  }, [])
  return (
    <>
      <section className="container">
        <h2 className="mt-4 mb-4">발주 수정</h2>
        <div className="d-flex justify-content-between mb-2">
          <div>
            <button type="button" className="btn btn-outline-success" onClick={handleOpen}>품목선택</button>
          </div>
          <div>
            <button type="button" className="btn btn-outline-secondary" onClick={deleteEvent}>선택삭제</button>
          </div>
        </div>
        <div className="overflow-y-auto">
          <table>
            <thead>
                <tr className="text-center">
                  <th className="text-nowrap"><input type="checkbox" checked={allChecked} onChange={() => allCheckedEvent(!allChecked)} /></th>
                  <th className="text-nowrap">품목번호</th>
                  <th className="text-nowrap">품목명</th>
                  <th className="text-nowrap">단위수량</th>
                  <th className="text-nowrap">출고단위신청</th>
                </tr>
            </thead>
            <tbody>
              {orders?.map((v, i) => {
                return (
                  <tr key={i}>
                    <td><input type="checkbox" checked={isChecked(v.itemNo)} onChange={() => handleRowClick(v.itemNo)} /></td>
                    <td>{v.itemNo}</td>
                    <td>{v.itemName}</td>
                    <td>{v.bundle}</td>
                    <td><input type="number" className="w-100" placeholder='0' name="qty" value={v.qty} onChange={(e) => changeEvent(e, v)}/></td>
                  </tr>
                )
              })}
              {orders.length == 0 && <tr className="text-center fs-6"><td colSpan="5">품목을 추가하세요.</td></tr>}
            </tbody>
          </table>
        </div>
        <div className="d-flex justify-content-end gap-2 mt-4 mb-4">
          <button type="button" className="btn btn-outline-success" onClick={orderEvent}>발주 저장</button>
          <a type="button" className="btn btn-outline-secondary" href={`/order/${id}`}>취소</a>
        </div>
      </section>
      {showModal && <ItemModal handleClose={handleClose} addOrderEvent={addOrderEvent} orders={orders} />}
    </>
  )
}

export default Edit