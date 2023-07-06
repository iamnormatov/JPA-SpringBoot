package com.example.jpa.service.validation;

import com.example.jpa.dto.ErrorDto;
import com.example.jpa.dto.UserDto;
import com.example.jpa.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserValidation {
//    private final UserService userService;
    private final UserRepository userRepository;

    public List<ErrorDto> validate(UserDto dto) {
        List<ErrorDto> list = new ArrayList<>();
        if (StringUtils.isBlank(dto.getName())) {
            list.add(new ErrorDto("name", "Name cannot be null or empty"));
        }
        if (StringUtils.isBlank(dto.getSurname())) {
            list.add(new ErrorDto("surname", "Surname cannot be null or empty"));
        }
        if (StringUtils.isBlank(dto.getEmail())) {
            list.add(new ErrorDto("email", "Email cannot be null or empty"));
        } else if (this.userRepository.existsByEmail(dto.getEmail())) {
            list.add(new ErrorDto("email", String.format("This email: %s already exist!", dto.getEmail())));
        }

        if (checkEmailPattern(dto.getEmail())) {
            list.add(new ErrorDto("email", String.format("Given %s The email was not valid", dto.getEmail())));
        }

        return list;
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
