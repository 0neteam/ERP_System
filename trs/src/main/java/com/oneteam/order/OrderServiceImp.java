package com.oneteam.order;

import com.oneteam.domain.order.OrderRepository;
import com.oneteam.domain.release.ReleaseEntity;
import com.oneteam.domain.release.ReleaseRepository;
import com.oneteam.domain.transp.TranspEntity;
import com.oneteam.domain.transp.TranspRepository;
import com.oneteam.domain.user.UserEntity;
import com.oneteam.domain.user.UserRepository;
import com.oneteam.domain.vehicle.VehicleEntity;
import com.oneteam.domain.vehicle.VehicleRepository;
import com.oneteam.dto.OrderReqDTO;
import com.oneteam.dto.OrderSearchReqDTO;
import com.oneteam.dto.ResDTO;
import com.oneteam.dto.TranspUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

  private final OrderRepository orderRepository;
  private final TranspRepository transpRepository;
  private final ReleaseRepository releaseRepository;
  private final VehicleRepository vehicleRepository;
  private final UserRepository userRepository;

  @Override
  public ResDTO findAll(Pageable pageable, OrderSearchReqDTO orderSearchReqDTO) {
    boolean status = false;
    String message = "조회된 운송 기사가 없습니다.";
    Object result = null;
    Integer type = orderSearchReqDTO.getType();
    String name = orderSearchReqDTO.getName();
    if(name == null) name = "";
    if(type != null) {
      Page<TranspUserDTO> transpUsers = null;
      if(type == 1) transpUsers = orderRepository.findAllByLicence1(name, pageable);
      if(type == 2) transpUsers = orderRepository.findAllByLicence2(name, pageable);
      if(type == 3) transpUsers = orderRepository.findAllByLicence3(name, pageable);
      if(type == 4) transpUsers = orderRepository.findAllByLicence4(name, pageable);
      status = true;
      message = null;
      result = transpUsers;
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Transactional
  @Override
  public ResDTO register(Long no, OrderReqDTO orderReqDTO, Authentication authentication) {
    boolean status = false;
    String message = "정상적으로 운송 기사가 등록 되지 않았습니다.";
    Object result = null;

    VehicleEntity vehicleEntity = vehicleRepository.findById(orderReqDTO.getVehicleNo()).orElseThrow(() -> new RuntimeException("존재하지 않는 차량 입니다."));
    UserEntity userEntity = userRepository.findById(orderReqDTO.getUserNo()).orElseThrow(() -> new RuntimeException("존재하지 않는 기사 입니다."));

    TranspEntity transpEntity = TranspEntity.builder()
        .userNo(userEntity.getNo())
        .vehicleNo(vehicleEntity.getNo())
        .build();
    transpEntity.setUseYn('Y');
    transpEntity.setRegUserNo(Long.parseLong(authentication.getName()));

    transpEntity = transpRepository.save(transpEntity);
    if(transpEntity.getNo() > 0) {
      List<ReleaseEntity> releases = releaseRepository.findAllByOrderItemNoAndUseYnAndTranspNoNull(no, 'Y');
      int cnt = 0;
      for(ReleaseEntity releaseEntity : releases) {
        log.info("R : {}", releaseEntity.getNo());
        releaseEntity.setTranspNo(transpEntity.getNo());
        releaseEntity.setModUserNo(Long.parseLong(authentication.getName()));
        releaseEntity = releaseRepository.save(releaseEntity);
        if(releaseEntity.getNo() > 0) cnt++;
      }
      if(cnt == releases.size()) {
        vehicleEntity.setStatus(2);
        vehicleEntity.setModUserNo(Long.parseLong(authentication.getName()));
        vehicleEntity = vehicleRepository.save(vehicleEntity);
        if(vehicleEntity.getNo() > 0) {
          status = true;
          message = null;
        }
      }
    }
    return ResDTO.builder().status(status).message(message).build();
  }

}
