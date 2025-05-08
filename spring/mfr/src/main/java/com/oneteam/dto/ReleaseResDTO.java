package com.oneteam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseResDTO {

  private Long orderNo;
  private Long transpNo;
  private String depDate;
  private String arrDate;

}
