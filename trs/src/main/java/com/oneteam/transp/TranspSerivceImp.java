package com.oneteam.transp;

import com.oneteam.domain.release.ReleaseEntity;
import com.oneteam.domain.release.ReleaseRepository;
import com.oneteam.domain.transp.TranspEntity;
import com.oneteam.domain.transp.TranspRepository;
import com.oneteam.domain.vehicle.VehicleEntity;
import com.oneteam.domain.vehicle.VehicleRepository;
import com.oneteam.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TranspSerivceImp implements TranspSerivce {

    private final TranspRepository transpRepository;
    private final ReleaseRepository releaseRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public ResDTO findAll(Pageable pageable) {
        boolean status = false;
        String message = "조회된 운송이 없습니다.";
        Object result = null;
        Page<TranspEntity> transpEntities = transpRepository.findAllByUseYn('Y', pageable);
        if(transpEntities != null) {
            status = true;
            message = null;
            result = TranspDTO.findByTransps(transpEntities);
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Override
    public ResDTO findAll(Pageable pageable, TranspSearchReqDTO transpSearchReqDTO) {
        boolean status = false;
        String message = "조회된 운송이 없습니다.";
        Object result = null;
        Integer type = transpSearchReqDTO.getType();
        LocalDateTime startDepDate = getLocalDateTime(transpSearchReqDTO.getStartDate(), false);
        LocalDateTime endDepDate = getLocalDateTime(transpSearchReqDTO.getEndDate(), true);
        LocalDateTime startArrDate = getLocalDateTime(transpSearchReqDTO.getStartDate(), false);
        LocalDateTime endArrDate = getLocalDateTime(transpSearchReqDTO.getEndDate(), true);
        if(type != null) {
            Page<TranspEntity> transps = null;
            if(type == 0) transps = transpRepository.findAllByUseYn('Y', pageable);
            if(type == 1) transps = transpRepository.findAllByUseYnAndDepDateBetween('Y', startDepDate, endDepDate, pageable);
            if(type == 2) transps = transpRepository.findAllByUseYnAndArrDateBetween('Y', startArrDate, endArrDate, pageable);
            if(transps != null) {
                status = true;
                message = null;
                result = TranspDTO.findByTransps(transps);
            }
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Override
    public ResDTO findByNo(Long no, Authentication authentication, Pageable pageable) {
        boolean status = false;
        String message = "존재하지 않는 운송 입니다.";
        Object result = null;
        TranspEntity transpEntity = transpRepository.findByNoAndUseYn(no, 'Y').orElseThrow(() -> new RuntimeException("존재하지 않는 운송 입니다."));
        if(transpEntity != null) {
            Page<ReleaseEntity> releases = releaseRepository.findAllByTranspNoAndUseYn(transpEntity.getNo(), 'Y', pageable);
            status = true;
            message = null;
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("transp", TranspDTO.findByTransp(transpEntity));
            resultMap.put("releases", ReleaseDTO.findByReleases(releases));
            result = resultMap;
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Transactional
    @Override
    public ResDTO register(Long no, TranspReqDTO transpReqDTO, Authentication authentication) {
        boolean status = false;
        String message = "정상적으로 운송이 등록 되지 않았습니다.";
        Object result = null;

        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Transactional
    @Override
    public ResDTO modify(Long no, TranspReqDTO transpReqDTO, Authentication authentication) {
        boolean status = false;
        String message = "정상적으로 운송이 수정 되지 않았습니다.";
        Object result = null;
        Integer point = transpReqDTO.getPoint();
        if(point != null) {
            TranspEntity transp = transpRepository.findByNoAndUseYn(no, 'Y').orElseThrow(() -> new RuntimeException("존재하지 않는 운송 입니다."));
            if (transp != null) {
                if(point == 1) transp.setDepDate(LocalDateTime.now());
                if(point == 2) transp.setArrDate(LocalDateTime.now());
                transp.setModUserNo(Long.parseLong(authentication.getName()));
                log.info("t: {}, p: {}", transp.getNo(), point);
                transp = transpRepository.save(transp);
                if(transp.getNo() > 0) {
                    if(point == 2) {
                        VehicleEntity vehicle = vehicleRepository.findById(transp.getVehicleNo()).orElseThrow(() -> new RuntimeException("존재하지 않는 차량 입니다."));
                        vehicle.setStatus(1);
                        vehicle.setModUserNo(Long.parseLong(authentication.getName()));
                        vehicleRepository.save(vehicle);
                    }
                    status = true;
                    message = null;
                }
            }
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Transactional
    @Override
    public ResDTO delete(Long no, Authentication authentication) {
        boolean status = false;
        String message = "정상적으로 운송이 삭제 되지 않았습니다.";
        Object result = null;

        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    private LocalDateTime getLocalDateTime(String data, boolean type) {
        if(data == null) data = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = "T00:00:00";
        if(type) time = "T23:59:59";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return LocalDateTime.parse(data + time, formatter);
    }

}
