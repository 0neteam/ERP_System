import React, { useState, useEffect, useRef } from 'react'
import { GET, PUT } from '@utils/Network.js'
import Pagination from '@components/commons/Pagination.jsx'

const Step3 = ({id, isShow, setVehicleNo, setLicence}) => {
  const [vehicles, setVehicles] = useState([])
  const [no, setNo] = useState(0)
  const [point, setPoint] = useState(0)
  const [query, setQuery] = useState('')
  const [type, setType] = useState(0)
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
  const imgEvent1 = () => {
    isShow(3)
  }
  const imgEvent2 = data => {
    setNo(data.no)
    setVehicleNo(data.no)
    setLicence(data.licence)
  }
  const selectEvent = e => {
    setPoint(Number(e.target.value))
    if(Number(e.target.value) === 0) getData()
    setQuery('')
    setType(0)
    setNo(0)
  }
  const queryEvent = e => {
    setQuery(e.target.value)
  }
  const typeEvent = e => {
    setType(e.target.value)
  }
  const getType = type => {
    if(type === 1) return '소형화물'
    if(type === 2) return '카고'
    if(type === 3) return '탑차'
    if(type === 4) return '윙바디'
    if(type === 5) return '트레일러'
  }
  const submitEvent = e => {
    e.preventDefault()
    getData()
  }
  const getData = () => {
    const params = {point}
    if(point === 1) params['regNumber'] = query
    if(point === 2) params['name'] = query
    if(point === 3) params['type'] = type
    GET(`/trs/vehicle?size=${size}&page=${page}`, params).then(res => {
      if(res.status) {
        setVehicles(res.result.list)
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
    setVehicleNo(0)
    getData()
  }, [])
  return (
    <>
      <h2 className="mt-4 mb-4">차량 배정</h2>
      <div className="row">
        <div className="col-12 col-md-4">
          <select className="form-select" onChange={selectEvent}>
            <option value="0">전체</option>
            <option value="1">등록번호</option>
            <option value="2">차량명</option>
            <option value="3">차종</option>
          </select>
        </div>
        <div className="col-12 col-md-8">
          {point > 0 &&
          <form className="input-group" onSubmit={submitEvent}>
            {(point == 1 || point == 2) &&
              <input type="text" className="form-control" name="query" value={query} onChange={queryEvent} />
            }
            {point === 3 &&
              <select className="form-select" name="type" value={type} onChange={typeEvent}>
                <option value="0">전체</option>
                <option value="1">소형화물</option>
                <option value="2">카고</option>
                <option value="3">탑차</option>
                <option value="4">윙바디</option>
                <option value="5">트레일러</option>
              </select>
            }
            <button type="submit" className="btn btn-outline-success">검색</button>
          </form>
          }
        </div>
      </div>

      <div className="mt-3">
        <table>
          <thead>
            <tr>
              <th className="text-nowrap"></th>
              <th className="text-nowrap">차종</th>
              <th className="text-nowrap">차량명</th>
              <th className="text-nowrap">등록번호</th>
            </tr>
          </thead>
          <tbody>
            {vehicles?.map((v, i) => {
              return (
                <tr key={i} onClick={() => imgEvent2(v)} style={{cursor: 'pointer'}}>
                    <td><input type="radio" name="vehicleRadio" checked={no === v.no} onChange={()=>{}}/></td>
                    <td>{getType(v.type)}</td>
                    <td>{v.name}</td>
                    <td>{v.regNumber}</td>
                </tr>
              )
            })}
            {vehicles.length == 0 &&
              <tr className="text-center"><td colSpan="4">조회된 차량이 없습니다</td></tr>
            }
          </tbody>
        </table>
      </div>

      {/* 페이징 */}
      {vehicles.length > 0 &&
        <Pagination pagination={pagination} clickEvent={clickEvent} page={page} total={total} />
      }

      <div className="d-flex justify-content-end gap-2 mt-2">
        <button type="button" className="btn btn-outline-success" onClick={imgEvent1} disabled={no === 0}>기사 배정</button>
      </div>
    </>
  )
}

export default Step3