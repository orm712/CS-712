package com.example.multipartTest.controller;

import com.example.multipartTest.dto.req.DTOMetadataReqDTO;
import com.example.multipartTest.dto.req.DTOMultipartReqDTO;
import com.example.multipartTest.dto.res.MultiPartResDTO;
import com.example.multipartTest.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final FileService fileService;

    //단일 파일 받아오기
    @PostMapping(value = "/upload")
    public ResponseEntity<MultiPartResDTO> uploadFile(@RequestPart("file") MultipartFile file) {
        try{
            log.info(file.getOriginalFilename());
            MultiPartResDTO fileInfo = fileService.checkFile(file);
            return ResponseEntity.ok(fileInfo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //파일을 포함한 여러 파라미터 받아오기
    //첫 번째 방식
    @PostMapping("/upload/dto")
    public ResponseEntity<MultiPartResDTO> uploadFileDto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description) {
        try{
            log.info(description);
            MultiPartResDTO fileInfo = fileService.checkFile(file);
            return ResponseEntity.ok(fileInfo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //두 번째 방식
    @PostMapping("/upload/dto2")
    public ResponseEntity<MultiPartResDTO> uploadFileDto2(@ModelAttribute DTOMultipartReqDTO dtoMultipartReqDTO) {
        try{
            log.info(dtoMultipartReqDTO.toString());
            MultipartFile file = dtoMultipartReqDTO.getFile();
            MultiPartResDTO fileInfo = fileService.checkFile(file);
            return ResponseEntity.ok(fileInfo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //세 번째 방식
    //Content-Type 설정
    @PostMapping(value = "/upload/dto3", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MultiPartResDTO> uploadFileDto3(
            @RequestPart("file") MultipartFile file,
            @RequestPart("description")DTOMetadataReqDTO dto) {
        try{
            log.info(dto.toString());
            MultiPartResDTO fileInfo = fileService.checkFile(file);
            return ResponseEntity.ok(fileInfo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
