import React from 'react'

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

export default Release