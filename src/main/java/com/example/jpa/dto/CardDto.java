package com.example.jpa.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {
    private Integer cardId;
    private String cardName;
    private Long cardNumber;
    private String type;
    private Double amount;

    private Integer userId;
    private UserDto userDto;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;
}
