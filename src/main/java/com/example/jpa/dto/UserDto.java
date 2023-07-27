package com.example.jpa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Integer userId;
    @NotBlank(message = "Name cannot be null or empty")
    @Size(min = 3, max = 10, message = "Error firstname")
    private String name;
//    @NotBlank(message = "Surname cannot be null or empty")
    private String surname;
//    @Min(value = 18, message = "Users age cannot be minimum 18")
//    @Max(value = 70, message = "Users age cannot be maximum 70")
    private Integer age;
//    @Email(message = "Email is error")
    private String email;
    private String password;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;
}
