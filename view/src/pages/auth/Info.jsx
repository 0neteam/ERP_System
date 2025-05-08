import { useState, useEffect, useRef } from 'react'
import { GET, PATCH } from '@utils/Network.js'
import Logo from '@assets/user/empty_user.png'
import '@styles/img.css'
import { useAuth } from '@hooks/AuthProvider.jsx'

const Info = () => {
  const { styles } = useAuth()
  const [depts, setDepts] = useState('')
  const [user, setUser] = useState({no: 0, email: '', name: ''})
  const [image, setImage] = useState(null);
  const [image2, setImage2] = useState(null);
  const [licences, setLicences] = useState(null)
  const [dri, isDri] = useState(false)
  const fileRef = useRef(null);
  const [show, setShow] = useState(false)
  const baseUrl = import.meta.env.VITE_APP_GATEWAY_URL + '/oauth/file/u/'
  const changeDept = () => {
    setDepts(user?.depts?.map(d => d.deptName).join(', '))
  }
  const changeLicence = (data) => {
    const arr = []
    if(data.licence1 === 'Y') arr[arr.length] = '1종 특수'
    if(data.licence2 === 'Y') arr[arr.length] = '1종 대형'
    if(data.licence3 === 'Y') arr[arr.length] = '1종 보통'
    if(data.licence4 === 'Y') arr[arr.length] = '2종 보통'
    setLicences(arr?.map(l => l).join(', '))
  }
  const licenceEvent = i => {
    const name = `licence${i}`
    const value = user[name] === 'Y' ? 'N' : 'Y'
    setUser({...user, [name]: value})
    changeLicence({...user, [name]: value})
  }
  const checkLicence = licence => (licence === 'Y') ? 'btn btn-outline-primary w-100 text-nowrap active' : 'btn btn-outline-primary w-100 text-nowrap'
  const getData = () => {
    GET('/oauth/user', {}).then(
      res => {
        if(res.status) {
          setUser(res.result)
          setDepts(res.result?.depts?.map(d => d.deptName).join(', '))
          setImage(getFile(res.result.fileNo))
          setImage2(getFile(res.result.fileNo))
          for(let dept of res.result.depts) {
            if(dept.no === 5) {
              isDri(true)
              changeLicence(res.result)
              break
            }
          }
        }
      }
    )
  }
  const changeEvent = (e) => {
    const {name, value} = e.target
    setUser({...user, [name]: value})
  }
  const editEvent = () => {
    if(show) {
      const form = new FormData();
      form.append('licence1', user.licence1)
      form.append('licence2', user.licence2)
      form.append('licence3', user.licence3)
      form.append('licence4', user.licence4)
      if(fileRef.current.files.length > 0) {
        form.append('file', fileRef.current.files[0])
      }
      PATCH(`/oauth/user/${user.no}`, form).then(res=>{
        if(!res.status) {
          fileRef.current.value = ''
          getData()
        }
      })
    }
    setShow(!show)
  }
  const fileEvent = () => {
    if(show) fileRef.current.click()
  }
  const imageChange = () => {
    const file = fileRef.current.files[0];
    if (file && file.type.startsWith('image/')) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setImage2(reader.result)
        setImage(reader.result)
      };
      reader.readAsDataURL(file);
    } else {
      setImage((image2 == null) ? getFile(user.fileNo) : image2)
    }
  }
  const cancelEvent = () => {
    fileRef.current.value = ''
    setImage(getFile(user.fileNo))
  }
  const getFile = (fileNo) => {
    if(fileNo == null) return Logo
    return baseUrl + fileNo
  }
  useEffect(() => {
    getData()
  }, [])
  return (
    <section className="container" style={styles}>
        <div className="row">
            <div className="col"></div>
            <div className="col-8 authinfo">
                {/* <h1 className="display-6 text-nowrap text-center">mypage</h1> */}

                <div className="mb-2 text-center">
                  <div className="d-flex justify-content-center overlayImg">
                    <img className="d-block user rounded-circle img-thumbnail mt-3" src={image} alt="logo" />
                    
                    {show &&
                    <button className="d-block overlayButton dropdown-toggle addImgDropdown" type="button" id="addImg" data-bs-toggle="dropdown" aria-expanded="false">+</button>
                    }
                    <ul className="dropdown-menu" aria-labelledby="addImg">
                      <li className="dropdown-item" style={{cursor: 'pointer'}} onClick={fileEvent} >edit</li>
                      <li className="dropdown-item" style={{cursor: 'pointer'}} onClick={cancelEvent} >cancel</li>
                    </ul>
                    <input type="file" className="file-input d-none" accept="image/*" ref={fileRef} onChange={imageChange}/>
                  </div>
                </div>

                <div className="input-group mt-3">
                    <span className="input-group-text d-flex justify-content-center text-nowrap" style={{width: '60px'}}>사번</span>
                    <input type="text" className="form-control" id="no" name="no" value={user.no} readOnly onChange={changeEvent} />
                </div>
                <div className="input-group mt-3">
                    <span className="input-group-text d-flex justify-content-center text-nowrap" style={{width: '60px'}}>Email</span>
                    <input type="text" className="form-control" id="email" name="email" value={user.email} readOnly onChange={changeEvent} />
                </div>

                <div className="input-group mt-3">
                    <span className="input-group-text d-flex justify-content-center text-nowrap" style={{width: '60px'}}>이름</span>
                    <input type="text" className="form-control" id="name" name="name" value={user.name} readOnly onChange={changeEvent} />
                </div>

                <div className="input-group mt-3">
                    <span className="input-group-text d-flex justify-content-center text-nowrap" style={{width: '60px'}}>부서</span>
                    <input type="text" className="form-control" placeholder="" disabled value={depts} onChange={changeDept} />
                </div>

                {dri && <>
                <div className="input-group mt-3">
                    <span className="input-group-text d-flex justify-content-center text-nowrap" style={{width: '60px'}}>면허</span>
                    <input type="text" className="form-control" placeholder="" disabled value={licences} onChange={changeLicence}/>
                </div>
                
                {show &&
                <div className="row row-cols-2 g-2 mt-1">
                  <div className="col">
                    <button className={checkLicence(user.licence1)} onClick={() => licenceEvent(1)}>1종 특수</button>
                  </div>
                  <div className="col">
                    <button className={checkLicence(user.licence2)} onClick={() => licenceEvent(2)}>1종 대형</button>
                  </div>
                  <div className="col">
                    <button className={checkLicence(user.licence3)} onClick={() => licenceEvent(3)}>1종 보통</button>
                  </div>
                  <div className="col">
                    <button className={checkLicence(user.licence4)} onClick={() => licenceEvent(4)}>2종 보통</button>
                  </div>
                </div>
                }
                </>}

                <div className="d-flex btn-group mt-4 mb-2">
                    <button type="button" className="btn btn-outline-primary w-50" onClick={editEvent}>{show ? '저장' : '수정'}</button>
                    <a type="button" className="btn btn-outline-danger w-50" href="/">취소</a>
                </div>
            </div>
            <div className="col"></div>
        </div>
    </section>
  )
}

export default Info