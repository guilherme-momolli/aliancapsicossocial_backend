package br.com.aliancapsicossocial.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileResponseDTO {
    private String fileName;
    private String fileKey;
    private String fileUrl;
    private Long size;
    private String contentType;
}
