import React from 'react'

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
    <div className="mt-3">
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

export default Pagination