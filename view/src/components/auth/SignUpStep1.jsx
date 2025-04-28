import React, { useState, useEffect } from 'react'
import { POST } from '@utils/Network.js'

const SignUpStep1 = ({auth, setAuth, authEvent, offEvent, timerRef, user}) => {
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

export default SignUpStep1