import { useState, useEffect, useRef } from 'react'
import { useParams } from "react-router-dom"
import { GET, POST, PUT, DELETE, PATCH } from '@utils/Network.js'
import EmptyUser from '@assets/user/empty_user.png'

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
    <div className="d-flex justify-content-center mt-3">
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

const Step1 = ({id, items, status, show, outEvent}) => {
  const [orderItems, setOrderItems] = useState([])
  const [pagination, setPagination] = useState([])
  const [page, setPage] = useState(0)
  const [total, setTotal] = useState(4)
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
  const itemEvent = data => {
    console.log(data)
    if(show === 1) {
      if(data.qty == data.iqty) return
      if((data.qty - data.oqty) + data.pqty == 0) return
      if((data.iqty === 0 && data.pqty === 0) && data.qty <= data.oqty) return
      if((data.oqty - data.pqty) == data.iqty ? data.qty == data.iqty : false) return
      outEvent(data)
    }
  }
  const deleteEvent = () => {
    DELETE(`/stg/order/${id}`).then(res => {
      if(res.status) document.location.href = '/order'
    })
  }
  useEffect(() => {
    setOrderItems(items.content)
    const arr = []
    for(let i = 0; i < items.totalPages; i++) {
      const active = page == i
      arr[i] = {page: i+1, active}
    }
    setPagination(arr)
    setTotal(items.totalPages)
  }, [items])
  return (
    <>
      <div className="overflow-y-auto">
        <table>
          <thead>
            <tr>
              <th className="text-nowrap">품목코드</th>
              <th className="text-nowrap">단위수량</th>
              <th className="text-nowrap">발주량</th>
              <th className="text-nowrap">출고량</th>
              <th className="text-nowrap">입고량</th>
              <th className="text-nowrap">불량</th>
            </tr>
          </thead>
          <tbody>
            {orderItems?.map((v, i) => {
              return (
                <tr style={{cursor: show === 1 ? 'pointer' : ''}} key={i} onClick={() => itemEvent(v)}>
                  <td>{v.itemNo}</td>
                  <td>{v.bundle}</td>
                  <td>{v.qty}</td>
                  <td>{v.oqty}</td>
                  <td>{v.iqty}</td>
                  <td>{v.pqty}</td>
                </tr>
              )
            })}

          </tbody>
        </table>
      </div>

      {status == 1 &&
      <div className="d-flex justify-content-end gap-2 mt-2">
        <a className="btn btn-outline-success" href={`/order/edit/${id}`}>발주수정</a>
        <button type="button" className="btn btn-outline-secondary" onClick={deleteEvent}>발주취소</button>
      </div>
      }
      {/* 페이징 자리 */}
      <Pagination pagination={pagination} clickEvent={clickEvent} page={page} total={total} />
    </>
  )
}

