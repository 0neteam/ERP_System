package com.oneteam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oneteam.domain.dept.DeptUserEntity;
import com.oneteam.domain.user.UserEntity;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeptUserDTO {

  private Long no;
  private Long deptNo;
  private Long userNo;
  private UserDTO user;
  private String regUserName;
  private String modUserName;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime regDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime modDate;

  public static DeptUserDTO findByDeptUser(DeptUserEntity deptUserEntity) {
    UserDTO userDTO = null;
    UserEntity userEntity =  deptUserEntity.getTargetUser();
    if(userEntity != null) {
      if(userEntity.getUseYn() == 'Y') {
        userDTO = UserDTO.builder()
            .no(userEntity.getNo())
            .name(userEntity.getName())
            .fileNo(userEntity.getFileNo())
            .build();
      }
    }
    return (deptUserEntity == null) ? null : DeptUserDTO.builder()
        .no(deptUserEntity.getNo())
        .deptNo(deptUserEntity.getDeptNo())
        .userNo(deptUserEntity.getUserNo())
        .user(userDTO)
        .regDate(deptUserEntity.getRegDate())
        .regUserName((deptUserEntity.getRegUser() == null) ? null : deptUserEntity.getRegUser().getName())
        .modDate(deptUserEntity.getModDate())
        .modUserName((deptUserEntity.getModUser() == null) ? null : deptUserEntity.getModUser().getName())
        .build();
  }

  public static Map<String, Object> findByDeptUsers(Page<DeptUserEntity> deptUserEntities) {
    Map<String, Object> resultMap = new HashMap<>();
    List<DeptUserDTO> deptUsers = new ArrayList<>();
    deptUserEntities.forEach(deptUser -> deptUsers.add(DeptUserDTO.findByDeptUser(deptUser)));
    resultMap.put("list", deptUsers);
    resultMap.put("totalElements", deptUserEntities.getTotalElements());
    resultMap.put("totalPages", deptUserEntities.getTotalPages());
    resultMap.put("size", deptUserEntities.getSize());
    return resultMap;
  }

}
