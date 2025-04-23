package com.oneteam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oneteam.domain.vehicle.VehicleEntity;
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
@Schema(description = "차량 응답 데이터")
public class VehicleDTO {

    private Long no;
    private String regNumber;
    private int type;
    private String name;
    private int licence;
    private int status;
    private Long fileNo;

    private String regUserName;
    private String modUserName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "ASIA/Seoul")
    private LocalDateTime regDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
    private LocalDateTime modDate;

    public static VehicleDTO findByVehicle(VehicleEntity vehicleEntity) {
        return (vehicleEntity == null) ? null : VehicleDTO.builder()
                .no(vehicleEntity.getNo())
                .regNumber(vehicleEntity.getRegNumber())
                .type(vehicleEntity.getType())
                .name(vehicleEntity.getName())
                .licence(vehicleEntity.getLicence())
                .status(vehicleEntity.getStatus())
                .fileNo(vehicleEntity.getFileNo())
                .regDate(vehicleEntity.getRegDate())
                .regUserName((vehicleEntity.getRegUser() == null) ? null : vehicleEntity.getRegUser().getName())
                .modDate(vehicleEntity.getModDate())
                .modUserName((vehicleEntity.getModUser() == null) ? null : vehicleEntity.getModUser().getName())
                .build();
    }

    public static Map<String, Object> findByVehicles(Page<VehicleEntity> vehicleEntitys) {
        Map<String, Object> resultMap = new HashMap<>();
        List<VehicleDTO> vehicles = new ArrayList<>();
        vehicleEntitys.forEach(vehicle -> vehicles.add(VehicleDTO.findByVehicle(vehicle)));
        resultMap.put("list", vehicles);
        resultMap.put("totalElements", vehicleEntitys.getTotalElements());
        resultMap.put("totalPages", vehicleEntitys.getTotalPages());
        resultMap.put("size", vehicleEntitys.getSize());
        return resultMap;
    }

}
