package com.example.jpa.dto;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private boolean success;
    private String messege;
    private Integer code;
    private T data;
    private List<ErrorDto> error;
}
