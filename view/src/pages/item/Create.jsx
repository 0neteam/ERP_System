import { useState, useRef } from 'react'
import EMPTY from '@assets/item/empty_img.jpg'
import { PUT } from '@utils/Network.js'

const Create = () => {
  const [item, setItem] = useState({name: '', bundle: '', price: ''})
  const [image, setImage] = useState(EMPTY);
  const [image2, setImage2] = useState(null);
  const fileRef = useRef(null);
  const baseUrl = import.meta.env.VITE_APP_GATEWAY_URL + '/oauth/file/i/'
  const changeEvent = (e) => {
    const {name, value} = e.target
    setItem({...item, [name]: value})
  }
  const submitEvent = (e) => {
    e.preventDefault();
    const form = new FormData();
    form.append('name', item.name);
    form.append('bundle', item.bundle);
    if(item.price !== '') {
      form.append('price', item.price);
    }
    if(fileRef.current.files.length > 0) {
      form.append('file', fileRef.current.files[0])
    }
    PUT('/mfr/item', form).then(res=>{ 
      if(res.status) document.location.href = "/item"
    })
  }
  const fileEvent = () => {
    fileRef.current.click()
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
    if(fileNo == null) return EMPTY
    return baseUrl + fileNo
  }
  return (
    <section className="container d-flex justify-content-center align-items-center" style={{minHeight: '70vh'}}>
        <div className="d-flex flex-column flex-md-row w-100 justify-content-center align-items-center gap-3">
      
          <div>
            <div className="overlayImg">
              <img className="img-thumbnail item" src={image} alt="logo" />
              
              <button className="overlayButton dropdown-toggle addImgDropdown" type="button" id="addImg" data-bs-toggle="dropdown" aria-expanded="false">+</button>
              <ul className="dropdown-menu" aria-labelledby="addImg">
                <li className="dropdown-item" style={{cursor: 'pointer'}} onClick={fileEvent} >edit</li>
                <li className="dropdown-item" style={{cursor: 'pointer'}} onClick={cancelEvent} >cancel</li>
              </ul>
              <input type="file" className="file-input d-none" accept="image/*" ref={fileRef} onChange={imageChange} />
            </div>
          </div>
      
          <form className="item-container w-100" onSubmit={submitEvent} >
            <div>
              <div className="input-group mb-3">
                <span className="input-group-text" style={{width: '100px'}}>품목명</span>
                <input type="text" className="form-control" name="name" placeholder="품목명을 입력하세요." required value={item.name} onChange={changeEvent} />
              </div>
              <div className="input-group mb-3">
                <span className="input-group-text" style={{width: '100px'}}>생산단위</span>
                <input type="number" className="form-control" name="bundle" placeholder="묶음 수량을 입력하세요." required value={item.bundle} onChange={changeEvent} />
              </div>
              <div className="input-group mb-3">
                <span className="input-group-text" style={{width: '100px'}}>생산단가</span>
                <input type="number" className="form-control" name="price" placeholder="단품 가격을 입력하세요." value={item.price} onChange={changeEvent} />
              </div>
            </div>
    
            <div className="btns d-flex w-100 justify-content-end gap-2">
              <button type="submit" className="btn btn-outline-primary">저장</button>
              <a className="btn btn-outline-secondary" href="/item">취소</a>
            </div>
          </form>

        </div>
    </section>
  )
}

export default Create