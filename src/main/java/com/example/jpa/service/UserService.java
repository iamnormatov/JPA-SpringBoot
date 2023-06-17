package com.example.jpa.service;

import com.example.jpa.dto.ResponseDto;
import com.example.jpa.dto.SimpleCRUD;
import com.example.jpa.dto.UserDto;
import com.example.jpa.model.User;
import com.example.jpa.repository.UserRepository;
import com.example.jpa.service.mapper.UserMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public record UserService(UserMapper userMapper, UserRepository userRepository) implements SimpleCRUD<Integer, UserDto> {
    @Override
    public ResponseDto<UserDto> create(UserDto dto) {
        if (this.userRepository.existsByEmail(dto.getEmail())){
            return ResponseDto.<UserDto>builder()
                    .code(-3)
                    .messege(String.format("This email: %s already exist!", dto.getEmail()))
                    .build();
        }
        if (checkEmailPattern(dto.getEmail())) {
            return ResponseDto.<UserDto>builder()
                    .code(-3)
                    .messege(String.format("Given %s The email was not valid", dto.getEmail()))
                    .build();
        }

        try {
            User user = this.userMapper.toEntity(dto);
            user.setCreateAt(LocalDateTime.now());
            this.userRepository.save(user);
            return ResponseDto.<UserDto>builder()
                    .success(true)
                    .messege("OK")
                    .data(this.userMapper.toDto(user))
                    .build();
        }catch (Exception e){
            return ResponseDto.<UserDto>builder()
                    .messege(String.format("User while saving error %s", e.getMessage()))
                    .code(-2)
                    .build();

        }
    }

    @Override
    public ResponseDto<UserDto> get(Integer entityId) {
        Optional<User> optional = this.userRepository.findByUserIdAndDeleteAtIsNull(entityId);
        if (optional.isEmpty()){
            return ResponseDto.<UserDto>builder()
                    .messege("User is not found!")
                    .code(-1)
                    .build();
        }
        return ResponseDto.<UserDto>builder()
                .success(true)
                .messege("OK")
                .data(this.userMapper.toDto(optional.get()))
                .build();
    }

    @Override
    public ResponseDto<UserDto> update(Integer entityId, UserDto dto) {
        Optional<User> optional = this.userRepository.findByUserIdAndDeleteAtIsNull(entityId);
        if (optional.isEmpty()){
            return ResponseDto.<UserDto>builder()
                    .messege("User is not found")
                    .code(-1)
                    .build();
        }
        if (checkEmailPattern(dto.getEmail())) {
            return ResponseDto.<UserDto>builder()
                    .code(-3)
                    .messege(String.format("Given %s The email was not valid", dto.getEmail()))
                    .build();
        }
        try {
            User user = optional.get();
            this.userMapper.updateFromUser(dto, user);
            user.setUpdateAt(LocalDateTime.now());
            this.userRepository.save(user);
            return ResponseDto.<UserDto>builder()
                    .success(true)
                    .messege("OK")
                    .data(this.userMapper.toDto(optional.get()))
                    .build();
        }catch (Exception e){
            return ResponseDto.<UserDto>builder()
                    .messege(String.format("User while saving error %s", e.getMessage()))
                    .code(-2)
                    .build();
        }
    }

    @Override
    public ResponseDto<UserDto> delete(Integer entityId) {
        Optional<User> optional = this.userRepository.findByUserIdAndDeleteAtIsNull(entityId);
        if (optional.isEmpty()){
            return ResponseDto.<UserDto>builder()
                    .messege("User is not found")
                    .code(-1)
                    .build();
        }
        User user = optional.get();
        user.setDeleteAt(LocalDateTime.now());
        this.userRepository.save(user);
        return ResponseDto.<UserDto>builder()
                .success(true)
                .messege("OK")
                .data(this.userMapper.toDto(user))
                .build();
    }

    private boolean checkEmailPattern(String email) {
        if(email != null){
            String[] array = email.split("@");
            if (array.length == 2) {
                return !array[1].equals("gmail.com");
            } else {
                return true;
            }
        }else {
            return false;
        }
    }
}