const Step2 = ({id, outItems, setOutItems, setStatus}) => {
  const [allChecked, setAllChecked] = useState(false)
  const [checkedItems, setCheckedItems] = useState([])
  const isChecked = no => checkedItems.includes(no)
  const handleRowClick = (no) => {
    setCheckedItems(prev => (prev.includes(no)) 
      ? prev.filter(itemNo => itemNo !== no)
      : [...prev, no]
    )
  }
  const changeEvent = (e, data) => {
    setOutItems(prev => prev.filter(item => {
      if(item.itemNo === data.itemNo) {
        item.oqty = e.target.value
      }
      return true
    }))
  }
  const allCheckedEvent = checked => {
    setAllChecked(checked)
    setCheckedItems([])
    if(checked) {
      const arr = []
      outItems?.map(item => arr[arr.length] = item.itemNo)
      setCheckedItems([...arr])
    }
  }
  const deleteEvent = () => {
    checkedItems?.map(no => setOutItems(prev => prev.filter(item => item.itemNo !== no)))
    setCheckedItems([])
    setAllChecked(false)
  }
  const outEvent = () => {
    PUT(`/mfr/release/${id}`, {releases: outItems}).then(res => {
      if(res.status) document.location.reload()
    })
  }
  return (
    <>
      <h2 className="mt-4 mb-4">출고 요청</h2>
      
      <div className="overflow-y-auto mt-1">
        <table>
          <thead>
            <tr>
              <th className="text-nowrap"><input type="checkbox" checked={allChecked} onChange={() => allCheckedEvent(!allChecked)} /></th>
              <th className="text-nowrap">품목코드</th>
              <th className="text-nowrap">단위수량</th>
              <th className="text-nowrap">출고량</th>
            </tr>
          </thead>
          <tbody>
            {outItems?.map((v, i) => {
              return (
                <tr key={i}>
                    <td><input type="checkbox" checked={isChecked(v.itemNo)} onChange={() => handleRowClick(v.itemNo)} /></td>
                    <td>{v.itemNo}</td>
                    <td>{v.qty}</td>
                    <td><input type="number" className="w-100" placeholder={v.maxQty} max={v.maxQty} min={1} name="oqty" value={v.oqty} onChange={(e) => changeEvent(e, v)} /></td>
                </tr>
              )
            })}
            {outItems.length === 0 &&
            <tr className='text-center'>
              <td colSpan="5">발주 품목을 선택하세요.</td>
            </tr>
            }
          </tbody>
        </table>
      </div>
      <div className='d-flex justify-content-end gap-2'>
        <button type="button" className="btn btn-outline-success mt-2" onClick={deleteEvent} disabled={checkedItems.length === 0}>품목삭제</button>
        <button type="button" className="btn btn-outline-success mt-2" onClick={outEvent} disabled={(checkedItems.length > 0) || (outItems.length === 0)}>출고요청</button>
      </div>
    </>
  )
}

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
      if(res.status) isShow(4)
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

