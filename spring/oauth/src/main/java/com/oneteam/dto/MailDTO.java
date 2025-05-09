package com.oneteam.dto;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {

  private String emailFrom;
  private String emailTo;
  private String emailSubject;
  private String emailBody;
  private boolean emailHtmlEnable;

}
