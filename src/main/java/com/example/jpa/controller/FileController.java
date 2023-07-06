package com.example.jpa.controller;

import com.example.jpa.dto.FileDto;
import com.example.jpa.dto.ResponseDto;
import com.example.jpa.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "file")
public class FileController {

    private FileService fileService;
    @PostMapping(name = "/upload")
    public ResponseDto<FileDto> uploadFile(@RequestBody MultipartFile file){
        return this.fileService.uploadFile(file);
    }

    @GetMapping(name = "/download/{id}")
    public ResponseDto<FileDto> download(@RequestParam(name = "id") Integer fileId){
        return this.fileService.download(fileId);
    }

    @PutMapping(name = "/update/{id}")
    public ResponseDto<FileDto> update(@RequestBody MultipartFile file, @RequestParam(name = "id") Integer fileId){
        return this.fileService.update(file, fileId);
    }

    @DeleteMapping(name = "/delete/{id}")
    public ResponseDto<FileDto> delete(@RequestParam(name = "id") Integer fileId){
        return this.fileService.delete(fileId);
    }
}
