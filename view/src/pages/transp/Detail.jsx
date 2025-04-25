import { useState, useEffect } from 'react'
import { useParams } from "react-router-dom"
import DRI from '@assets/transp/driver3.png'
import { POST, PATCH } from '@utils/Network.js'

const Detail = () => {
  const { id } = useParams()
  const [transp, setTransp] = useState({})
  const [releases, setReleases] = useState([])
  const [point, setPoint] = useState(0)
  const baseUrl = import.meta.env.VITE_APP_GATEWAY_URL + '/oauth/file/u/'
  const getFile = fileNo => {
    if(fileNo == null) return DRI
    return baseUrl + fileNo
  }
  const getType = t => {
    if(t === 1) return '소형화물'
    if(t === 2) return '카고'
    if(t === 3) return '탑차'
    if(t === 4) return '윙바디'
    if(t === 5) return '트레일러'
    return ''
  }
  const checkStatus = status => {
    if(status == 0) return 'badge bg-warning'
    if(status == 1) return 'badge bg-success'
    if(status == 2) return 'badge bg-danger'
  }
  const checkStatusName = status => {
    if(status == 0) return '대기'
    if(status == 1) return '정상'
    if(status == 2) return '비정상'
  }
  const pointEvent = () => {
    if(point === 0 || point === 1) return '출발'
    if(point === 2 || point === 3) return '도착'
    if(point === 4) return '완료'
  }
//   const getLicence = licence => {
//     if(licence === 1) return '1종 특수'
//     if(licence === 2) return '1종 대형'
//     if(licence === 3) return '1종 보통'
//     if(licence === 4) return '2종 보통'
//     return ''
//   }
  const transpEvent = () => {
    const params = {point: point + 1}
    console.log(params)
    PATCH(`/trs/transp/${id}`, params).then(res => {
      if(res.status) getData()
    })
  }
  const getData = () => {
    POST(`/trs/transp/${id}`, {}).then(res => {
      console.log(res)
      if(res.status) {
        setTransp(res.result.transp)
        setReleases(res.result.releases.list)

        // if(res.result.transp.arrDate == null && res.result.transp.depDate == null) setPoint(0)
        // else if(res.result.transp.arrDate == null && res.result.transp.depDate != null) setPoint(1)
        // else if(res.result.transp.arrDate != null && res.result.transp.depDate != null) setPoint(2)
        if(res.result.transp.depDate == null && res.result.releases.list[0].depDate == null) setPoint(0) //출발버튼 disabled
        else if(res.result.transp.depDate == null && res.result.releases.list[0].depDate != null) setPoint(1) //출발버튼
        else if(res.result.transp.depDate != null && res.result.releases.list[0].depDate != null && res.result.releases.list[0].arrDate == null) setPoint(2) //도착버튼 disabled
        else if(res.result.releases.list[0].arrDate != null && res.result.releases.list[0].depDate != null && res.result.transp.arrDate == null) setPoint(3) //도착버튼
        else if(res.result.releases.list[0].arrDate != null && res.result.releases.list[0].depDate != null && res.result.transp.arrDate != null) setPoint(4) //완료
        console.log(point)
      }
    })
  }
  useEffect(() => {
    getData()
  }, [])
  return (
    <section className="container" style={{minHeight: '70vh'}} >
      <div className="mb-4">
          <div className="d-flex justify-content-between align-items-center mb-4">
              <h2 className="mb-0">운송 상세</h2>
              <button type="button" className="btn btn-outline-success" onClick={() => document.location.href = '/transp'}>목록</button>
          </div>

          <div className="row row-cols-1 row-cols-md-2 align-items-stretch g-3">
              <div className="col">
                  <div className="card shadow-sm h-100">
                      <div className="card-body">
                          <div className="row align-items-start gx-5">
                              <div className="col-12 col-lg-4 text-center mb-3 mb-lg-0">
                                  <img src={getFile(transp.fileNo)} alt="운송기사 사진" className="img-fluid rounded-circle" style={{maxWidth: '150px'}} />
                              </div>
                              <div className="col-12 col-lg-8 mt-3">
                                  <p>운송번호 : {transp.no}</p>
                                  <p>등록번호 : {transp?.vehicle?.regNumber}</p>
                                  <p>차종 : {getType(Number(transp?.vehicle?.type))}</p>
                                  <p>차량명 : {transp?.vehicle?.name}</p>
                                  <p className="card-text">이메일: {transp?.userEmail}</p>
                                  <h5 className="card-title">운송기사: {transp?.userName}</h5>
                              </div>

                          </div>
                      </div>
                  </div>
              </div>

              <div className="col">
                  <div className="card shadow-sm h-100">
                      <div className="card-body text-center text-lg-start">
                          <div>
                              <button type="button" className="btn btn-outline-success w-100 mb-2" disabled={point === 0 || point === 2 || point === 4} onClick={transpEvent}>{pointEvent()}</button>
                              <p className="text-center mb-3">요청일자: {transp.regDate}</p>
                              {point >= 2 &&
                                <p className="text-center mb-3">출발일자: {transp.depDate}</p>
                              }
                              {point >= 4 &&
                                <p className="text-center mb-3">도착일자: {transp.arrDate}</p>
                              }
                          </div>
                      </div>
                  </div>
              </div>
          </div>
      </div>

      <table>
        <thead>
          <tr>
            <th className="text-nowrap">품목번호</th>
            <th className="text-nowrap">품목명</th>
            <th className="text-nowrap">출고수량</th>
            <th className="text-nowrap">입고수량</th>
            <th className="text-nowrap">부족수량</th>
            <th className="text-nowrap">상태</th>
          </tr>
        </thead>
        <tbody>
          {releases?.map((v,i) => {
            return (
              <tr key={i}>
                  <td>{v.itemNo}</td>
                  <td className="text-truncate">{v.itemName}</td>
                  <td>{v.oqty}</td>
                  <td>{v.iqty}</td>
                  <td>{v.pqty}</td>
                  <td>
                    <span className={checkStatus(v.status)}>{checkStatusName(v.status)}</span>
                  </td>
              </tr>
            )
          })}
          {releases.length === 0 && 
            <tr className='text-center'><td colSpan="6">조회된 품목이 없습니다.</td></tr>
          }
        </tbody>
      </table>

      {/* 페이징 유무 확인 */}

    </section>
  )
}

export default Detail