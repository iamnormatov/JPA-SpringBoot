package com.example.jpa.user;


import com.example.jpa.dto.ResponseDto;
import com.example.jpa.dto.UserDto;
import com.example.jpa.model.User;
import com.example.jpa.repository.UserRepository;
import com.example.jpa.service.UserService;
import com.example.jpa.service.mapper.UserMapper;
import com.example.jpa.service.validation.UserValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestUser {
    private UserService userService;
    private UserMapper userMapper;
    private UserRepository userRepository;
    private UserValidation userValidation;

    @BeforeEach
    void initObject() {
        userMapper = mock(UserMapper.class);
        userRepository = mock(UserRepository.class);
        userValidation = mock(UserValidation.class);
        userService = new UserService(userMapper, userRepository, userValidation);
    }

    @Test
    void testSumma() {
        int result = this.userService.summaToNumber(3, 9);
        Assertions.assertEquals(12, result);
    }

    @Test
    void testCreatePositive() {
        when(userMapper.toEntity(any()))
                .thenReturn(User.builder()
                        .userId(1)
                        .name("Azizjon")
                        .surname("Normatov")
                        .build());

        when(userMapper.toDto(any()))
                .thenReturn(UserDto.builder()
                        .userId(1)
                        .name("Azizjon")
                        .surname("Normatov")
                        .build());

        ResponseDto<UserDto> responseDto = this.userService.create(any());

        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertTrue(responseDto.isSuccess());
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertNull(responseDto.getError());

        verify(this.userMapper, times(1)).toDto(any());
        verify(this.userRepository, times(1)).save(any());
    }

    @Test
    void testCreateException() {
        when(this.userService.create(any()))
                .thenThrow(RuntimeException.class);

        ResponseDto<UserDto> responseDto = this.userService.create(any());

        Assertions.assertNull(responseDto.getData());
        Assertions.assertEquals(-2, responseDto.getCode());
    }

    @Test
    void testGetPositive() {
        when(this.userRepository.getUserById(any()))
                .thenReturn(Optional.of(User.builder()
                        .userId(1)
                        .name("Azizjon")
                        .surname("Normatov")
                        .build())
                );

        when(this.userMapper.toDto(any()))
                .thenReturn(UserDto.builder()
                        .userId(2)
                        .name("Azizjon")
                        .surname("Normatov")
                        .build()
                );

        ResponseDto<UserDto> responseDto = this.userService.get(any());

        Assertions.assertTrue(responseDto.isSuccess());
        Assertions.assertEquals(0, responseDto.getCode());
        Assertions.assertNotNull(responseDto.getData());
        Assertions.assertEquals(responseDto.getData().getUserId(), 2);
    }

    @Test
    void testGetNegative() {
        when(this.userRepository.getUserById(any()))
                .thenReturn(Optional.empty());

        ResponseDto<UserDto> responseDto = this.userService.get(any());

        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertNull(responseDto.getData());
        Assertions.assertFalse(responseDto.isSuccess());
    }

    @Test
    void testUpdatePositive() {
        when(this.userRepository.getUserById(any()))
                .thenReturn(Optional.of(User.builder()
                                .userId(1)
                                .name("Azizjon")
                                .surname("Noramtov")
                                .build())
                );

        when(this.userMapper.updateFromUser(any(), any()))
                .thenReturn(User.builder()
                                .userId(1)
                                .name("Azizjon")
                                .surname("Normatov")
                                .build()
                );

        when(this.userMapper.toDto(any()))
                .thenReturn(UserDto.builder()
                                .userId(2)
                                .name("Azizjon")
                                .surname("Noramtov")
                                .build()
                );

        ResponseDto<UserDto> responseDto = this.userService.update(any(), UserDto.builder()
                                                                                   .userId(2)
                                                                                   .name("Azizjon")
                                                                                   .surname("Noramtov")
                                                                                   .build());

        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertTrue(responseDto.isSuccess());
        Assertions.assertEquals(responseDto.getMessage(), "OK");
        Assertions.assertNotNull(responseDto.getData());

        verify(this.userRepository, times(1)).getUserById(any());
        verify(this.userMapper, times(1)).toDto(any());
        verify(this.userRepository, times(1)).save(any());
    }

    @Test
    void testUpdateNegative() {
        when(this.userRepository.getUserById(any()))
                .thenReturn(Optional.empty());

        ResponseDto<UserDto> responseDto = this.userService.update(any(), UserDto.builder()
                                                                                  .userId(2)
                                                                                  .name("Azizjon")
                                                                                  .surname("Noramtov")
                                                                                  .build());

        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertFalse(responseDto.isSuccess());
        Assertions.assertEquals(responseDto.getMessage(), "User is not found");
    }

    @Test
    void testUpdateException() {
        when(this.userMapper.toDto(any()))
                .thenThrow(RuntimeException.class);

        ResponseDto<UserDto> responseDto = this.userService.update(any(), any());

        Assertions.assertFalse(responseDto.isSuccess());
        Assertions.assertEquals(responseDto.getCode(), -2);
    }

    @Test
    void testDeletePositive() {
        when(this.userMapper.toDto(any()))
                .thenReturn(UserDto.builder()
                        .userId(2)
                        .name("Azizjon")
                        .surname("Normatov")
                        .build()
                );

        when(this.userRepository.getUserById(any()))
                .thenReturn(Optional.of(User.builder()
                        .userId(1)
                        .name("Azizjon")
                        .surname("Normatov")
                        .build())
                );

        ResponseDto<UserDto> responseDto = this.userService.delete(any());

        Assertions.assertTrue(responseDto.isSuccess());
        Assertions.assertEquals(responseDto.getMessage(), "OK");
        Assertions.assertEquals(responseDto.getCode(), 0);
        Assertions.assertNotNull(responseDto.getData());

        verify(this.userMapper, times(1)).toDto(any());
        verify(this.userRepository, times(1)).getUserById(any());
        verify(this.userRepository, times(1)).save(any());
    }

    @Test
    void testDeleteNegative() {
        when(this.userRepository.getUserById(any()))
                .thenReturn(Optional.empty());

        ResponseDto<UserDto> responseDto = this.userService.delete(any());

        Assertions.assertEquals(responseDto.getCode(), -1);
        Assertions.assertEquals(responseDto.getMessage(), "User is not found");
        Assertions.assertNull(responseDto.getData());
    }

}
