package com.example.jpa.service;

import com.example.jpa.dto.CardDto;
import com.example.jpa.dto.ResponseDto;
import com.example.jpa.dto.SimpleCRUD;
import com.example.jpa.model.Card;
import com.example.jpa.repository.CardRepository;
import com.example.jpa.service.mapper.CardMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public record CardService(CardMapper cardMapper, CardRepository cardRepository, UserService userService) implements SimpleCRUD<Integer, CardDto> {

    @Override
    public ResponseDto<CardDto> create(CardDto dto) {
        if (this.userService.get(dto.getUserId()).getData() == null){
            return ResponseDto.<CardDto>builder()
                    .code(-1)
                    .messege("User is not found")
                    .build();
        }
        try {
            Card card = this.cardMapper.toEntity(dto);
            card.setCreateAt(LocalDateTime.now());
            this.cardRepository.save(card);
            return ResponseDto.<CardDto>builder()
                    .success(true)
                    .messege("OK")
                    .data(this.cardMapper.toDto(card))
                    .build();
        }catch (Exception e){
            return ResponseDto.<CardDto>builder()
                    .messege(String.format("Card while saving error %s", e.getMessage()))
                    .code(-2)
                    .build();
        }
    }

    @Override
    public ResponseDto<CardDto> get(Integer entityId) {
        Optional<Card> optional = this.cardRepository.findByCardIdAndDeleteAtIsNull(entityId);
        if (optional.isEmpty()){
            return ResponseDto.<CardDto>builder()
                    .code(-1)
                    .messege("Card is not found")
                    .build();
        }
        return ResponseDto.<CardDto>builder()
                .success(true)
                .messege("OK")
                .data(this.cardMapper.toDto(optional.get()))
                .build();
    }

    @Override
    public ResponseDto<CardDto> update(Integer entityId, CardDto dto) {
        try {
            return null;
        }catch (Exception e){
            return ResponseDto.<CardDto>builder()
                    .messege(String.format("Card while saving error %s", e.getMessage()))
                    .code(-2)
                    .build();
        }
    }

    @Override
    public ResponseDto<CardDto> delete(Integer entityId) {
        return null;
    }

}
