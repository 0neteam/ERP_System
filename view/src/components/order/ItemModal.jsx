import React, { useState, useEffect } from 'react'
import { GET, PUT } from '@utils/Network.js'

const ItemModal = ({handleClose, addOrderEvent, orders}) => {
  const [items, setItems] = useState([])
  const [pagination, setPagination] = useState([{page: 1, active: true}])
  const [page, setPage] = useState(0)
  const [total, setTotal] = useState(4)
  const [select, setSelect] = useState(0)
  const [query, setQuery] = useState('')
  const size = 10
  const selectStyle = no => {
    const c = orders.filter(o => o.itemNo === no)
    if(c.length === 1) return '#f8f8f8'
    return '#fff'
  }
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
  const selectEvent = e => {
    setSelect(e.target.value)
    setQuery('')
  }
  const changeEvent = e => setQuery(e.target.value)
  const submitEvent = e => {
    e.preventDefault()
    let params = {page, size}
    if(e.target.select.value == 1) params['no'] = query
    if(e.target.select.value == 2) params['name'] = query
    getData(params)
  }
  const getData = params => {
    GET('/mfr/item', params).then(res => {
      if(res.status) {
        setItems(res.result.list)
        const arr = []
        for(let i = 0; i < res.result.totalPages; i++) {
          const active = page == i
          arr[i] = {page: i+1, active}
        }
        setPagination(arr)
        setTotal(res.result.totalPages)
      }
    })
  }
  useEffect(() => {
    getData({page, size})
  }, [page, orders])
  return (
    <div className="modal show d-block" id="productModal">
      <div className="modal-dialog modal-xl">
        <div className="modal-content">
          <div className="modal-header">
            <h3>품목선택</h3>
          </div>
          <div className="modal-body">
            <div className="container">
              <form className="row" onSubmit={submitEvent}>
                <div className="col-12 col-md-4 mb-2">
                  <div className="input-group">
                    <select className="form-select" name="select" value={select} onChange={selectEvent}>
                      <option value="0">전체</option>
                      <option value="1">품목코드</option>
                      <option value="2">품목명</option>
                    </select>
                  </div>
                </div>
                {select != 0 &&
                <div className="col-12 col-md-8 mb-2">
                  <div className="input-group">
                    <input type="text" className="form-control" name="query" value={query} onChange={changeEvent} placeholder="검색 내용을 입력하세요."/>
                    <button type="submit" className="btn btn-outline-success">검색</button>
                  </div>
                </div>
                }
              </form>
              <div className="mt-2 overflow-y-auto">
                <table>
                  <thead>
                    <tr>
                      <th className="text-nowrap">품목코드</th>
                      <th className="text-nowrap">품목명</th>
                      <th className="text-nowrap">단위수량</th>
                    </tr>
                  </thead>
                  <tbody>
                  {items?.map((v, i) => {
                    return (
                      <tr style={{cursor: 'pointer'}} key={i} onClick={() => addOrderEvent(v)} >
                        <td style={{backgroundColor: selectStyle(v.no)}}>{v.no}</td>
                        <td style={{backgroundColor: selectStyle(v.no)}}>{v.name}</td>
                        <td style={{backgroundColor: selectStyle(v.no)}}>{v.bundle}</td>
                      </tr>
                    )
                  })}
                  </tbody>
                </table>
              </div>
              <Pagination pagination={pagination} clickEvent={clickEvent} page={page} total={total} />
              <div className="d-flex justify-content-end gap-2">
                <button type="button" className="btn btn-outline-secondary" onClick={handleClose}>닫기</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default ItemModal