package com.oneteam.dept;

import com.oneteam.domain.dept.DeptEntity;
import com.oneteam.domain.dept.DeptRepository;
import com.oneteam.dto.DeptDTO;
import com.oneteam.dto.DeptReqDTO;
import com.oneteam.dto.ResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeptServiceImp implements DeptService{

    private final DeptRepository deptRepository;

    @Override
    public ResDTO findAll() {
        boolean status = false;
        String message = "조회된 부서가 없습니다.";
        Object result = null;
        List<DeptEntity> depts = deptRepository.findAllByUseYn('Y');
        if(depts != null) {
            status = true;
            message = null;
            result = DeptDTO.findByDepts(depts);
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Override
    public ResDTO findByNo(Long no, Authentication authentication) {
        boolean status = false;
        String message = "존재하지 않는 부서 입니다.";
        Object result = null;
        DeptEntity deptEntity = deptRepository.findByNoAndUseYn(no, 'Y').orElseThrow(() -> new RuntimeException("존재하지 않는 부서 입니다."));
        if(deptEntity.getNo() > 0) {
            status = true;
            message = null;
            result = DeptDTO.findByDept(deptEntity);
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Transactional
    @Override
    public ResDTO register(DeptReqDTO deptReqDTO, Authentication authentication) {
        boolean status = false;
        String message = "정상적으로 부서가 등록 되지 않았습니다.";
        Object result = null;
        DeptEntity deptEntity = DeptEntity.builder().name(deptReqDTO.getName()).deptName(deptReqDTO.getDeptName()).build();
        deptEntity.setUseYn('Y');
        deptEntity.setRegUserNo(Long.parseLong(authentication.getName()));
        deptEntity = deptRepository.save(deptEntity);
        if(deptEntity.getNo() > 0) {
            status = true;
            message = null;
            result = DeptDTO.findByDept(deptEntity);
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Transactional
    @Override
    public ResDTO delete(Long no, Authentication authentication) {
        boolean status = false;
        String message = "정상적으로 부서가 삭제 되지 않았습니다.";
        Object result = null;
        DeptEntity deptEntity = deptRepository.findByNoAndUseYn(no, 'Y').orElseThrow(() -> new RuntimeException("존재하지 않는 부서 입니다."));
        deptEntity.setUseYn('N');
        deptEntity.setModUserNo(Long.parseLong(authentication.getName()));
        deptEntity = deptRepository.save(deptEntity);
        if(deptEntity.getNo() > 0) {
            status = true;
            message = null;
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }
}
