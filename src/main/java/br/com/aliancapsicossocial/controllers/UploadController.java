package br.com.aliancapsicossocial.controllers;

import br.com.aliancapsicossocial.dtos.FileResponseDTO;
import br.com.aliancapsicossocial.services.S3StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Tag(name = "Upload", description = "Endpoints para gestão de arquivos")
public class UploadController {

    private final S3StorageService s3StorageService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @Operation(summary = "Realiza o upload de múltiplos arquivos para uma pasta específica")
    public ResponseEntity<List<FileResponseDTO>> uploadMultipleFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam(value = "folder", defaultValue = "geral") String folder) throws IOException {

        List<FileResponseDTO> responses = new ArrayList<>();

        for (MultipartFile file : files) {
            String key = s3StorageService.uploadFile(folder, file);
            String url = s3StorageService.getFileUrl(key);

            responses.add(FileResponseDTO.builder()
                    .fileName(file.getOriginalFilename())
                    .fileKey(key)
                    .fileUrl(url)
                    .size(file.getSize())
                    .contentType(file.getContentType())
                    .build());
        }

        return ResponseEntity.ok(responses);
    }
}
