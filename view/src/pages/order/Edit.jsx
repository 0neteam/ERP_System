import { useState, useEffect } from 'react'
import { useParams } from "react-router-dom"
import { GET, POST, PUT, PATCH } from '@utils/Network.js'

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

const Edit = () => {
  const { id } = useParams()
  const [orders, setOrders] = useState([])
  const [allChecked, setAllChecked] = useState(false)
  const [checkedItems, setCheckedItems] = useState([])
  const [showModal, setShowModal] = useState(false)
  const handleOpen = () => setShowModal(true)
  const handleClose = () => setShowModal(false)
  const addOrderEvent = order => {
    const c = orders.filter(o => o.itemNo === order.no)
    if(c.length === 0) {
      const item = {
        itemNo: order.no,
        itemName: order.name,
        bundle: order.bundle,
        price: order.price,
        qty: 0
      }
      setOrders([...orders, item].sort((a, b) => b.itemNo - a.itemNo))
    }
  }
  const deleteEvent = () => {
    checkedItems?.map(no => setOrders(prev => prev.filter(order => order.itemNo !== no)))
    setCheckedItems([])
    setAllChecked(false)
  }
  const orderEvent = () => {
    PATCH(`/stg/order/${id}`, {items: orders, status: 1}).then(res => {
      if(res.status) document.location.href = `/order/${id}`
    })
  }
  const isChecked = no => checkedItems.includes(no)
  const handleRowClick = (no) => {
    setCheckedItems(prev => (prev.includes(no)) 
      ? prev.filter(itemNo => itemNo !== no)
      : [...prev, no]
    )
  }
  const changeEvent = (e, data) => {
    setOrders(prev => prev.filter(order => {
      if(order.itemNo === data.itemNo) {
        order.qty = e.target.value
      }
      return true
    }))
  }
  const allCheckedEvent = checked => {
    setAllChecked(checked)
    setCheckedItems([])
    if(checked) {
      const arr = []
      orders?.map(item => arr[arr.length] = item.itemNo)
      setCheckedItems([...arr])
    }
  }
  const getData = () => {
    POST(`/stg/order/edit/${id}`, {}).then(res => {
      console.log(res)
      if(res.status) {
        setOrders(res.result)
      }
    })
  }
  useEffect(() => {
    getData()
  }, [])
  return (
    <>
      <section className="container">
        <h2 className="mt-4 mb-4">발주 수정</h2>
        <div className="d-flex justify-content-between mb-2">
          <div>
            <button type="button" className="btn btn-outline-success" onClick={handleOpen}>품목선택</button>
          </div>
          <div>
            <button type="button" className="btn btn-outline-secondary" onClick={deleteEvent}>선택삭제</button>
          </div>
        </div>
        <div className="overflow-y-auto">
          <table>
            <thead>
                <tr className="text-center">
                  <th className="text-nowrap"><input type="checkbox" checked={allChecked} onChange={() => allCheckedEvent(!allChecked)} /></th>
                  <th className="text-nowrap">품목번호</th>
                  <th className="text-nowrap">품목명</th>
                  <th className="text-nowrap">단위수량</th>
                  <th className="text-nowrap">출고단위신청</th>
                </tr>
            </thead>
            <tbody>
              {orders?.map((v, i) => {
                return (
                  <tr key={i}>
                    <td><input type="checkbox" checked={isChecked(v.itemNo)} onChange={() => handleRowClick(v.itemNo)} /></td>
                    <td>{v.itemNo}</td>
                    <td>{v.itemName}</td>
                    <td>{v.bundle}</td>
                    <td><input type="number" className="w-100" placeholder='0' name="qty" value={v.qty} onChange={(e) => changeEvent(e, v)}/></td>
                  </tr>
                )
              })}
              {orders.length == 0 && <tr className="text-center fs-6"><td colSpan="5">품목을 추가하세요.</td></tr>}
            </tbody>
          </table>
        </div>
        <div className="d-flex justify-content-end gap-2 mt-4 mb-4">
          <button type="button" className="btn btn-outline-success" onClick={orderEvent}>발주 저장</button>
          <a type="button" className="btn btn-outline-secondary" href={`/order/${id}`}>취소</a>
        </div>
      </section>
      {showModal && <ItemModal handleClose={handleClose} addOrderEvent={addOrderEvent} orders={orders} />}
    </>
  )
}

export default Edit