package com.oneteam.dept;

import com.oneteam.domain.dept.DeptUserEntity;
import com.oneteam.domain.dept.DeptUserRepository;
import com.oneteam.domain.user.UserEntity;
import com.oneteam.domain.user.UserRepository;
import com.oneteam.dto.DeptUserDTO;
import com.oneteam.dto.DeptUserReqDTO;
import com.oneteam.dto.ResDTO;
import com.oneteam.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeptUserServiceImp implements DeptUserService {

    private final DeptUserRepository deptUserRepository;
    private final UserRepository userRepository;

    @Override
    public ResDTO findAll(Long no, String name, Pageable pageable) {
        boolean status = false;
        String message = "조회된 부서직원이 없습니다.";
        Object result = null;
        if(name == null) name = "";
        Page<UserEntity> users = userRepository.findByDeptNotUser(no, name, pageable);
        if(users != null) {
            status = true;
            message = null;
            result = UserDTO.findByUsers(users);
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Override
    public ResDTO findByNo(Long no, Pageable pageable) {
        boolean status = false;
        String message = "존재하지 않는 부서직원 입니다.";
        Object result = null;
        Page<DeptUserEntity> deptUsers = deptUserRepository.findAllByDeptNoAndUseYn(no, 'Y', pageable);
        if(deptUsers != null) {
            status = true;
            message = null;
            result = DeptUserDTO.findByDeptUsers(deptUsers);
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Transactional
    @Override
    public ResDTO register(Long no, DeptUserReqDTO deptUserReqDTO, Authentication authentication) {
        boolean status = false;
        String message = "정상적으로 부서 직원이 등록 되지 않았습니다.";
        Object result = null;
        int cnt = 0;
        for(Long userNo :  deptUserReqDTO.getUsers() ) {
            DeptUserEntity deptUserEntity = deptUserRepository.findByDeptNoAndUserNo(no, userNo);
            if(deptUserEntity == null) {
                deptUserEntity = DeptUserEntity.builder()
                        .deptNo(no)
                        .userNo(userNo)
                        .build();
            }
            deptUserEntity.setUseYn('Y');
            deptUserEntity.setRegUserNo(Long.parseLong(authentication.getName()));
            deptUserEntity = deptUserRepository.save(deptUserEntity);
            if(deptUserEntity.getNo() > 0) cnt++;
        }
        if(cnt == deptUserReqDTO.getUsers().size()) {
            status = true;
            message = null;
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Transactional
    @Override
    public ResDTO delete(Long no, DeptUserReqDTO deptUserReqDTO, Authentication authentication) {
        boolean status = false;
        String message = "정상적으로 부서 직원이 삭제 되지 않았습니다.";
        Object result = null;
        int cnt = 0;
        for(Long userNo :  deptUserReqDTO.getUsers() ) {
            DeptUserEntity deptUserEntity = deptUserRepository.findByDeptNoAndUserNo(no, userNo);
            if(deptUserEntity != null) {
                deptUserEntity.setUseYn('N');
                deptUserEntity.setModUserNo(Long.parseLong(authentication.getName()));
                deptUserEntity = deptUserRepository.save(deptUserEntity);
                if (deptUserEntity.getNo() > 0) cnt++;
            }
        }
        if(cnt == deptUserReqDTO.getUsers().size()) {
            status = true;
            message = null;
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }

}
