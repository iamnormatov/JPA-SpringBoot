package com.example.jpa.service;

import com.example.jpa.dto.ErrorDto;
import com.example.jpa.dto.ResponseDto;
import com.example.jpa.dto.SimpleCRUD;
import com.example.jpa.dto.UserDto;
import com.example.jpa.model.User;
import com.example.jpa.repository.UserRepository;
import com.example.jpa.service.mapper.UserMapper;
import com.example.jpa.service.validation.UserValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public record UserService(UserMapper userMapper,
                          UserRepository userRepository,
                          UserValidation userValidation) implements SimpleCRUD<Integer, UserDto> {

    @Override
    public ResponseDto<UserDto> create(UserDto dto) {
        List<ErrorDto> errors = this.userValidation.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<UserDto>builder()
                    .code(-3)
                    .error(errors)
                    .build();
        }
        try {
            User user = this.userMapper.toEntity(dto);
            user.setCreateAt(LocalDateTime.now());
            this.userRepository.save(user);
            return ResponseDto.<UserDto>builder()
                    .success(true)
                    .message("OK")
                    .data(this.userMapper.toDto(user))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UserDto>builder()
                    .message(String.format("User while saving error %s", e.getMessage()))
                    .code(-2)
                    .build();

        }
    }

    @Override
    public ResponseDto<UserDto> get(Integer entityId) {
        return this.userRepository.getUserById(entityId)
                .map(user -> ResponseDto.<UserDto>builder()
                        .success(true)
                        .message("OK")
                        .data(this.userMapper.toDto(user))
                        .build())
                .orElse(ResponseDto.<UserDto>builder()
                        .message("User is not found!")
                        .code(-1)
                        .build());
    }

    @Override
    public ResponseDto<UserDto> update(Integer entityId, UserDto dto) {
        List<ErrorDto> errorDto = this.userValidation.validate(dto);
        if (errorDto.isEmpty()) {
            return ResponseDto.<UserDto>builder()
                    .code(-3)
                    .error(errorDto)
                    .build();
        }
        try {
            return this.userRepository.getUserById(entityId)
                    .map(user -> {
                        this.userMapper.updateFromUser(dto, user);
                        user.setUpdateAt(LocalDateTime.now());
                        this.userRepository.save(user);
                        return ResponseDto.<UserDto>builder()
                                .success(true)
                                .message("OK")
                                .data(this.userMapper.toDto(user))
                                .build();
                    })
                    .orElse(ResponseDto.<UserDto>builder()
                            .message("User is not found")
                            .code(-1)
                            .build()
                    );
        } catch (Exception e) {
            return ResponseDto.<UserDto>builder()
                    .message(String.format("User while saving error %s", e.getMessage()))
                    .code(-2)
                    .build();
        }
    }

    @Override
    public ResponseDto<UserDto> delete(Integer entityId) {
        return this.userRepository.getUserById(entityId)
                .map(user -> {
                    user.setDeleteAt(LocalDateTime.now());
                    this.userRepository.save(user);
                    return ResponseDto.<UserDto>builder()
                            .success(true)
                            .message("OK")
                            .data(this.userMapper.toDto(user))
                            .build();
                })
                .orElse(ResponseDto.<UserDto>builder()
                        .message("User is not found")
                        .code(-1)
                        .build()
                );
    }

    public ResponseDto<List<UserDto>> getAllUsers() {
        List<User> userList = this.userRepository.findAllByDeleteAtIsNull();
        if (userList.isEmpty()) {
            return ResponseDto.<List<UserDto>>builder()
                    .code(-1)
                    .message("Users are not found!")
                    .build();
        }
        return ResponseDto.<List<UserDto>>builder()
                .success(true)
                .message("OK")
                .data(userList.stream().map(this.userMapper::toDto).toList())
                .build();
    }


    public ResponseDto<Page<UserDto>> getAllPageUsers(Integer page, Integer size) {
        Page<User> userPage = this.userRepository.findAllByDeleteAtIsNull(PageRequest.of(page, size));
        if (userPage.isEmpty()) {
            return ResponseDto.<Page<UserDto>>builder()
                    .code(-1)
                    .message("Users are not found")
                    .build();
        }
        return ResponseDto.<Page<UserDto>>builder()
                .success(true)
                .message("OK")
                .data(userPage.map(this.userMapper::toDto))
                .build();
    }

    public ResponseDto<List<UserDto>> searchUserByName(String value) {
        List<User> userList = this.userRepository.searchUserByName(value);
        if (userList.isEmpty()) {
            return ResponseDto.<List<UserDto>>builder()
                    .code(-1)
                    .message("Users are not found")
                    .build();
        }
        return ResponseDto.<List<UserDto>>builder()
                .success(true)
                .message("OK")
                .data(userList.stream().map(this.userMapper::toDto).toList())
                .build();
    }

    public ResponseDto<Page<UserDto>> searchAllUserByValue(Integer page, Integer size, String value) {
        return Optional.ofNullable(this.userRepository.searchAllUserByValue(PageRequest.of(page, size), value))
                .map(users -> ResponseDto.<Page<UserDto>>builder()
                        .success(true)
                        .message("OK")
                        .data(users.map(this.userMapper::toDto))
                        .build()
                )
                .orElse(ResponseDto.<Page<UserDto>>builder()
                        .code(-1)
                        .message("Users are not found")
                        .build()
                );
    }

    public ResponseDto<Page<UserDto>> getAllUserByBasicSearch(Map<String, String> params) {
        int page = 0, size = 10;
        if (params.containsKey("page")) {
            page = Integer.parseInt(params.get("page"));
        }
        if (params.containsKey("size")) {
            size = Integer.parseInt(params.get("size"));
        }

        Page<User> users = this.userRepository.findAllUserByParams(
                params.get("id") == null ? null : Integer.parseInt(params.get("id")),
                params.get("n"), params.get("s"),
                params.get("e"), params.get("p"),
                params.get("a") == null ? null : Integer.parseInt(params.get("a")),
                PageRequest.of(page, size)
        );

        return ResponseDto.<Page<UserDto>>builder()
                .success(true)
                .message("OK")
                .data(users.map(this.userMapper::toDto))
                .build();
    }
}
