import React, { useState, useEffect, useRef } from 'react'
import { GET, PUT, POST } from '@utils/Network.js'
import EmptyUser from '@assets/user/empty_user.png'
import Modal from '@components/order/Modal.jsx'
import Pagination from '@components/commons/Pagination.jsx'

const Step5 = ({id, checkData, checkRole}) => {
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
      {showModal && <Modal handleClose={handleClose} transp={transp} checkRole={checkRole} />}
    </>
  )
}

export default Step5