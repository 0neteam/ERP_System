import { useState, useEffect, useRef } from 'react'
import EMPTY from '@assets/item/empty_img.jpg'
import '@styles/card.css'
import { GET } from '@utils/Network.js'

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
    <div>
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
              <li className="page-item" key={i} ><button className={v.active ? 'page-link active' : 'page-link'}  onClick={()=> clickEvent(i)}>{v.page}</button></li>
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
  )
}

const List = () => {
  const [items, setItems] = useState([])
  const [pagination, setPagination] = useState([])
  const [page, setPage] = useState(0)
  const [total, setTotal] = useState(4)
  const [show, isShow] = useState(false)
  const selectRef = useRef(null);
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
  const selectEvent = () => {
    inputRef.current.value = ''
  }
  const submitEvent = (e) => {
    e.preventDefault();
    const params = { page }
    if(selectRef.current.value == 1) params.no = inputRef.current.value
    if(selectRef.current.value == 2) params.name = inputRef.current.value
    getData(params)
  }
  useEffect(() => {getData({ page })}, [page])
  return (
    <section className="container" style={{minHeight: '70vh'}}>
      <div>
        <h2 className="mb-4">품목 관리</h2>
      </div>
      <div className="mb-4">
        <form className="form" onSubmit={submitEvent} >
          <div className="row">
            <div className="col-md-4">
              <select className="form-select type-select" ref={selectRef} onChange={selectEvent} >
                <option value="0">전체</option>
                <option value="1">품목번호</option>
                <option value="2">품목명</option>
              </select>
            </div>
            <div className="col-md-8">
              <div className="input-group">
                <input type="input" className="form-control" ref={inputRef} />
                <button type="submit" className="btn btn-outline-success">검색</button>
              </div>
            </div>
          </div>
        </form>
      </div>
      {show && 
      <>
        <Items items={items} />
        <Pagination pagination={pagination} clickEvent={clickEvent} page={page} total={total} />
      </>
      }
  </section>
  )
}

export default List