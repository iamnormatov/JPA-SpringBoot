package com.example.jpa.service;

import com.example.jpa.dto.FileDto;
import com.example.jpa.dto.ResponseDto;
import com.example.jpa.model.FileModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileService {

    public ResponseDto<FileDto> uploadFile(MultipartFile file){
        return null;
    }

    public ResponseDto<FileDto> download(Integer fileId){
        return null;
    }

    public ResponseDto<FileDto> update(MultipartFile file, Integer fileId){
        return null;
    }

    public ResponseDto<FileDto> delete(Integer fileId){
        return null;
    }

//    public String saveFile(MultipartFile file) {
//        String folder = String.format("%s/%s", "upload", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
//        FileModel sFile = new FileModel(folder);
//
//    }

}
