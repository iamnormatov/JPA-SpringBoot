package com.example.jpa.controller;

import com.example.jpa.dto.FileDto;
import com.example.jpa.dto.ResponseDto;
import com.example.jpa.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "file")
public class FileController {
    private final FileService fileService;
    @PostMapping(value = "/upload")
    public ResponseDto<FileDto> upload(@RequestBody MultipartFile file) {
        return this.fileService.upload(file);
    }
    @GetMapping(value = "/download/{id}")
    public ResponseDto<FileDto> download(@PathVariable(value = "id") Integer fileId){
        return this.fileService.download(fileId);
    }
    @PutMapping(value = "/update/{id}")
    public ResponseDto<FileDto> update(@RequestBody MultipartFile file,
                                       @PathVariable(value = "id") Integer fileId){
        return this.fileService.update(file, fileId);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseDto<FileDto> delete(@PathVariable(value = "id") Integer fileId){
        return this.fileService.delete(fileId);
    }

}
