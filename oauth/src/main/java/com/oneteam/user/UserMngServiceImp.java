package com.oneteam.user;

import com.oneteam.domain.user.UserEntity;
import com.oneteam.domain.user.UserRepository;
import com.oneteam.dto.ResDTO;
import com.oneteam.dto.UserDTO;
import com.oneteam.dto.UserMngReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserMngServiceImp implements UserMngService {

    private final UserRepository userRepository;

    @Override
    public ResDTO findAll(String name, Pageable pageable) {
        boolean status = false;
        String message = "조회된 직원이 없습니다.";
        Object result = null;
        if(name == null) name = "";
        Page<UserEntity> users = userRepository.findAllByNameContainingAndUseYn(name, 'Y', pageable);
        if(users != null) {
            status = true;
            message = null;
            result = UserDTO.findByUsers(users);
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Override
    public ResDTO findByNo(Long no, Authentication authentication) {
        boolean status = false;
        String message = "존재하지 않는 직원 입니다.";
        Object result = null;

        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Transactional
    @Override
    public ResDTO modify(Long no, UserMngReqDTO userMngReqDTO, Authentication authentication) {
        boolean status = false;
        String message = "정상적으로 직원 정보가 수정 되지 않았습니다.";
        Object result = null;
        UserEntity userEntity = userRepository.findById(no).orElseThrow(() -> new RuntimeException("존재하지 않는 사용자 입니다."));
        userEntity.setLicence1("Y".equals(userMngReqDTO.getLicence1()) ? 'Y' : 'N');
        userEntity.setLicence2("Y".equals(userMngReqDTO.getLicence2()) ? 'Y' : 'N');
        userEntity.setLicence3("Y".equals(userMngReqDTO.getLicence3()) ? 'Y' : 'N');
        userEntity.setLicence4("Y".equals(userMngReqDTO.getLicence4()) ? 'Y' : 'N');
        userEntity = userRepository.save(userEntity);
        if(userEntity.getNo() > 0) {
            status = true;
            message = null;
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }

    @Transactional
    @Override
    public ResDTO delete(Long no, Authentication authentication) {
        boolean status = false;
        String message = "정상적으로 직원 정보가 삭제 되지 않았습니다.";
        Object result = null;
        UserEntity userEntity = userRepository.findById(no).orElseThrow(() -> new RuntimeException("존재하지 않는 사용자 입니다."));
        userEntity.setUseYn('N');
        userEntity.setModUserNo(Long.parseLong(authentication.getName()));
        userEntity = userRepository.save(userEntity);
        if(userEntity.getNo() > 0) {
            status = true;
            message = null;
        }
        return ResDTO.builder().status(status).result(result).message(message).build();
    }
}
