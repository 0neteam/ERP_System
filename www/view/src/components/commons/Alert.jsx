const Alert = ({type}) => {
  let div = `alert d-block ${type.state ? 'alert-info ' : 'alert-danger'}`
  return (
    <>
      <div className="mt-1">
          <div className={div}>
            {type.msg}
          </div>
      </div>
    </>
  )
}

export default Alert