const Step5 = ({id, checkData}) => {
  const [releases, setResleases] = useState([])
  const [pagination, setPagination] = useState([])
  const [page, setPage] = useState(0)
  const [total, setTotal] = useState(4)
  const [showModal, setShowModal] = useState(false)
  const [transp, setTransp] = useState({})
  const handleClose = () => {
    setShowModal(false)
    getData()
    checkData()
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
  const rowClickEvent = data => {
    setTransp(data)
    setShowModal(true)
  }
  const getData = () => {
    POST(`/mfr/release/${id}`, {}).then(res => {
      if(res.status) {
        setResleases(res.result.content)
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
      <h2 className="mt-4 mb-4">입출고 관리</h2>
      <div className="overflow-y-auto ">
        <table>
          <thead>
            <tr>
              <th className="text-nowrap">운송번호</th>
              <th className="text-nowrap">출고일자</th>
              <th className="text-nowrap">입고일자</th>
            </tr>
          </thead>
          <tbody>
            {releases?.map((v, i) => {
              return (
                <tr style={{cursor: 'pointer'}} key={i} onClick={() => rowClickEvent(v)}>
                    <td>{v.transpNo}</td>
                    <td>{v.depDate}</td>
                    <td>{v.arrDate}</td>
                </tr>
              )
            })}
          </tbody>
        </table>
      </div>
      {/* 페이징 자리 */}
      <Pagination pagination={pagination} clickEvent={clickEvent} page={page} total={total} />
      {showModal && <Modal handleClose={handleClose} transp={transp} />}
    </>
  )
}

const Modal = ({handleClose, transp}) => {
  console.log(transp)
  const searchRef = useRef(null)
  const [releaseItems, setReleaseItems] = useState([])
  const [driInfo, setDriInfo] = useState([])
  const [point, setPoint] = useState(0)
  const closeEvent = () => {
    searchRef.current.value = ''
    handleClose()
  }
  const getData = () => {
    GET(`/mfr/release/${transp.transpNo}`, {}).then(res => {
      if(res.status) {
        setDriInfo(res.result[0])
        setReleaseItems(res.result[1].list)
      }
    })
  }
  const changeEvent = (e, data) => {
    setReleaseItems(prev => prev.filter(item => {
      if(item.itemNo === data.itemNo) {
        item.iqty = Number(e.target.value)
        item.edit = true
      }
      return true
    }))
  }
  const submitEvent = (e) => {
    e.preventDefault()
    setReleaseItems(prev => prev.filter(item => {
      if(item.itemNo === Number(e.target.itemNo.value)) item.search = true
      else if(e.target.itemNo.value === '') item.search = true
      else item.search = false
      return true
    }))
  }
  const checkEvent = (data) => {
    if(data.search === false) return false
    else return true
  }
  const itemsEvent = () => {
    PATCH(`/mfr/release/${transp.transpNo}`, {releases: releaseItems, point: point, orderNo: transp.orderNo}).then(res => {
      if(res.status) closeEvent()
    })
  }
  useEffect(() => {
    if(point !== 0) itemsEvent()
  },[point])
  useEffect(() => {
    getData()
  }, [])
  return (
    <div className="modal show d-block" id="IOModal">
        <div className="modal-dialog modal-xl modal-dialog-scrollable">
            <div className="modal-content">
                <div className="modal-header">
                    <h3>입출고 관리</h3>
                </div>
                <div className="modal-body">
                    <div className="container">
                        <div className="d-flex flex-column flex-md-row justify-content-between align-items-center gap-2 mb-2"> 
                            <div className="d-flex gap-2 order-md-2 justify-content-center"> 
                                <button type="button" className="btn btn-outline-success" onClick={() => setPoint(1)} disabled={transp.depDate}>출고 완료</button>
                                <button type="button" className="btn btn-outline-success" onClick={() => setPoint(2)} disabled={transp.depDate == null || (transp.depDate != null && driInfo.depDate == null) || transp.arrDate}>입고 완료</button>
                                <button type="button" className="btn btn-outline-secondary" onClick={closeEvent}>닫기</button>
                            </div>
                            <div className="d-flex align-items-center order-md-1 text-center"> 
                                <p className="mb-0">발주번호 : {transp.orderNo}</p>
                            </div>
                        </div>
                        <div className="row g-2 mb-2">
                            <div className="col-12 col-md-6">
                                <div className="input-group">
                                    <span className="input-group-text" style={{width: '95px'}}>운송자이름</span>
                                    <input type="text" className="form-control" defaultValue={driInfo.userName} readOnly />
                                </div>
                            </div>
                            <div className="col-12 col-md-6">
                                <div className="input-group">
                                    <span className="input-group-text" style={{width: '95px'}}>연락처</span>
                                    <input type="text" className="form-control" defaultValue={driInfo.userEmail} readOnly />
                                </div>
                            </div>
                        </div>
                        <div className="row g-2">
                            <div className="col-12 col-md-6">
                                <div className="input-group">
                                    <span className="input-group-text" style={{width: '95px'}}>출발일</span>
                                    <input type="text" className="form-control" defaultValue={driInfo.depDate} readOnly />
                                </div>
                            </div>
                            <div className="col-12 col-md-6">
                                <div className="input-group">
                                    <span className="input-group-text" style={{width: '95px'}}>도착일</span>
                                    <input type="text" className="form-control" defaultValue={driInfo.arrDate} readOnly />
                                </div>
                            </div>
                        </div>
                        <form className="input-group mt-2 mb-2" onSubmit={submitEvent}>
                            <input type="number" className="form-control" name='itemNo' ref={searchRef} />
                            <button type="submit" className="btn btn-outline-success">검색</button>
                        </form>
                        <div className="overflow-y-auto tableHeight">
                            <table className="mb-2">
                                <thead style={{position: 'sticky', top: '0', zIndex: '1'}}>
                                    <tr>
                                        <th className="text-nowrap">품목코드</th>
                                        <th className="text-nowrap">출고수량</th>
                                        <th className={(transp.arrDate === null && transp.depDate === null) ? 'd-none' : 'd-block text-nowrap'}>입고량</th>
                                    </tr>
                                </thead>
                                <tbody>
                                  {releaseItems?.map((v, i) => {
                                    if(v.iqty === 0 && v.edit === undefined) v.iqty = v.oqty
                                    return checkEvent(v) && 
                                     (
                                      <tr key={i}>
                                        <td>{v.itemNo}</td>
                                        <td>{v.oqty}</td>
                                        <td className={(transp.arrDate === null && transp.depDate === null) ? 'd-none' : 'd-block'}><input type="number" style={{width: '100%'}} value={v.iqty} onChange={(e) => changeEvent(e, v)} max={v.oqty} min={0} disabled={transp.arrDate != null}/></td>
                                      </tr>
                                    )
                                  })}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
  )
}

const Detail = () => {
  const { id } = useParams()
  const [order, setOrder] = useState({})
  const [items, setItems] = useState([])
  const [outItems, setOutItems] = useState([])
  const [vehicleNo, setVehicleNo] = useState(0)
  const [licence, setLicence] = useState(0)
  const [status, setStatus] = useState(0)
  const [show, isShow] = useState(0)
  const changeStatus = s => {
    if(s === 1) return '발주요청'
    if(s === 2) return '발주진행'
    if(s === 3) return '발주완료'
    if(s === 4) return '발주취소'
  }
  const outEvent = data => {
    const c = outItems.filter(o => o.itemNo === data.itemNo)
    if(c.length === 0) {
      const item = {
        orderItemNo: data.no,
        itemNo: data.itemNo,
        name: data.name,
        qty: data.qty,
        maxQty: (data.qty - data.iqty),
        oqty: (data.qty - data.iqty)
      }
      setOutItems([...outItems, item].sort((a, b) => a.itemNo - b.itemNo))
    }
  }
  const checkData = () => {
    getData()
  }
  const getData = () => {
    POST(`/stg/order/${id}`, {}).then(res => {
      if(res.status) {
        setOrder(res.result.order)
        setStatus(res.result.order.status)
        setItems(res.result.items)
      }
    })
  }
  useState(() => {
    getData()
  }, [])
  return (
    <section className="container">
      <div>
        <h2 className="mt-4 mb-4">발주 상세</h2>
        <h4><span className="badge bg-warning">{changeStatus(order.status)}</span></h4>
        <div className="d-flex align-items-center">
          <p>발주번호 : {order.no}</p>
          <p className="ms-auto">발주요청일 : {order.reqDate}</p>
        </div>
      </div>

      {/* STEP1 : 발주 품목 화면 */}
      <Step1 items={items} status={status} id={id} show={show} outEvent={outEvent} />

      <div className={status === 4 ? 'd-flex justify-content-end gap-2 mt-4 mb-2 p-1' : 'd-flex justify-content-center gap-2 mt-4 mb-2 p-1'}>
        {status < 3 &&
        <button type="button" className="btn btn-outline-success w-100" onClick={() => isShow(1)}>출고요청</button>
        }
        {status == 2 &&
        <button type="button" className="btn btn-outline-success w-100" onClick={() => isShow(2)}>운송관리</button>
        }
        {(status > 1 && status < 4) &&
        <button type="button" className="btn btn-outline-success w-100" onClick={() => isShow(4)}>입출고관리</button>
        }
        <button type="button" className="btn btn-outline-success" style={{width: status === 4 ? 'auto' : '100%'}} onClick={() => document.location.href = '/order'}>발주목록</button>
      </div>

      {/* STEP2 : 출고요청 화면 */}
      {status < 4 && show == 1 && <Step2 id={id} outItems={outItems} setOutItems={setOutItems} setStatus={setStatus} />}
      {/* STEP3 : 운송관리 (차량 선택) */}
      {status < 4 && show == 2 && <Step3 id={id} isShow={isShow} setVehicleNo={setVehicleNo} setLicence={setLicence} />}
      {/* STEP4 : 운송관리 (기사 선택) */}
      {status < 4 && show == 3 && <Step4 id={id} isShow={isShow} vehicleNo={vehicleNo} licence={licence} />}
      {/* STEP5 : 입출고관리 화면 */}
      {status < 4 && show == 4 && <Step5 id={id} checkData={checkData}/>}
    </section>
      
  )
}

export default Detail