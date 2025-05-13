package com.oneteam.file;

import com.oneteam.dto.ResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController implements FileControllerDocs {

  private final FileService fileService;

  @PreAuthorize("isAuthenticated()")
  @ResponseBody
  @PostMapping
  public ResDTO upload(@RequestParam("file") MultipartFile file, Authentication authentication) {
    return fileService.upload(file, authentication);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/{type:[iu]}/{no:[0-9]+}")
  public ResponseEntity<?> uri(@PathVariable("type") String type, @PathVariable("no") Long no, Authentication authentication) {
    return fileService.uri(type, no, authentication);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/{no:[0-9]+}")
  public ResponseEntity<?> uri(@PathVariable("no") Long no, Authentication authentication) {
    return fileService.uri(no, authentication);
  }

}
