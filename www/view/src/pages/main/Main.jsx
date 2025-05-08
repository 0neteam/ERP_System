import MainImg  from '@assets/main/main.jpg'
import MfrImg  from '@assets/main/mfr.jpg'
import StgImg  from '@assets/main/stg.jpg'
import TrsImg  from '@assets/main/trs.jpg'
import '@styles/main/Main.css'

const Main = () => {
  return (
    <>
      <section className="hero" style={{backgroundImage: `linear-gradient(rgba(0,0,0,0.4), rgba(0,0,0,0.4)), url(${MainImg})`}}>
        <div className="container text-center hero-overlay">
          <h1 className="animate__animated animate__fadeInDown">환영합니다!</h1>
          <p className="lead animate__animated animate__fadeInUp">최첨단 ERP 솔루션과 함께하는 통합 유통 시스템</p>
          <a href="#services" className="btn btn-primary btn-lg mt-3 animate__animated animate__fadeInUp">더 알아보기</a>
        </div>
      </section>

      <section id="services" className="section bg-light">
          <div className="container">
              <h2 className="section-title">서비스 소개</h2>
              <div className="row align-items-center mb-5">
                  <div className="col-md-6">
                      <img src={StgImg} alt="발주 서비스" className="img-fluid animate-img animate__animated animate__fadeInLeft" />
                  </div>
                  <div className="col-md-6">
                      <h3 className="mt-4">발주 서비스</h3>
                      <p>
                          고객의 주문을 신속하게 처리하여 재고관리를 효과적으로 수행합니다. 전문적인 발주 신청 및 확인 시스템으로 물류 흐름을 최적화합니다.
                      </p>
                  </div>
              </div>
              <div className="row align-items-center mb-5">
                  <div className="col-md-6 order-md-2">
                      <img src={MfrImg} alt="제조 서비스" className="img-fluid animate-img animate__animated animate__zoomIn" />
                  </div>
                  <div className="col-md-6 order-md-1">
                      <h3 className="mt-4">제조 서비스</h3>
                      <p>
                          효율적인 생산 프로세스 관리로 고품질 제품을 생산합니다. 생산 일정 관리와 실시간 모니터링을 통해 생산성을 극대화합니다.
                      </p>
                  </div>
              </div>
              <div className="row align-items-center">
                  <div className="col-md-6">
                      <img src={TrsImg} alt="운송 서비스" className="img-fluid animate-img animate__animated animate__fadeInRight" />
                  </div>
                  <div className="col-md-6">
                      <h3 className="mt-4">운송 서비스</h3>
                      <p>
                          최적의 물류 경로와 전문 운송 인력을 통해 신속하고 안전한 배송을 보장합니다. 고객 만족도를 높이는 정시 배송 서비스를 제공합니다.
                      </p>
                  </div>
              </div>
          </div>
      </section>

      {/* <section id="vision" className="section">
          <div className="container">
              <h2 className="section-title">비전 &amp; 미션</h2>
              <div className="row">
                  <div className="col-md-6">
                      <h3>우리의 비전</h3>
                      <p>
                          당사는 혁신적인 ERP 솔루션을 통해 고객사의 경쟁력을 강화하고, 글로벌 시장에서 선도적인 역할을 수행하는 것을 목표로 합니다. 변화하는 시장 환경에 발맞춰 지속적인 기술 혁신과 고객 맞춤형 서비스를 제공하여, 신뢰와 우수성의 기준이 되고자 합니다.
                      </p>
                  </div>
                  <div className="col-md-6">
                      <h3>우리의 미션</h3>
                      <p>
                          고객의 비즈니스 프로세스를 최적화하며, 효율적이고 투명한 운영 시스템을 구축하는 것이 우리의 미션입니다. 이를 위해 끊임없는 연구개발과 고객 의견 수렴을 통해 최고의 ERP 솔루션을 제공하고, 고객 성공을 함께 만들어 갑니다.
                      </p>
                  </div>
              </div>
          </div>
      </section>

      <section id="testimonials" className="section">
          <div className="container">
              <h2 className="section-title">고객 후기</h2>
              <div className="row">
                  <div className="col-md-4">
                      <blockquote className="blockquote">
                          <p>"ERP 솔루션 도입 후 업무 효율이 비약적으로 향상되었습니다. 사용하기 쉽고 안정적인 시스템입니다."</p>
                          <footer className="blockquote-footer">고객 A, <cite title="Source Title">대표이사</cite></footer>
                      </blockquote>
                  </div>
                  <div className="col-md-4">
                      <blockquote className="blockquote">
                          <p>"전문적인 지원과 우수한 기술력 덕분에 우리 회사의 운영 효율이 크게 개선되었습니다."</p>
                          <footer className="blockquote-footer">고객 B, <cite title="Source Title">CTO</cite></footer>
                      </blockquote>
                  </div>
                  <div className="col-md-4">
                      <blockquote className="blockquote">
                          <p>"직원들이 ERP 시스템을 통해 더 체계적으로 업무를 관리하게 되어 전반적인 업무 만족도가 높아졌습니다."</p>
                          <footer className="blockquote-footer">고객 C, <cite title="Source Title">운영 관리자</cite></footer>
                      </blockquote>
                  </div>
              </div>
          </div>
      </section>

      <section id="news" className="section bg-light">
          <div className="container">
              <h2 className="section-title">뉴스 &amp; 이벤트</h2>
              <div className="row">
                  <div className="col-md-6 mb-4">
                      <article>
                          <h4>신제품 ERP 모듈 출시 기념 이벤트</h4>
                          <p>
                              최신 ERP 모듈 출시를 기념하여 한정 기간 동안 특별 할인 및 무료 체험 이벤트를 진행합니다. 자세한 내용은 공지 사항을 확인해 주세요.
                          </p>
                          <a href="#" className="btn btn-outline-success">자세히 보기</a>
                      </article>
                  </div>
                  <div className="col-md-6">
                      <article>
                          <h4>미니 프로젝트 카페 &amp; 블로그</h4>
                          <p>
                              미니 프로젝트 카페 &amp; 블로그는 소규모 프로젝트의 아이디어와 진행 과정을 공유하는 열린 공간입니다. 개발자들의 경험담, 최신 트렌드, 기술 팁과 사용자 후기를 통해 여러분의 창의력을 자극하고 협업 문화를 만들어갑니다.
                          </p>
                          <a href="#" className="btn btn-outline-success">자세히 보기</a>
                      </article>
                  </div>
              </div>
          </div>
      </section>

      <section id="history" className="section bg-light">
          <div className="container">
              <h2 className="section-title">히스토리</h2>
              <ul className="list-group">
                  <li className="list-group-item">
                      <strong>2025년 4월</strong> - ERP System 3차 프로젝트 런칭.
                  </li>
                  <li className="list-group-item">
                      <strong>2025년 2월</strong> - 혁신적인 ERP 솔루션 개발.
                  </li>
                  <li className="list-group-item">
                      <strong>2025년 1월</strong> - 2차 프로젝트 APP1, APP2 서비스 정식 오픈.
                  </li>
                  <li className="list-group-item">
                      <strong>2024년 12월</strong> - 1차 프로젝트 런칭.
                  </li>
              </ul>
          </div>
      </section>

      <section id="contact" className="section">
          <div className="container">
              <h2 className="section-title">문의하기</h2>
              <div className="row justify-content-center">
                  <div className="col-md-8">
                      <form action="/contact_process" method="post">
                          <div className="mb-3">
                              <label htmlFor="name" className="form-label">이름</label>
                              <input type="text" className="form-control" id="name" name="name" placeholder="이름을 입력하세요" required />
                          </div>
                          <div className="mb-3">
                              <label htmlFor="email" className="form-label">이메일</label>
                              <input type="email" className="form-control" id="email" name="email" placeholder="이메일을 입력하세요" required />
                          </div>
                          <div className="mb-3">
                              <label htmlFor="message" className="form-label">문의 내용</label>
                              <textarea className="form-control" id="message" name="message" rows="4" placeholder="문의 사항을 작성해주세요" required></textarea>
                          </div>
                          <button type="submit" className="btn btn-outline-success">전송하기</button>
                      </form>
                  </div>
              </div>
          </div>
      </section> */}
    </>
  )
}

export default Main;