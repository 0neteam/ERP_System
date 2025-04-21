package com.oneteam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oneteam.domain.dept.DeptEntity;
import com.oneteam.domain.dept.DeptUserEntity;
import com.oneteam.domain.user.UserEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long no;
    private String email;
    private String name;
    private Long fileNo;
    private char licence1;
    private char licence2;
    private char licence3;
    private char licence4;
    private char useYn;
    private String regUserName;
    private String modUserName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
    private LocalDateTime regDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
    private LocalDateTime modDate;

    private List<DeptDTO> depts;

    public static UserDTO findByUser(UserEntity userEntity) {
        List<DeptDTO> arr = new ArrayList<>();
        userEntity.getDepts().forEach(dept -> {
            boolean check = true;
            for(DeptUserEntity du : userEntity.getDeptUsers()) {
                if(dept.getNo() == du.getDeptNo()) {
                    if(du.getUseYn() == 'N') check = false;
                }
            }
            if(check) {
                DeptDTO deptDTO = DeptDTO.builder()
                        .no(dept.getNo())
                        .name(dept.getName())
                        .deptName(dept.getDeptName())
                        .build();
                arr.add(deptDTO);
            }
        });
        return (userEntity == null) ? null : UserDTO.builder()
                .no(userEntity.getNo())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .fileNo(userEntity.getFileNo())
                .licence1(userEntity.getLicence1())
                .licence2(userEntity.getLicence2())
                .licence3(userEntity.getLicence3())
                .licence4(userEntity.getLicence4())
                .useYn(userEntity.getUseYn())
                .depts(arr)
                .regDate(userEntity.getRegDate())
                .regUserName((userEntity.getRegUser() == null) ? null : userEntity.getRegUser().getName())
                .modDate(userEntity.getModDate())
                .modUserName((userEntity.getModUser() == null) ? null : userEntity.getModUser().getName())
                .build();
    }

    public static Map<String, Object> findByUsers(Page<UserEntity> userEntities) {
        Map<String, Object> resultMap = new HashMap<>();
        List<UserDTO> users = new ArrayList<>();
        userEntities.forEach(user -> users.add(UserDTO.findByUser(user)));
        resultMap.put("list", users);
        resultMap.put("totalElements", userEntities.getTotalElements());
        resultMap.put("totalPages", userEntities.getTotalPages());
        resultMap.put("size", userEntities.getSize());
        return resultMap;
    }

}
