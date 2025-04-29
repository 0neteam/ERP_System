import { useState, useEffect, useRef } from 'react'
import Logo from '@assets/user/empty_user.png'
import Alert from '@components/commons/Alert.jsx'
import { POST, PUT } from '@utils/Network.js'
import SignUpStep1 from '@components/auth/SignUpStep1.jsx'

const SignUp = () => {
  const [user, setUser] = useState({email: '', name: '', type: 1})
  const [auth, setAuth] = useState('')
  const [show, isShow] = useState(true)
  const [type, setType] = useState({state: true, msg : '인증코드 확인 완료'})
  const [step, setStep] = useState(0)
  const timerRef1 = useRef(null);
  const timerRef2 = useRef(null);
  const timerRef3 = useRef(null);
  const arr = [{state: true, msg : '인증코드 확인 완료'}, {state: false, msg : '다시 인증 해주세요.'}, {state: false, msg : '인증코드를 입력해주세요.'}, {state: false, msg : '서버 문제로 정상 처리 되지 않았습니다.'}]
  const changeEvent = (e) => {
    const {name, value} = e.target
    setUser({...user, [name]: value})
  }
  const emailEvent = (e) => {
    e.preventDefault();
    setStep(0)
    clearTimeout(timerRef3.current)
    POST('/oauth/user/email', user).then(
      (res) => {
        if(res.status) {
          setStep(1)
        } else {
          if(res.message != "") {
            setType({state: res.status, msg : res.message})
            setStep(4)
            timerRef3.current = setTimeout(() => setStep(0), 5000)
          }
        }
      }
    )
  }
  const signUpEvent = () => {
    PUT('/oauth/user', user).then(
      res => {
        if(res.status) {
          document.location.href = "/signIn"
        } else {
          setType(arr[3])
        }
      }
    )
  }
  const authEvent = () => {
    clearTimeout(timerRef2.current)
    timerRef2.current = setTimeout(() => setStep(1), 5000)
    setStep(2)
    if(auth === ''){
      setType(arr[2])
      return
    }
    POST('/oauth/user/auth', {code: auth}).then(
      res => {
        if(res.status) {
          setStep(3)
          setType(arr[0])
          isShow(false)
          clearInterval(timerRef1.current)
          clearInterval(timerRef2.current)
        } else {
          setType(arr[1])
        }
      }
    )
    
  }
  const offEvent = (v) => {
    if(v === 0) {
      clearTimeout(timerRef2.current)
      setStep(1)
    }
  }
  return (
    <section className="container" style={{minHeight: '50vh'}}>
        <div className="row">
            <div className="col"></div>
            <div className="col-6" style={{minWidth: '400px'}}>
                <h1 className="display-6 text-nowrap text-center">SignUp</h1>
                <a href="#!">
                    <img className="mx-auto d-block mt-3" style={{width: '20vh'}} src={Logo} alt="logo" />
                </a>
                <form onSubmit={emailEvent}>
                <div className="form-floating mt-3">
                  <input type="text" className="form-control" id="name" name="name" placeholder="Name" required value={user.name} onChange={changeEvent} />
                  <label htmlFor="name">Name</label>
                </div>
                
                <div className="form-floating mt-3">
                    <input type="email" className="form-control" id="email" name="email" placeholder="Email" required value={user.email} onChange={changeEvent}/>
                    <label htmlFor="email">Email</label>
                </div>

                {(step == 0 || step == 4) && 
                <div className="d-flex mt-3">
                    <button name="codeReqButton" type="submit" className="btn btn-outline-primary flex-fill">Email 인증코드 전송</button>
                </div>
                }
                </form>
                {step == 4 && <Alert type={type} />}

                {step >= 1 && step <= 2 && <SignUpStep1 auth={auth} setAuth={setAuth} authEvent={authEvent} offEvent={offEvent} timerRef={timerRef1} user={user} />}
                {step == 2 && <Alert type={type} />}
                {step == 3 && <Alert type={type} />}
                <div className="d-flex btn-group mt-3 mb-2">
                    <button type="button" className="btn btn-outline-primary" disabled={show} onClick={signUpEvent}>가입</button>
                    <a type="button" className="btn btn-outline-danger" href="/">취소</a>
                </div>
            </div>
            <div className="col"></div>
        </div>
       
    </section>
  )
}

export default SignUp