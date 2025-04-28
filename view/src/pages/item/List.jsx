import { useState, useEffect, useRef } from 'react'
import EMPTY from '@assets/item/empty_img.jpg'
import '@styles/card.css'
import { GET } from '@utils/Network.js'
import Pagination from '@components/commons/Pagination.jsx';

const Items = ({items}) => {
  const baseUrl = import.meta.env.VITE_APP_GATEWAY_URL + '/oauth/file/i/'
  const getFile = (fileNo) => {
    if(fileNo == null) return EMPTY
    return baseUrl + fileNo
  }
  const linkEvent = no => {
    document.location.href = `/item/${no}`
  }
  return (
    <div className="row g-4 mb-2">
    {
      items?.map((v, i) => {
        return (
            <div className="col-6 col-md-4 col-lg-3" key={i} onClick={() => linkEvent(v.no)}>
              <div className="card h-100">
                <img src={getFile(v.fileNo)} className="card-img-top" />
                <div className="card-body d-flex flex-column">
                  <h5 className="card-title mt-2">{v.name}</h5>
                  <ul className="list-group list-group-flush mb-3 flex-grow-1">
                    <li className="list-group-item px-0 py-1"><strong>품목번호:</strong> {v.no}</li>
                  </ul>
                </div>
              </div>
            </div>
        )
      })
    }
    </div>
  )
}


const List = () => {
  const [items, setItems] = useState([])
  const [pagination, setPagination] = useState([])
  const [page, setPage] = useState(0)
  const [total, setTotal] = useState(4)
  const [show, isShow] = useState(false)
  const [type, setType] = useState(0)
  const [no, setNo] = useState('')
  const [name, setName] = useState('')
  const inputRef = useRef(null);
  const getData = (params) => {
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
        isShow(true)
      }
    })
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
  const selectEvent = (e) => {
    setType(Number(e.target.value))
    setNo(0)
    setName('')
    if(Number(e.target.value) === 0) getData({page})
  }
  const submitEvent = (e) => {
    e.preventDefault();
    const params = {page}
    if(type === 1) params.no = no
    if(type === 2) params.name = name
    getData(params)
  }
  useEffect(() => {getData({ page })}, [page])
  return (
    <section className="container" style={{minHeight: '70vh'}}>
      <div>
        <h2 className="mb-4">품목 목록</h2>
      </div>
      <div className="mb-4">
        <form className="form" onSubmit={submitEvent} >
          <div className="row">
            <div className="col-md-4">
              <select className="form-select type-select" onChange={selectEvent} >
                <option value="0">전체</option>
                <option value="1">품목번호</option>
                <option value="2">품목명</option>
              </select>
            </div>
            {type !== 0 &&
            <div className="col-md-8">
              <div className="input-group">
                {type === 1 &&
                <input type="input" className="form-control" name="no" onChange={e => setNo(e.target.value)}/>
                }
                {type === 2 &&
                <input type="input" className="form-control" name="name" onChange={e => setName(e.target.value)} />
                }
                <button type="submit" className="btn btn-outline-success">검색</button>
              </div>
            </div>
            }
          </div>
        </form>
      </div>
      {show && (
        items.length > 0
          ? (
            <>
              <Items items={items} />
              <Pagination
                pagination={pagination}
                clickEvent={clickEvent}
                page={page}
                total={total}
              />
            </>
          )
          : (
            <div className="text-center py-5">
              <p className="mb-0">검색 결과가 없습니다.</p>
            </div>
          )
      )}
  </section>
  )
}

export default List