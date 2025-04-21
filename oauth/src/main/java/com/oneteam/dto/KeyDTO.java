package com.oneteam.dto;

import lombok.*;

import java.time.LocalTime;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeyDTO {

    private String email;
    private String key;
    private LocalTime regTime;

}
