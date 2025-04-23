package com.oneteam.util;

import com.oneteam.domain.file.FileEntity;
import com.oneteam.domain.file.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileComponent {

  private final FileRepository fileRepository;

  private String getRootPath() {return new File("").getAbsolutePath();}
  private String lastPath = "/upload";
  private String getCurrnetDatePath() {return "/".concat(new SimpleDateFormat("yyyyMMdd").format(new Date()));}
  private String getName(MultipartFile file) {return file.getOriginalFilename();}
  private String setName() {return Long.toString(System.nanoTime());}
  private String getContentType(MultipartFile file) {return file.getContentType();}
  private String getExtension(MultipartFile file) {
    String contentType = file.getContentType();
    String name = getName(file);
    String originalFileExtension = "";
    if (!ObjectUtils.isEmpty(contentType)){
      if(contentType.contains("image/jpeg")){originalFileExtension = ".jpg";}
      else if(contentType.contains("image/png")){originalFileExtension = ".png";}
      else if(contentType.contains("image/gif")){originalFileExtension = ".gif";}
      else if(name.lastIndexOf(".") > 0){originalFileExtension = name.substring(name.lastIndexOf("."), name.length());}
    }
    return originalFileExtension;
  }

  @Transactional
  public Long upload(MultipartFile file, Authentication authentication) {
    if(authentication != null && file != null) {
      Long userNo = Long.parseLong(authentication.getName());
      String origin = getName(file);
      String name = setName();
      String ext = getExtension(file);
      String mediaType = getContentType(file);
      String attachPath = getRootPath().concat(lastPath).concat(getCurrnetDatePath()).concat("/" + userNo);
      log.info("attachPath : {}", attachPath);
      try {
        File newFile = new File(attachPath.concat("/").concat(name).concat(ext));
        if(!newFile.exists()){newFile.mkdirs();}
        file.transferTo(newFile);
        FileEntity fileInfo = FileEntity.builder()
            .origin(origin)
            .name(name)
            .attachPath(attachPath)
            .ext(ext)
            .mediaType(mediaType)
            .build();
        fileInfo.setUseYn('Y');
        fileInfo.setRegUserNo(userNo);
        fileInfo = fileRepository.save(fileInfo);
        return (fileInfo.getNo() > 0) ? fileInfo.getNo() : null;
      } catch (IOException e) {
        log.error(e.getMessage());
      }
    }
    return null;
  }

}
