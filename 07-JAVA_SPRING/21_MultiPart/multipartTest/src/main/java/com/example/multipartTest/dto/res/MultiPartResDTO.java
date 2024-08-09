package com.example.multipartTest.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
public class MultiPartResDTO {
    private String fileName;
    private long size;

    @Builder
    public MultiPartResDTO(String fileName, long size) {
        this.fileName = fileName;
        this.size = size;
    }
}
