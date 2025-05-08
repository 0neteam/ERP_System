import React from 'react'
import EMPTY from '@assets/item/empty_img.jpg'

const Items = ({items}) => {
  const baseUrl = import.meta.env.VITE_APP_GATEWAY_URL + '/oauth/file/i/'
  const getFile = (fileNo) => {
    if(fileNo == null) return EMPTY
    return baseUrl + fileNo
  }
  const linkEvent = no => {
    document.location.href = `/stock/${no}`
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
                  <h5 className="card-title mt-2 text-truncate">{v.itemName}</h5>
                  <ul className="list-group list-group-flush mb-3 flex-grow-1">
                    <li className="list-group-item px-0 py-1"><strong>품목번호:</strong> {v.itemNo}</li>
                    <li className="list-group-item px-0 py-1"><strong>재고량:</strong> {v.bundle * v.qty}</li>
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

export default Items