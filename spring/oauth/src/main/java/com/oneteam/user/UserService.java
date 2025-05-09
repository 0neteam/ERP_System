package com.oneteam.user;

import com.oneteam.dto.AuthReqDTO;
import com.oneteam.dto.ResDTO;
import com.oneteam.dto.UserInfoReqDTO;
import com.oneteam.dto.UserReqDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;

public interface UserService {

  public ResDTO userInfo(Authentication authentication);
  public ResDTO signIn(AuthReqDTO authReqDTO, HttpServletRequest request, HttpServletResponse response, HttpSession session);
  public ResDTO signUp(UserReqDTO userDto);
  public ResDTO modify(Long no, UserInfoReqDTO userInfoReqDTO, Authentication authentication);
  public ResDTO email(UserReqDTO userDto);
  public ResDTO auth(AuthReqDTO authReqDTO);
  public ResDTO logout(HttpServletRequest request, HttpServletResponse response);

}
