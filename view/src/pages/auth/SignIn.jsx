import { useState, useEffect, useRef } from 'react'
import Logo from '@assets/user/empty_user.png'
import Alert from '@components/commons/Alert.jsx'
import { POST } from '@utils/Network.js'
import { useAuth } from '@hooks/AuthProvider.jsx'

const Step1 = ({auth, setAuth, authEvent, offEvent, timerRef, user}) => {
  const interval = 180
  const [counter, setCounter] = useState(interval)
  const [show, isShow] = useState(false)
  useEffect(() => {
    offEvent(counter)
    if(counter > 0) {
      timerRef.current = setInterval(() => setCounter(pre => pre - 0.5), 500)
      return () => clearInterval(timerRef.current)
    }
  }, [counter]);
  const changeEvent = (e) => setAuth(e.target.value)
  const emailEvent = () => {
    isShow(true)
    POST('/oauth/user/email', user).then(
      (res) => {
        if(res.status) {
          setCounter(interval)
        }
        isShow(false)
      },
      (err) => {
        console.error(err)
        isShow(false)
      }
    )
  }
  return (
    <div className="input-group mt-3">
        <div className="form-floating flex-grow-1">
          <input type="text" className="form-control" id="code" name="code" placeholder="인증코드" value={auth} onChange={changeEvent} />
          <label htmlFor="code">인증코드</label>
        </div>
        {counter > 0 ?
          <button type="button" className="btn btn-outline-primary" style={{width: '75px'}} onClick={authEvent}>{Math.floor(counter)}초</button>
          :
          <button type="button" className="btn btn-outline-primary" style={{width: '75px'}} onClick={emailEvent} disabled={show}>재전송</button>
        }
    </div>
  )
}

const SignIn = () => {
  const { checkAccess } = useAuth()
  const [user, setUser] = useState({email: '', type: 0})
  const [auth, setAuth] = useState('')
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
  const authEvent = () => {
    clearTimeout(timerRef2.current)
    timerRef2.current = setTimeout(() => setStep(1), 5000)
    setStep(2)
    if(auth === ''){
      setType(arr[2])
      return
    }
    POST('/oauth/user', {code: auth}).then(
      res => {
        if(res.status) {
          checkAccess({auth: res.status, roles: res.result})
          document.location.href = "/"
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
                <h1 className="display-6 text-nowrap text-center">SignIn</h1>
                <a href="#!">
                    <img className="mx-auto d-block mt-3" style={{width: '20vh'}} src={Logo} alt="logo" />
                </a>
                <form onSubmit={emailEvent}>
                
                <div className="form-floating mt-3">
                    <input type="email" className="form-control" id="email" name="email" placeholder="Email" required value={user.email} onChange={changeEvent}/>
                    <label htmlFor="email">Email</label>
                </div>

                {(step == 0 || step == 4) && 
                <div className="d-flex mt-3">
                    <button name="codeReqButton" type="submit" className="btn btn-outline-primary flex-fill">인증코드 요청</button>
                </div>
                }
                </form>
                {step == 4 && <Alert type={type} />}

                {step >= 1 && step <= 2 && <Step1 auth={auth} setAuth={setAuth} authEvent={authEvent} offEvent={offEvent} timerRef={timerRef1} user={user} />}
                {step == 2 && <Alert type={type} />}
                {step == 3 && <Alert type={type} />}
                <div className="d-flex btn-group mt-3 mb-2">
                    <a type="button" className="btn btn-outline-danger" href="/">취소</a>
                </div>
            </div>
            <div className="col"></div>
        </div>
       
    </section>
  )
}

export default SignIn