package com.example.jpa.service;

import com.example.jpa.dto.CardDto;
import com.example.jpa.dto.ErrorDto;
import com.example.jpa.dto.ResponseDto;
import com.example.jpa.dto.SimpleCRUD;
import com.example.jpa.model.Card;
import com.example.jpa.repository.CardRepository;
import com.example.jpa.service.mapper.CardMapper;
import com.example.jpa.service.validation.CardValidation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public record CardService(CardMapper cardMapper, CardRepository cardRepository, UserService userService, CardValidation cardValidation) implements SimpleCRUD<Integer, CardDto> {

    @Override
    public ResponseDto<CardDto> create(CardDto dto) {
        List<ErrorDto> error = this.cardValidation.validate(dto);
        if (!error.isEmpty()){
            return ResponseDto.<CardDto>builder()
                    .code(-3)
                    .error(error)
                    .build();
        }
        if (this.userService.get(dto.getUserId()).getData() == null){
            return ResponseDto.<CardDto>builder()
                    .code(-1)
                    .message("User is not found")
                    .build();
        }
        try {
            Card card = this.cardMapper.toEntity(dto);
            card.setCreateAt(LocalDateTime.now());
            this.cardRepository.save(card);
            return ResponseDto.<CardDto>builder()
                    .success(true)
                    .message("OK")
                    .data(this.cardMapper.toDto(card))
                    .build();
        }catch (Exception e){
            return ResponseDto.<CardDto>builder()
                    .message(String.format("Card while saving error %s", e.getMessage()))
                    .code(-2)
                    .build();
        }
    }

    @Override
    public ResponseDto<CardDto> get(Integer entityId) {
        return this.cardRepository.findByCardIdAndDeleteAtIsNull(entityId)
                .map(card -> ResponseDto.<CardDto>builder()
                        .success(true)
                        .message("OK")
                        .data(this.cardMapper.toDto(card))
                        .build())
                .orElse(ResponseDto.<CardDto>builder()
                        .code(-1)
                        .message("Card is not found")
                        .build()
                );
    }

    @Override
    public ResponseDto<CardDto> update(Integer entityId, CardDto dto) {
        List<ErrorDto> error = this.cardValidation.validate(dto);
        if (!error.isEmpty()){
            return ResponseDto.<CardDto>builder()
                    .code(-3)
                    .error(error)
                    .build();
        }
        try {
           return this.cardRepository.findByCardIdAndDeleteAtIsNull(entityId)
                    .map(card -> {
                        card.setUpdateAt(LocalDateTime.now());
                        this.cardMapper.updateFromCard(dto, card);
                        this.cardRepository.save(card);
                        return ResponseDto.<CardDto>builder()
                                .success(true)
                                .message("OK")
                                .data(this.cardMapper.toDto(card))
                                .build();
                    })
                    .orElse(ResponseDto.<CardDto>builder()
                        .message("Card is not found!")
                        .code(-1)
                        .build()
                    );
        }catch (Exception e){
            return ResponseDto.<CardDto>builder()
                    .message(String.format("Card while saving error %s", e.getMessage()))
                    .code(-2)
                    .build();
        }
    }

    @Override
    public ResponseDto<CardDto> delete(Integer entityId) {
       return this.cardRepository.findByCardIdAndDeleteAtIsNull(entityId)
                .map(card -> {
                    card.setDeleteAt(LocalDateTime.now());
                    this.cardRepository.save(card);
                    return ResponseDto.<CardDto>builder()
                            .success(true)
                            .message("OK")
                            .data(this.cardMapper.toDto(card))
                            .build();
                })
                .orElse(ResponseDto.<CardDto>builder()
                        .message("Card is not found!")
                        .code(-1)
                        .build()
                );
    }
}
