import React, { useState, useEffect, useRef } from 'react'
import { GET, PUT, POST } from '@utils/Network.js'
import EmptyUser from '@assets/user/empty_user.png'
import Pagination from '@components/commons/Pagination.jsx'

const Step4 = ({id, isShow, vehicleNo, licence}) => {
  const [users, setUsers] = useState([])
  const [userNo, setUserNo] = useState(0)
  const [query, setQuery] = useState('')
  const [pagination, setPagination] = useState([])
  const [page, setPage] = useState(0)
  const [total, setTotal] = useState(4)
  const size = 10
  const baseUrl = import.meta.env.VITE_APP_GATEWAY_URL + '/oauth/file/'
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
  const queryEvent = e => {
    setQuery(e.target.value)
  }
  const submitEvent = e => {
    e.preventDefault()
    getData()
  }
  const selectUserEvent = data => {
    const arr = []
    for(const user of users) {
      if(user.no === data.no) {
        setUserNo((user.target) ? 0 : data.no)
        user["target"] = (user.target) ? false : true
      } else user["target"] = false
      arr[arr.length] = user
    }
    setUsers([...arr])
  }
  const getFile = (fileNo) => {
    if(fileNo == null) return EmptyUser
    return baseUrl + fileNo
  }
  const transpEvent = () => {
    PUT(`/trs/order/${id}`, {userNo, vehicleNo}).then(res => {
      if(res.status) isShow(3)
    })
  }
  const getData = () => {
    const params = {type: licence, name: query}
    POST(`/trs/order?size=${size}&page=${page}`, params).then(res => {
      if(res.status) {
        setUsers(res.result.content)
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
    getData()
  }, [])
  return (
    <>
      <h2 className="mt-4 mb-4">기사 배정</h2>
      <div className="d-flex align-items-center mb-3">
        <div className="col-12">
          <form className="form" onSubmit={submitEvent}>
            <div className="input-group">
              <input type="text" className="form-control" placeholder="이름으로 검색" value={query} onChange={queryEvent} />
              <button type="submit" className="btn btn-outline-success">검색</button>
            </div>
          </form>
        </div>
      </div>

      <div className="row g-4 mb-2" style={{minHeight: '10vh'}}>
        {users?.map((v, i) => {
          return (
            <div className="col-6 col-sm-6 col-md-4 col-lg-3 col-xl-2" key={i} onClick={() => selectUserEvent(v)}>
              <div className={v.target ? 'card border border-5 border-success' : 'card border border-5'}>
                <img src={getFile(v.fileNo)} className="card-img-top" />
                <div className="card-body d-flex flex-column">
                  <h5 className="card-title">{v.name}</h5>
                  <p className="card-text card-driver-email text-truncate">{v.email}</p>
                </div>
              </div>
            </div>
          )
        })}
        {users.length == 0 &&
          <div className="text-center">조회된 기사가 없습니다.</div>
        }
      </div>

      <div className="d-flex justify-content-end gap-2 mt-2">
        <button type="button" className="btn btn-outline-secondary" onClick={() => isShow(2)}>이전</button>
        <button type="button" className="btn btn-outline-success" onClick={transpEvent} disabled={userNo === 0}>배차 완료</button>
      </div>

      {/* 페이징 */}
      <Pagination pagination={pagination} clickEvent={clickEvent} page={page} total={total} />
    </>
  )
}

export default Step4