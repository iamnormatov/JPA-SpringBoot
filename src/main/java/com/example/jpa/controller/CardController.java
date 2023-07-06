package com.example.jpa.controller;

import com.example.jpa.dto.CardDto;
import com.example.jpa.dto.ResponseDto;
import com.example.jpa.dto.SimpleCRUD;
import com.example.jpa.service.CardService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "card")
public record CardController(CardService cardService) implements SimpleCRUD<Integer, CardDto> {
    @Override
    @PostMapping(value = "/create")
    public ResponseDto<CardDto> create(@RequestBody CardDto dto) {
        return this.cardService.create(dto);
    }


    @Override
    @GetMapping(value = "/get/{id}")
    public ResponseDto<CardDto> get(@PathVariable(name = "id") Integer entityId) {
        return this.cardService.get(entityId);
    }

    @Override
    @PutMapping(value = "/update/{id}")
    public ResponseDto<CardDto> update(@PathVariable(name = "id") Integer entityId, @RequestBody CardDto dto) {
        return this.cardService.update(entityId, dto);
    }

    @Override
    @DeleteMapping(value = "/delete/{id}")
    public ResponseDto<CardDto> delete(@PathVariable(name = "id") Integer entityId) {
        return this.cardService.delete(entityId);
    }

}
