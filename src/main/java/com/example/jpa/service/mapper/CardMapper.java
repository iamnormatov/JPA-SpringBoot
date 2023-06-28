package com.example.jpa.service.mapper;

import com.example.jpa.dto.CardDto;
import com.example.jpa.model.Card;
import com.example.jpa.service.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
public abstract class CardMapper {

    @Autowired
    protected UserService userService;

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "deleteAt", ignore = true)
    public abstract Card toEntity(CardDto dto);

    public abstract CardDto toDto(Card card);

    @Mapping(target = "user", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateFromCard(CardDto dto, @MappingTarget Card card);

//
//    public Card toEntity(CardDto dto) {
//        Card card = new Card();
//        card.setCardName(dto.getCardName());
//        card.setCardNumber(dto.getCardNumber());
//        card.setAmount(dto.getAmount());
//        card.setType(dto.getType());
//        card.setUserId(dto.getUserId());
//        return card;
//    }
//
//    public CardDto toDto(Card card) {
//        return CardDto.builder()
//                .cardId(card.getCardId())
//                .cardName(card.getCardName())
//                .cardNumber(card.getCardNumber())
//                .amount(card.getAmount())
//                .type(card.getType())
//                .userId(card.getUserId())
//                .userDto(this.userService.get(card.getUserId()).getData())
//                .createAt(card.getCreateAt())
//                .updateAt(card.getUpdateAt())
//                .deleteAt(card.getDeleteAt())
//                .build();
//    }
//
//    public void updateFromCard(CardDto dto, Card card) {
//        return;
//    }
}
