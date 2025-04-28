import { useState, useEffect } from 'react'
import { POST } from '@utils/Network.js'

const Pagination = ({pagination, clickEvent, page, total}) => {
  const oneStepEvent = type => {
    if(type === 'p') {
      clickEvent(page == 0 ? page : page - 1)
    } 
    if(type === 'n') {
      clickEvent(page == total-1 ? page : page + 1)
    }
    if(type === 'P') {
      clickEvent(0)
    }
    if(type === 'N') {
      clickEvent(total-1)
    }
  }
  return (
    <div className="d-flex justify-content-center mt-2">
      <div aria-label="Page navigation example">
        <ul className="pagination justify-content-center">
          <li className="page-item">
            <button className="page-link" aria-label="First" onClick={() => oneStepEvent('P')}>
              <span aria-hidden="true">&laquo;</span>
            </button>
          </li>
          <li className="page-item">
            <button className="page-link" aria-label="Previous" onClick={() => oneStepEvent('p')}>
              <span aria-hidden="true">&lsaquo;</span>
            </button>
          </li>
          {
            pagination?.map((v, i) => {
              return (
                <li className="page-item" key={i} >
                  <button className={v.active ? 'page-link active' : 'page-link'}  onClick={()=> clickEvent(i)}>{v.page}</button>
                </li>
              )
            })
          }
          <li className="page-item">
            <button className="page-link" aria-label="Next" onClick={() => oneStepEvent('n')}>
              <span aria-hidden="true">&rsaquo;</span>
            </button>
          </li>
          <li className="page-item">
            <button className="page-link" aria-label="Last" onClick={() => oneStepEvent('N')}>
              <span aria-hidden="true">&raquo;</span>
            </button>
          </li>
        </ul>
      </div>
    </div>
  )
}

const List = () => {
  const [transps, setTransps] = useState([])
  const [type, setType] = useState(0)
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
    setStartDate('')
    setEndDate('')
  }
  const checkDate = data => {
    return (data) ? 'O' : '-'
  }
  const submitEvent = e => {
    e.preventDefault()
    getData()
  }
  const getData = () => {
    let params = {type}
    if(type !== 0) {
      if(startDate !== '') params.startDate = startDate
      if(endDate !== '') params.endDate = endDate
    }
    POST(`/trs/transp?size=${size}&page=${page}`, params).then(res => {
      if(res.status) {
        setTransps(res.result.list)
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
  }, [page])
  return (
    <section className="container" style={{minHeight: '60vh'}}>
      <div>
        <div className="row">
          <div className="col-12">
            <h2 className="mb-4">운송 목록</h2>
          </div>
        </div>
        <form className="row g-2 align-items-center" onSubmit={submitEvent} >
          <div className="col-12 col-md-2">
            <select className="form-select" onChange={typeEvent}>
              <option value="0">전체</option>
              <option value="1">출발</option>
              <option value="2">도착</option>
            </select>
          </div>
          {type != 0 &&
          <div className="col-12 col-md">
            <div className="input-group">
              <input type="date" className="form-control dateInput" placeholder="시작일" value={startDate} onChange={e => setStartDate(e.target.value)} />
              <span className="input-group-text">~</span>
              <input type="date" className="form-control dateInput" placeholder="종료일" value={endDate} onChange={e => setEndDate(e.target.value)} />
              <button type="submit" className="btn btn-outline-success">검색</button>
            </div>
          </div>
          }
        </form>
      </div>
      <div className="overflow-y-auto">
        <table className="mt-3">
          <thead>
          <tr className="text-center">
            <th className="text-nowrap">운송번호</th>
            <th className="text-nowrap">운송요청일</th>
            <th className="text-nowrap">출발</th>
            <th className="text-nowrap">도착</th>
            <th className="text-nowrap">배송기사</th>
          </tr>
          </thead>
          <tbody>
            {transps?.map((v, i) => {
              return (
                <tr key={i} style={{cursor: 'pointer'}} onClick={() => document.location.href = `/transp/${v.no}`}>
                  <td>{v.no}</td>
                  <td>{v.regDate}</td>
                  <td>{checkDate(v.depDate)}</td>
                  <td>{checkDate(v.arrDate)}</td>
                  <td>{v.userName}</td>
                </tr>
              )
            })}
            {transps.length == 0 &&
              <tr className='text-center'><td colSpan="5">운송 목록이 없습니다.</td></tr>
            }
          </tbody>
        </table>
      </div>
      {pagination.length > 0 &&
      <Pagination pagination={pagination} clickEvent={clickEvent} page={page} total={total} />
      }
    </section>
  )
}

export default List