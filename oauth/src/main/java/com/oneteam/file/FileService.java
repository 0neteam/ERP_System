package com.oneteam.file;

import com.oneteam.dto.ResDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public ResDTO upload(MultipartFile file, Authentication authentication);
    public ResponseEntity<?> uri(String type, Long fileNo, Authentication authentication);

}
