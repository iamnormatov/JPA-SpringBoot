package com.example.jpa.service;

import com.example.jpa.dto.FileDto;
import com.example.jpa.dto.ResponseDto;
import com.example.jpa.model.FileModel;
import com.example.jpa.repository.FileRepository;
import com.example.jpa.service.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Executable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    public ResponseDto<FileDto> upload(MultipartFile file) {
        try {
            return ResponseDto.<FileDto>builder()
                    .message("OK")
                    .data(this.fileMapper.toDto(
                            this.fileRepository.save(
                                    FileModel.builder()
                                            .path(saveFile(file))
                                            .createdAt(LocalDateTime.now())
                                            .fileName(Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[0])
                                            .ext(Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1])
                                            .build())))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<FileDto>builder()
                    .code(-3)
                    .message("File while saving error message :: " + e.getMessage())
                    .build();
        }
    }

    public ResponseDto<FileDto> download(Integer fileId) {
        try {
            Optional<FileModel> optional = this.fileRepository.findByFileIdAndDeletedAtIsNull(fileId);
            if (optional.isEmpty()) {
                return ResponseDto.<FileDto>builder()
                        .message(String.format("File with file %d id is not found", fileId))
                        .code(-1)
                        .build();
            }
            FileDto dto = this.fileMapper.toDto(optional.get());
            dto.setData(Files.readAllBytes(Path.of(optional.get().getPath())));
            return ResponseDto.<FileDto>builder()
                    .success(true)
                    .message("OK")
                    .data(dto)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<FileDto>builder()
                    .code(-3)
                    .message("File while getting error message :: " + e.getMessage())
                    .build();
        }
    }

    public ResponseDto<FileDto> update(MultipartFile file, Integer fileId) {
        try {
            Optional<FileModel> optional = this.fileRepository.findByFileIdAndDeletedAtIsNull(fileId);
            if (optional.isEmpty()) {
                return ResponseDto.<FileDto>builder()
                        .message(String.format("File with file %d id is not found", fileId))
                        .code(-1)
                        .build();
            }
            FileModel fileModel = optional.get();
            this.fileMapper.updateFile(FileDto.builder()
                    .path(saveFile(file))
                    .updatedAt(LocalDateTime.now())
                    .fileName(Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[0])
                    .ext(Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1])
                    .build(), fileModel);
            return ResponseDto.<FileDto>builder()
                    .success(true)
                    .message("OK")
                    .data(this.fileMapper.toDto(fileModel))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<FileDto>builder()
                    .code(-3)
                    .message(String.format("File while updating error message :: " + e.getMessage()))
                    .build();
        }
    }

    public ResponseDto<FileDto> delete(Integer fileId) {
        try {
            Optional<FileModel> optional = this.fileRepository.findByFileIdAndDeletedAtIsNull(fileId);
            if (optional.isEmpty()) {
                return ResponseDto.<FileDto>builder()
                        .message(String.format("File with file %d id is not found", fileId))
                        .code(-1)
                        .build();
            }
            FileModel fileModel = optional.get();
            File file = new File(fileModel.getPath());
            if (file.exists()){
                file.delete();
                return ResponseDto.<FileDto>builder()
                        .success(true)
                        .message("OK")
                        .data(this.fileMapper.toDto(fileModel))
                        .build();
            }else {
                return ResponseDto.<FileDto>builder()
                        .code(-3)
                        .message(String.format("File with file path %s is not found", fileModel.getPath()))
                        .build();
            }
        }catch (Exception e){
            return ResponseDto.<FileDto>builder()
                    .code(-3)
                    .message(String.format("File wile deleting error message :: " + e.getMessage()))
                    .build();
        }
    }

    public String saveFile(MultipartFile file) throws IOException {
        String folder = String.format("%s/%s", "upload", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        File sFile = new File(folder);
        if (!sFile.exists()) {
            sFile.mkdirs();
        }
        String fileName = String.format("%s/%s", folder, UUID.randomUUID());
        Files.copy(file.getInputStream(), Path.of(fileName));
        return fileName;
    }

}
