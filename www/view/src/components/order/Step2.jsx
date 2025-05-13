import React, { useState, useEffect, useRef } from 'react'
import { GET, PUT } from '@utils/Network.js'

const Step2 = ({id, outItems, setOutItems, setStatus}) => {
  const [allChecked, setAllChecked] = useState(false)
  const [checkedItems, setCheckedItems] = useState([])
  const isChecked = no => checkedItems.includes(no)
  const handleRowClick = (no) => {
    setCheckedItems(prev => (prev.includes(no))
      ? prev.filter(itemNo => itemNo !== no)
      : [...prev, no]
    )
  }
  const changeEvent = (e, data) => {
    setOutItems(prev => prev.filter(item => {
      if(item.itemNo === data.itemNo) {
        item.oqty = e.target.value
      }
      return true
    }))
  }
  const allCheckedEvent = checked => {
    setAllChecked(checked)
    setCheckedItems([])
    if(checked) {
      const arr = []
      outItems?.map(item => arr[arr.length] = item.itemNo)
      setCheckedItems([...arr])
    }
  }
  const deleteEvent = () => {
    checkedItems?.map(no => setOutItems(prev => prev.filter(item => item.itemNo !== no)))
    setCheckedItems([])
    setAllChecked(false)
  }
  const outEvent = () => {
    PUT(`/mfr/release/${id}`, {releases: outItems}).then(res => {
      if(res.status) document.location.reload()
    })
  }
  return (
    <>
      <h2 className="mt-4 mb-4">출고 요청</h2>

      <div className="overflow-y-auto mt-1 mb-3">
        <table>
          <thead>
            <tr>
              <th className="text-nowrap"><input type="checkbox" checked={allChecked} onChange={() => allCheckedEvent(!allChecked)} /></th>
              <th className="text-nowrap">품목코드</th>
              <th className="text-nowrap">단위수량</th>
              <th className="text-nowrap">출고량</th>
            </tr>
          </thead>
          <tbody>
            {outItems?.map((v, i) => {
              return (
                <tr key={i}>
                    <td><input type="checkbox" checked={isChecked(v.itemNo)} onChange={() => handleRowClick(v.itemNo)} /></td>
                    <td>{v.itemNo}</td>
                    <td>{v.qty}</td>
                    <td><input type="number" className="w-100" placeholder={v.maxQty} max={v.maxQty} min={1} name="oqty" value={v.oqty} onChange={(e) => changeEvent(e, v)} /></td>
                </tr>
              )
            })}
            {outItems.length === 0 &&
            <tr className='text-center'>
              <td colSpan="5">발주 품목을 선택하세요.</td>
            </tr>
            }
          </tbody>
        </table>
      </div>
      <div className='d-flex justify-content-end gap-2'>
        <button type="button" className="btn btn-outline-success mt-2" onClick={deleteEvent} disabled={checkedItems.length === 0}>품목삭제</button>
        <button type="button" className="btn btn-outline-success mt-2" onClick={outEvent} disabled={(checkedItems.length > 0) || (outItems.length === 0)}>출고요청</button>
      </div>
    </>
  )
}

export default Step2