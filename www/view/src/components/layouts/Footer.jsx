import Logo  from '@assets/logo_white.png'

const Footer = () => {
  return (
    <footer className="custom-footer">
        <div className="container">
            <div className="row d-flex justify-content-between flex-nowrap" style={{minHeight: '150px'}}>
                <div className="col-6 col-sm-8 d-flex flex-column">
                    <h5>ERP_System</h5>
                    <div className="mt-auto">
                        <div className="row text-center">
                            <div className="col-md-6 col-lg-3 mb-3">
                                <h6 className="member-name">함효신</h6>
                                <small className="member-info">
                                    <a href="mailto:hamhs0802@gmail.com">hamhs0802@gmail.com</a>
                                </small>
                                <small className="member-info">
                                    <a href="https://github.com/HelenHam" target="_blank">GitHub</a>
                                </small>
                            </div>
                            <div className="col-md-6 col-lg-3 mb-3">
                                <h6 className="member-name">임지은</h6>
                                <small className="member-info">
                                    <a href="mailto:j.lim9304@gmail.com">j.lim9304@gmail.com</a>
                                </small>
                                <small className="member-info">
                                    <a href="https://github.com/GGobung-of-Simba" target="_blank">GitHub</a>
                                </small>
                            </div>
                            <div className="col-md-6 col-lg-3 mb-3">
                                <h6 className="member-name">강승우</h6>
                                <small className="member-info">
                                    <a href="mailto:swparabellum@gmail.com">swparabellum@gmail.com</a>
                                </small>
                                <small className="member-info">
                                    <a href="https://github.com/swparabellum" target="_blank">GitHub</a>
                                </small>
                            </div>
                            <div className="col-md-6 col-lg-3 mb-3">
                                <h6 className="member-name">이경일</h6>
                                <small className="member-info">
                                    <a href="mailto:dlruddlf120914@gmail.com">dlruddlf120914@gmail.com</a>
                                </small>
                                <small className="member-info">
                                    <a href="https://github.com/KY129" target="_blank">GitHub</a>
                                </small>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="col-6 col-sm-4 d-flex justify-content-end">
                    <div className="text-start">
                        <div className="d-flex align-items-center">
                            <h4 className="github-header">GitHub</h4>
                        </div>
                        <ul className="list-unstyled github-links">
                            <li><a href="https://github.com/0neteam" target="_blank">0neteam</a></li>
                            <li><a href="https://github.com/0neteam/Project1" target="_blank">1차 Project / 웹 크롤링</a></li>
                            <li><a href="https://github.com/0neteam/app1" target="_blank">2차 Project / ERP System</a></li>
                            <li><a href="https://github.com/0neteam/ERP_System" target="_blank">3차 Project / ERP System</a></li>
                        </ul>
                    </div>
                </div>
            </div>

            <hr className="bg-light" />

            <div className="footer-bottom">
                <p>Copyright © STEP THREE 2025</p>
                <img src={Logo} alt="Logo" className="footer-logo" />
            </div>
        </div>
    </footer>
  )
}

export default Footer