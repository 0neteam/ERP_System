import { useState, useEffect } from 'react'
import { useParams } from "react-router-dom"
import EMPTY from '@assets/item/empty_img.jpg'
import { POST, GET } from '@utils/Network.js'
import Release from '@components/stock/Release.jsx'
import Pagination from '@components/commons/Pagination.jsx'

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
        setReleases(res.result.release.list)
        const arr = []
        for(let i = 0; i < res.result.release.totalPages; i++) {
          const active = page == i
          arr[i] = {page: i+1, active}
        }
        setPagination(arr)
        setTotal(res.result.release.totalPages)
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