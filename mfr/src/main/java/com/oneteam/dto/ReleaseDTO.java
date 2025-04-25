package com.oneteam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oneteam.domain.release.ReleaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "입출고 응답 데이터")
public class ReleaseDTO {

  private Long no;
  private Long orderItemNo;
  private Long itemNo;
  private String itemName;
  private Long transpNo;
  private int oQty;
  private int iQty;
  private int pQty;
  private int status;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime depDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime arrDate;

  private String regUserName;
  private String modUserName;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime regDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "ASIA/Seoul")
  private LocalDateTime modDate;

  public static ReleaseDTO findByRelease(ReleaseEntity releaseEntity) {
    return (releaseEntity == null) ? null : ReleaseDTO.builder()
        .no(releaseEntity.getNo())
        .orderItemNo(releaseEntity.getOrderItemNo())
        .itemNo(releaseEntity.getItemNo())
        .itemName((releaseEntity.getItem() == null) ? null : releaseEntity.getItem().getName())
        .transpNo(releaseEntity.getTranspNo())
        .oQty(releaseEntity.getOQty())
        .iQty(releaseEntity.getIQty())
        .pQty(releaseEntity.getPQty())
        .status(releaseEntity.getStatus())
        .depDate(releaseEntity.getDepDate())
        .arrDate(releaseEntity.getArrDate())
        .regDate(releaseEntity.getRegDate())
        .regUserName((releaseEntity.getRegUser() == null) ? null : releaseEntity.getRegUser().getName())
        .modDate(releaseEntity.getModDate())
        .modUserName((releaseEntity.getModUser() == null) ? null : releaseEntity.getModUser().getName())
        .build();
  }

  public static Map<String, Object> findByReleases(Page<ReleaseEntity> releaseEntitys) {
    Map<String, Object> resultMap = new HashMap<>();
    List<ReleaseDTO> releases = new ArrayList<>();
    releaseEntitys.forEach(release -> releases.add(ReleaseDTO.findByRelease(release)));
    resultMap.put("list", releases);
    resultMap.put("totalElements", releaseEntitys.getTotalElements());
    resultMap.put("totalPages", releaseEntitys.getTotalPages());
    resultMap.put("size", releaseEntitys.getSize());
    return resultMap;
  }

  public static Map<String, Object> findByReleases(List<ReleaseEntity> releaseEntitys) {
    Map<String, Object> resultMap = new HashMap<>();
    List<ReleaseDTO> releases = new ArrayList<>();
    releaseEntitys.forEach(release -> releases.add(ReleaseDTO.findByRelease(release)));
    resultMap.put("list", releases);
    return resultMap;
  }

}
