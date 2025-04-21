package com.oneteam.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeptUserReqDTO {

    private List<Long> users;

}
