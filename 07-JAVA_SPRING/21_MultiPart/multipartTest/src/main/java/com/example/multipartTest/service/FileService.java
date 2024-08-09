package com.example.multipartTest.service;

import com.example.multipartTest.dto.res.MultiPartResDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    public MultiPartResDTO checkFile(MultipartFile file) {
        if(file.isEmpty()) {
            throw new RuntimeException("파일 빔" + file.getOriginalFilename());
        }

        String fileName = file.getOriginalFilename();
        long fileSize = file.getSize();

        return new MultiPartResDTO(fileName, fileSize);
    }

    public MultiPartResDTO uploadFile(MultipartFile file) {
        if(file.isEmpty()) {
            throw new RuntimeException("파일 빔" + file.getOriginalFilename());
        }

        String fileName = file.getOriginalFilename();
        long fileSize = file.getSize();

        return new MultiPartResDTO(fileName, fileSize);
    }
}
