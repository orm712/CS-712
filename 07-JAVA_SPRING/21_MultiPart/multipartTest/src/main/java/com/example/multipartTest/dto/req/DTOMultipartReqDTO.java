package com.example.multipartTest.dto.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DTOMultipartReqDTO {
    private String fileName;
    private MultipartFile file;
}
