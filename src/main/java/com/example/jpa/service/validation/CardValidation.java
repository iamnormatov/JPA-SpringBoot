package com.example.jpa.service.validation;

import com.example.jpa.dto.CardDto;
import com.example.jpa.dto.ErrorDto;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CardValidation {

    public List<ErrorDto> validate(CardDto dto){
        List<ErrorDto> list = new ArrayList<>();
        if (StringUtils.isBlank(dto.getCardName())){
            list.add(new ErrorDto("CardName", "Card cannot be null or empty"));
        }
        if (StringUtils.isBlank(dto.getType())){
            list.add(new ErrorDto("type", "Type cannot be null or empty"));
        }
        return list;
    }
}
