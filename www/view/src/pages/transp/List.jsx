import { useState, useEffect } from 'react'
import { POST } from '@utils/Network.js'
import Pagination from '@components/commons/Pagination.jsx'
import { useAuth } from '@hooks/AuthProvider.jsx'

const List = () => {
  const { styles } = useAuth()
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
  const formattedDate = () => {
    const eDate = new Date(); 
    return eDate.getFullYear() + '-' +
          String(eDate.getMonth() + 1).padStart(2, '0') + '-' +
          String(eDate.getDate()).padStart(2, '0');
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
    <section className="container" style={styles}>
      <div className="d-flex justify-content-between mb-2 mt-4 mb-4">
        <div>
            <h2>운송 목록</h2>
        </div>
      </div>

      <div className="mb-4">
        <div className="row g-2">
          <div className="col-12 col-md-4">
            <select className="form-select" onChange={typeEvent}>
              <option value="0">전체</option>
              <option value="1">출발</option>
              <option value="2">도착</option>
            </select>
          </div>
          <div className="col-12 col-md-8">
            <form className="d-flex flex-column gap-2" onSubmit={submitEvent} >
              <div className="input-group">
                {type != 0 && <>
                  <input type="date" className="form-control dateInput" placeholder="시작일" value={startDate} onChange={e => {
                        setStartDate(e.target.value)
                        setEndDate(formattedDate())
                        }} max={endDate == '' ? formattedDate() : endDate}/>
                  <span className="input-group-text">~</span>
                  <input type="date" className="form-control dateInput" placeholder="종료일" value={endDate} onChange={e => setEndDate(e.target.value)} min={startDate == '' ? formattedDate() : startDate}/>
                  <button type="submit" className="btn btn-outline-success">검색</button>
                </>
                }
              </div>
            </form>
          </div>
        </div>
      </div>
      
      <div className="overflow-y-auto">
        <table>
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