import { useState, useEffect } from 'react'
import { POST } from '@utils/Network.js'
import Pagination from '@components/commons/Pagination.jsx'

const List = () => {
  const [orders, setOrders] = useState([])
  const [type, setType] = useState(0)
  const [query, setQuery] = useState('')
  const [status, setStatus] = useState(0)
  const [startDate, setStartDate] = useState('')
  const [endDate, setEndDate] = useState('')
  const [pagination, setPagination] = useState([{page: 1, active: true}])
  const [page, setPage] = useState(0)
  const [total, setTotal] = useState(4)
  const size = 10
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
  const typeEvent = e => {
    setType(Number(e.target.value))
    setQuery('')
    setStatus(0)
    setStartDate('')
    setEndDate('')
  }
  const changeStatus = (status) => {
    if(status === 1) return '발주요청'
    if(status === 2) return '발주진행'
    if(status === 3) return '발주완료'
    if(status === 4) return '발주취소'
  }
  const changeDate = (per, cancel) => {
    if(per != null) return per
    if(cancel != null) return cancel
    return "-"
  }
  const changeQueryEvent = e => {
    setQuery(e.target.value)
  }
  const submitEvent = e => {
    e.preventDefault()
    getData()
  }
  const linkEvent = no => {
    document.location.href = `/order/${no}`
  }
  const getData = () => {
    let params = {type}
    if(type === 1) params.orderNo = query
    if(type === 2) params.status = status
    if(type === 3) {
      if(startDate !== '') params.reqDateStart = startDate
      if(endDate !== '') params.reqDateEnd = endDate
    }
    if(type === 4) {
      if(startDate !== '') params.perDateStart = startDate
      if(endDate !== '') params.perDateEnd = endDate
    }
    if(type === 5) {
      if(startDate !== '') params.cancelDateStart = startDate
      if(endDate !== '') params.cancelDateEnd = endDate
    }
    POST(`/stg/order?size=${size}&page=${page}`, params).then(res => {
      if(res.status) {
        setOrders(res.result.list)
        const arr = []
        for(let i = 0; i < res.result.totalPages; i++) {
          const active = page == i
          arr[i] = {page: i+1, active}
        }
        setPagination([...arr])
        setTotal(res.result.totalPages)
      }
    })
  }
  useEffect(() => {
    getData()
  }, [page, type])
  return (
    <section className="container">
      <div className="d-flex justify-content-between mb-2 mt-4 mb-4">
          <div>
              <h2>발주 목록</h2>
          </div>
      </div>

      <div className="mb-4">
        <div className="row g-2">
          <div className="col-12 col-md-4">
            <select className="form-select" onChange={typeEvent}>
              <option value="0">전체</option>
              <option value="1">발주번호</option>
              <option value="2">발주상태</option>
              <option value="3">발주요청일</option>
              <option value="4">발주완료일</option>
              <option value="5">발주취소일</option>
            </select>
          </div>
          {type != 0 &&
          <div className="col-12 col-md-8">
            <form className="d-flex flex-column gap-2" onSubmit={submitEvent}>
              <div className="input-group">
                {type == 1 &&
                  <input type="number" className="form-control" placeholder="검색 번호를 입력하세요." value={query} onChange={changeQueryEvent} />
                }
                {type == 2 && 
                  <select className="form-select" value={status} onChange={e => setStatus(Number(e.target.value))}>
                    <option value="0">전체</option>
                    <option value="1">발주요청</option>
                    <option value="2">발주진행</option>
                    <option value="3">발주완료</option>
                    <option value="4">발주취소</option>
                  </select>
                }
                {type > 2 &&
                <>
                  <input type="date" id="startDate" className="form-control dateInput" placeholder="시작일" value={startDate} onChange={e => setStartDate(e.target.value)} />
                  <span className="input-group-text">~</span>
                  <input type="date" id="endDate" className="form-control dateInput" placeholder="종료일" value={endDate} onChange={e => setEndDate(e.target.value)}/>
                </>
                }
                <button type="submit" className="btn btn-outline-success">검색</button>
              </div>
            </form>
          </div>
          }
        </div>
      </div>
      <div className="overflow-y-auto">
        <table>
          <thead>
            <tr>
              <th className="text-nowrap">발주번호</th>
              <th className="text-nowrap">발주상태</th>
              <th className="text-nowrap">발주요청일</th>
              <th className="text-nowrap">발주완료/취소일</th>
            </tr>
          </thead>

          <tbody>
            {orders?.map((v, i) => {
              return (
                <tr style={{cursor: 'pointer'}} key={i} onClick={() => linkEvent(v.no)}>
                  <td>{v.no}</td>
                  <td>{changeStatus(v.status)}</td>
                  <td>{v.reqDate}</td>
                  <td>{changeDate(v.perDate, v.cancelDate)}</td>
                </tr>
              )
            })}
            {orders.length == 0 && <tr className='text-center'><td colSpan="4">데이터가 없습니다.</td></tr>}
          </tbody>
        </table>
      </div>
      {orders.length > 0 && (
      <Pagination pagination={pagination} clickEvent={clickEvent} page={page} total={total} />
      )}
    </section>
  )
}

export default List