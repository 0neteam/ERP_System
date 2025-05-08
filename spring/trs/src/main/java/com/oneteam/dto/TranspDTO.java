package com.oneteam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oneteam.domain.transp.TranspEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "운송 응답 데이터")
public class TranspDTO {

    private Long no;
    private Long userNo;
    private VehicleDTO vehicle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
    private LocalDateTime depDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
    private LocalDateTime arrDate;

    private Long fileNo;
    private String userName;
    private String userEmail;

    private String regUserName;
    private String modUserName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
    private LocalDateTime regDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
    private LocalDateTime modDate;

    public static TranspDTO findByTransp(TranspEntity transpEntity) {
        return (transpEntity == null) ? null : TranspDTO.builder()
                .no(transpEntity.getNo())
                .userNo(transpEntity.getUserNo())
                .vehicle((transpEntity.getVehicle() == null) ? null : VehicleDTO.findByVehicle(transpEntity.getVehicle()))
                .depDate(transpEntity.getDepDate())
                .arrDate(transpEntity.getArrDate())
                .fileNo((transpEntity.getUser() == null) ? null : transpEntity.getUser().getFileNo())
                .userName((transpEntity.getUser() == null) ? null : transpEntity.getUser().getName())
                .userEmail((transpEntity.getUser() == null) ? null : transpEntity.getUser().getEmail())
                .regDate(transpEntity.getRegDate())
                .regUserName((transpEntity.getRegUser() == null) ? null : transpEntity.getRegUser().getName())
                .modDate(transpEntity.getModDate())
                .modUserName((transpEntity.getModUser() == null) ? null : transpEntity.getModUser().getName())
                .build();
    }

    public static Map<String, Object> findByTransps(Page<TranspEntity> transpEntitys) {
        Map<String, Object> resultMap = new HashMap<>();
        List<TranspDTO> transps = new ArrayList<>();
        transpEntitys.forEach(transp -> transps.add(TranspDTO.findByTransp(transp)));
        resultMap.put("list", transps);
        resultMap.put("totalElements", transpEntitys.getTotalElements());
        resultMap.put("totalPages", transpEntitys.getTotalPages());
        resultMap.put("size", transpEntitys.getSize());
        return resultMap;
    }

}
