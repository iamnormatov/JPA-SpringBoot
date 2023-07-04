package com.example.jpa.service.mapper;

import com.example.jpa.dto.UserDto;
import com.example.jpa.model.User;
import org.mapstruct.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Lazy
    protected CardMapper cardMapper;

    @Mapping(target = "card", ignore = true)
    public abstract User toEntity(UserDto userDto);

    public abstract UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateFromUser(UserDto userDto, @MappingTarget User user);

//    public User toEntity(UserDto userDto){
//        User user = new User();
//        user.setName(userDto.getName());
//        user.setSurname(userDto.getSurname());
//        user.setAge(userDto.getAge());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        return user;
//    }
//
//    public UserDto toDto(User user){
//        return UserDto.builder()
//                .userId(user.getUserId())
//                .name(user.getName())
//                .surname(user.getSurname())
//                .age(user.getAge())
//                .email(user.getEmail())
//                .password(user.getPassword())
//                .createAt(user.getCreateAt())
//                .updateAt(user.getUpdateAt())
//                .deleteAt(user.getDeleteAt())
//                .build();
//    }
//
//    public UserDto toDtoWithCard(User user){
//        return UserDto.builder()
//                .userId(user.getUserId())
//                .name(user.getName())
//                .surname(user.getSurname())
//                .age(user.getAge())
//                .email(user.getEmail())
//                .password(user.getPassword())
//                .createAt(user.getCreateAt())
//                .updateAt(user.getUpdateAt())
//                .deleteAt(user.getDeleteAt())
//                .build();
//    }
//    public void updateFromUser(UserDto userDto, User user){
//        if (userDto == null){
//            return;
//        }
//        if (userDto.getName() != null){
//            user.setName(userDto.getName());
//        }
//        if (userDto.getSurname() != null){
//            user.setSurname(userDto.getSurname());
//        }
//        if (userDto.getAge() != null){
//            user.setAge(userDto.getAge());
//        }
//        if (userDto.getEmail() != null){
//            user.setEmail(userDto.getEmail());
//        }
//        if (userDto.getPassword() != null){
//            user.setPassword(userDto.getPassword());
//        }
//    }
}
