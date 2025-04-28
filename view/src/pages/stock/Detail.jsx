import { useState, useEffect } from 'react'
import { useParams } from "react-router-dom"
import EMPTY from '@assets/item/empty_img.jpg'
import { POST, GET } from '@utils/Network.js'

const Release = ({releases}) => {

  const linkEvent = no => {
    document.location.href = `/order/${no}`
  }
  
  return (
    <>
    <div className="d-flex mb-2" style={{overflowY: 'auto'}}>
      <table>
        <thead>
          <tr>
            <th style={{minWidth: '90px'}}>입출고번호</th>
            <th style={{minWidth: '50px'}}>수량</th>
            <th style={{minWidth: '140px'}}>출발일시</th>
            <th style={{minWidth: '140px'}}>도착일시</th>
          </tr>
        </thead>
        <tbody>
          {releases?.map((v, i) => {
            console.log(v)
            return (
              <tr style={{cursor: 'pointer'}} key={i} onClick={() => linkEvent(v.orderNo)}>
                <td>{v.no}</td>
                <td>{v.iqty}</td>
                <td>{v.depDate}</td>
                <td>{v.arrDate}</td>
              </tr>
            )
          })}
          {releases.length == 0 && <tr className='text-center fw-bold fs-6'><td colSpan="4">조회된 입출고 내역이 없습니다.</td></tr>}
        </tbody>
      </table>
    </div>
    </>
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

const Detail = () => {
  const [stock, setStock] = useState({})
  const [releases, setReleases] = useState([])
  const [pagination, setPagination] = useState([])
  const [page, setPage] = useState(0)
  const [total, setTotal] = useState(4)
  const { id } = useParams()
  const baseUrl = import.meta.env.VITE_APP_GATEWAY_URL + '/oauth/file/i/'
  const blockStyle = {width: '100px'}
  const getFile = (fileNo) => {
    if(fileNo == null) return EMPTY
    return baseUrl + fileNo
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
  const getData = (params) => {
    POST(`/stg/stock/${id}`, params).then(res => {
      if(res.status) {
        const totalQty = res.result.stock.bundle * res.result.stock.qty
        const stock = res.result.stock
        setStock({...stock, totalQty})
        console.log(res.result.release.list)
        setReleases(res.result.release.list)
        const arr = []
        for(let i = 0; i < res.result.release.totalPages; i++) {
          const active = page == i
          arr[i] = {page: i+1, active}
        }
        setPagination(arr)
        setTotal(res.result.release.totalPages)
        console.log(res.result)
      }
    })
  }
  useEffect(() => {
    getData({page})
  }, [page])
  return (
    <section className="container mb-2 p-3" style={{minHeight: '70vh'}}>
      <div className="p-3">
        <h2>재고 상세</h2>
      </div>
      <div className="d-flex flex-column flex-md-row w-100 justify-content-center align-items-center gap-3 mb-4">
        <div className="d-flex justify-content-center align-items-center">
          <img className="item" src={getFile(stock.fileNo)} />
        </div>
  
        <div className="item-container w-100 w-mb-50">
          <div>
            <div className="input-group mb-2">
              <span className="input-group-text text-nowrap" style={blockStyle}>품목번호</span>
              <input type="text" className="form-control" name="itemNo" defaultValue={stock.itemNo} disabled />
            </div>
            <div className="input-group mb-2">
              <span className="input-group-text text-nowrap" style={blockStyle}>품목명</span>
              <input type="text" className="form-control" name="itemName" defaultValue={stock.itemName} disabled />
            </div>
            <div className="input-group mb-2">
              <span className="input-group-text text-nowrap" style={blockStyle}>생산단위</span>
              <input type="text" className="form-control" name="bundle" defaultValue={stock.bundle} disabled />
            </div>
            {/* <div className="input-group mb-2">
              <span className="input-group-text text-nowrap" style={blockStyle}>재고량</span>
              <input type="text" className="form-control" name="qty" defaultValue={stock.qty} disabled />
            </div> */}
            <div className="input-group mb-2">
              <span className="input-group-text text-nowrap" style={blockStyle}>재고량</span>
              <input type="text" className="form-control" name="totalQty" defaultValue={stock.totalQty} disabled />
            </div>
            <div className="input-group mb-2">
              <span className="input-group-text text-nowrap" style={blockStyle}>등록일자</span>
              <input type="text" className="form-control" name="regDate" defaultValue={stock.regDate} disabled />
            </div>
          </div>
          <div className="btns d-flex w-100 justify-content-end gap-2">
            <a className="btn btn-outline-secondary" href="/stock">돌아가기</a>
          </div>
        </div>
      </div>

      <Release releases={releases} />
      {releases.length > 0 && 
        <Pagination pagination={pagination} clickEvent={clickEvent} page={page} total={total} />
      }

    </section>
  )
}

export default Detail