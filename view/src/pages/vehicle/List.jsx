import { useState, useEffect } from 'react'
import { POST } from '@utils/Network.js'
import Pagination from '@components/commons/Pagination.jsx';

const List = () => {
  const [vehicle, setVehicle] = useState([])
  const [point, setPoint] = useState(0)
  const [query, setQuery] = useState('')
  const [pagination, setPagination] = useState([])
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
  const getLicence = licence => {
    if(licence === 1) return '1종 특수'
    if(licence === 2) return '1종 대형'
    if(licence === 3) return '1종 보통'
    if(licence === 4) return '2종 보통'
    return ''
  }
  const getStatus = status => {
    if(status === 1) return '대기'
    if(status === 2) return '운송'
    if(status === 3) return '점검'
    if(status === 4) return '폐차'
    return ''
  }
  const getType = type => {
    if(type === 1) return '소형화물'
    if(type === 2) return '카고'
    if(type === 3) return '탑차'
    if(type === 4) return '윙바디'
    if(type === 5) return '트레일러'
  }
  const typeEvent = e => {
    setPoint(Number(e.target.value))
    setQuery('')
    if(Number(e.target.value) === 0) getData({point: 0})
  }
  const linkEvent = no => {
    document.location.href = `/vehicle/${no}`
  }
  const submitEvent = e => {
    e.preventDefault()
    const params = {point}
    if(point === 1) params['regNumber'] = query
    else if(point === 2) params['name'] = query
    else if(point === 3) params['type'] = e.target.select1.value
    else if(point === 4) params['licence'] = e.target.select2.value
    else if(point === 5) params['status'] = e.target.select3.value
    getData(params)
  }
  const getData = (params) => {
    POST(`/trs/vehicle?size=${size}&page=${page}`, params).then(res => {
      if(res.status) {
        setVehicle(res.result.list)
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
    getData({point})
  }, [])
  return (
    <section className="container" style={{minHeight: '70vh'}}>
      <div>
        <h2 className="mb-4">차량 목록</h2>
      </div>
      <div className="mb-4">
        <form className="form" onSubmit={submitEvent}>
          <div className="row">
            <div className="col-12 col-md-4">
              <select className="form-select" onChange={typeEvent}>
                <option value="0">전체</option>
                <option value="1">등록번호</option>
                <option value="2">차량명</option>
                <option value="3">차종</option>
                <option value="4">면허</option>
                <option value="5">상태</option>
              </select>
            </div>
            {point != 0 &&
              <div className="col-12 col-md-8">
                <div className="input-group">
                  {point < 3 && <input type="text" className="form-control" name="query" value={query} onChange={e => setQuery(e.target.value)} />}
                  {point === 3 &&
                  <select className="form-select" name="select1">
                    <option value="0">차종 전체</option>
                    <option value="1">소형화물</option>
                    <option value="2">카고</option>
                    <option value="3">탑차</option>
                    <option value="4">윙바디</option>
                    <option value="5">트레일러</option>
                  </select>
                  }
                  {point === 4 &&
                  <select className="form-select" name="select2">
                    <option value="0">면허 전체</option>
                    <option value="1">1종 특수</option>
                    <option value="2">1종 대형</option>
                    <option value="3">1종 보통</option>
                    <option value="4">2종 보통</option>
                  </select>
                  }
                  {point === 5 &&
                  <select className="form-select" name="select3">
                    <option value="0">상태 전체</option>
                    <option value="1">대기 중</option>
                    <option value="2">운송 중</option>
                    <option value="3">점검 중</option>
                  </select>
                  }
                  <button type="submit" className="btn btn-outline-success">검색</button>
                </div>
              </div>
              }
          </div>
        </form>
      </div>

      <div className="overflow-y-auto mb-4">
        <table className="mt-3">
          <thead>
            <tr className="text-center">
              <th className="text-nowrap">차종</th>
              <th className="text-nowrap">차량명</th>
              <th className="text-nowrap">등록번호</th>
              <th className="text-nowrap">면허</th>
              <th className="text-nowrap">상태</th>
            </tr>
          </thead>
          <tbody>
            {vehicle?.map((v, i) => {
              return (
                <tr style={{cursor: 'pointer'}} key={i} onClick={() => linkEvent(v.no)}>
                  <td>{getType(v.type)}</td>
                  <td>{v.name}</td>
                  <td>{v.regNumber}</td>
                  <td>{getLicence(v.licence)}</td>
                  <td>{getStatus(v.status)}</td>
                </tr> 
              )
            })}
            {vehicle.length == 0 &&
              <tr className='text-center'><td colSpan="5">차량 목록이 없습니다.</td></tr>
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