package com.oneteam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oneteam.domain.dept.DeptEntity;
import com.oneteam.domain.user.UserEntity;
import lombok.*;

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
public class DeptDTO {

    private Long no;
    private String name;
    private String deptName;
    private List<UserDTO> users;
    private String regUserName;
    private String modUserName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
    private LocalDateTime regDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
    private LocalDateTime modDate;

    public static DeptDTO findByDept(DeptEntity deptEntity) {
        List<UserEntity> userEntities = deptEntity.getUsers();
        List<UserDTO> users = new ArrayList<>();
        if(userEntities != null) {
            userEntities.forEach(user -> users.add(UserDTO.findByUser(user)));
        }
        return (deptEntity == null) ? null : DeptDTO.builder()
                .no(deptEntity.getNo())
                .name(deptEntity.getName())
                .deptName(deptEntity.getDeptName())
                .users(users)
                .regDate(deptEntity.getRegDate())
                .regUserName((deptEntity.getRegUser() == null) ? null : deptEntity.getRegUser().getName())
                .modDate(deptEntity.getModDate())
                .modUserName((deptEntity.getModUser() == null) ? null : deptEntity.getModUser().getName())
                .build();
    }

    public static Map<String, Object> findByDepts(List<DeptEntity> deptEntities) {
        Map<String, Object> resultMap = new HashMap<>();
        List<DeptDTO> depts = new ArrayList<>();
        deptEntities.forEach(dept -> depts.add(DeptDTO.findByDept(dept)));
        resultMap.put("list", depts);
        return resultMap;
    }

}
