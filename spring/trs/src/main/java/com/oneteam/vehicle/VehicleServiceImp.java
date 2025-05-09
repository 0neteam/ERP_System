package com.oneteam.vehicle;

import com.oneteam.domain.release.ReleaseEntity;
import com.oneteam.domain.release.ReleaseRepository;
import com.oneteam.domain.vehicle.VehicleEntity;
import com.oneteam.domain.vehicle.VehicleRepository;
import com.oneteam.dto.*;
import com.oneteam.util.FileComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VehicleServiceImp implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final FileComponent fileComponent;
    private final ReleaseRepository releaseRepository;

    @Override
    public ResDTO findByVehicle(Pageable pageable, VehicleSearchReqDTO vehicleSearchReqDTO) {
        boolean status = false;
        String message = "조회된 차량이 없습니다.";
        Object result = null;
        Integer point = vehicleSearchReqDTO.getPoint();
        Integer no = Integer.parseInt(vehicleSearchReqDTO.getOrderNo());
        String regNumber = vehicleSearchReqDTO.getRegNumber();
        String name = vehicleSearchReqDTO.getName();
        Integer type = vehicleSearchReqDTO.getType();
        List<ReleaseEntity> releases = releaseRepository.findAllByOrderNoAndTranspIsNull(no, 'Y');
        // System.out.println("여기요"+releases.size());
        if(releases.size() != 0){     
            System.out.println("낫널");
            if(point != null) {
                Page<VehicleEntity> vehicles = null;
                if(point == 1) vehicles = vehicleRepository.findAllByUseYnAndStatusAndRegNumberContaining('Y', 1, (regNumber == null)?"":regNumber, pageable);
                else if(point == 2) vehicles = vehicleRepository.findAllByUseYnAndStatusAndNameContaining('Y', 1, (name == null)?"":name, pageable);
                else if(point == 3 && type > 0) vehicles = vehicleRepository.findAllByUseYnAndStatusAndType('Y', 1, type, pageable);
                else vehicles = vehicleRepository.findAllByUseYnAndStatus('Y', 1, pageable);
                if(vehicles != null) {
                    status = true;
                    message = null;
                    result = VehicleDTO.findByVehicles(vehicles);
                }
            }
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Override
    public ResDTO findAll(Pageable pageable, VehicleSearchReqDTO vehicleSearchReqDTO) {
        boolean status = false;
        String message = "조회된 차량이 없습니다.";
        Object result = null;
        Integer point = vehicleSearchReqDTO.getPoint();
        String regNumber = vehicleSearchReqDTO.getRegNumber();
        String name = vehicleSearchReqDTO.getName();
        Integer type = vehicleSearchReqDTO.getType();
        Integer licence = vehicleSearchReqDTO.getLicence();
        Integer state = vehicleSearchReqDTO.getStatus();
        if(point != null) {
            Page<VehicleEntity> vehicles = null;
            if(point == 1) vehicles = vehicleRepository.findAllByUseYnAndRegNumberContaining('Y', (regNumber == null)?"":regNumber, pageable);
            else if(point == 2) vehicles = vehicleRepository.findAllByUseYnAndNameContaining('Y', (name == null)?"":name, pageable);
            else if(point == 3 && type > 0) vehicles = vehicleRepository.findAllByUseYnAndType('Y', type, pageable);
            else if(point == 4 && licence > 0) vehicles = vehicleRepository.findAllByUseYnAndLicence('Y', licence, pageable);
            else if(point == 5 && state > 0) vehicles = vehicleRepository.findAllByUseYnAndStatus('Y', state, pageable);
            else vehicles = vehicleRepository.findAllByUseYn('Y', pageable);
            if(vehicles != null) {
                status = true;
                message = null;
                result = VehicleDTO.findByVehicles(vehicles);
            }
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Override
    public ResDTO findByNo(Long no, Authentication authentication) {
        boolean status = false;
        String message = "존재하지 않는 차량 입니다.";
        Object result = null;
        VehicleEntity vehicleEntity = vehicleRepository.findByUseYnAndNo('Y', no).orElseThrow(() -> new RuntimeException("존재하지 않는 차량 입니다."));
        if(vehicleEntity != null) {
            status = true;
            message = null;
            result = VehicleDTO.findByVehicle(vehicleEntity);
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Transactional
    @Override
    public ResDTO register(VehicleReqDTO vehicleReqDTO, Authentication authentication) {
        boolean status = false;
        String message = "정상적으로 차량이 등록 되지 않았습니다.";
        Object result = null;
        VehicleEntity vehicleEntity = VehicleEntity.builder()
                .regNumber(vehicleReqDTO.getRegNumber())
                .type(vehicleReqDTO.getType())
                .name(vehicleReqDTO.getName())
                .licence(vehicleReqDTO.getLicence())
                .status(1)
                .build();

        if(vehicleReqDTO.getFile() != null) {
            Long fileNo = fileComponent.upload(vehicleReqDTO.getFile(), authentication);
            if(fileNo > 0) vehicleEntity.setFileNo(fileNo);
        }

        vehicleEntity.setUseYn('Y');
        vehicleEntity.setRegUserNo(Long.parseLong(authentication.getName()));
        vehicleEntity = vehicleRepository.save(vehicleEntity);
        if(vehicleEntity.getNo() > 0) {
            status = true;
            message = null;
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Transactional
    @Override
    public ResDTO modify(Long no, VehicleReqDTO vehicleReqDTO, Authentication authentication) {
        boolean status = false;
        String message = "정상적으로 차량이 수정 되지 않았습니다.";
        Object result = null;
        VehicleEntity vehicleEntity = vehicleRepository.findById(no).orElseThrow(() -> new RuntimeException("존재하지 않는 차량 입니다."));

        if(vehicleReqDTO.getFile() != null) {
            Long fileNo = fileComponent.upload(vehicleReqDTO.getFile(), authentication);
            if(fileNo > 0) vehicleEntity.setFileNo(fileNo);
        }

        vehicleEntity.setStatus(vehicleReqDTO.getStatus());
        vehicleEntity.setModUserNo(Long.parseLong(authentication.getName()));
        vehicleEntity = vehicleRepository.save(vehicleEntity);
        if(vehicleEntity.getNo() > 0) {
            status = true;
            message = null;
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Transactional
    @Override
    public ResDTO delete(Long no, Authentication authentication) {
        boolean status = false;
        String message = "정상적으로 차량이 삭제 되지 않았습니다.";
        Object result = null;
        VehicleEntity vehicleEntity = vehicleRepository.findById(no).orElseThrow(() -> new RuntimeException("존재하지 않는 차량 입니다."));
        vehicleEntity.setUseYn('N');
        vehicleEntity.setModUserNo(Long.parseLong(authentication.getName()));
        vehicleEntity = vehicleRepository.save(vehicleEntity);
        if(vehicleEntity.getNo() > 0) {
            status = true;
            message = null;
        }
        return ResDTO.builder().status(status).message(message).build();
    }


    private LocalDateTime getLocalDateTime(String data, boolean type) {
        if(data == null) data = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = "T00:00:00";
        if(type) time = "T23:59:59";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return LocalDateTime.parse(data + time, formatter);
    }

}
