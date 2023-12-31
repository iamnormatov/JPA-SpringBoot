package com.example.jpa.controller;

import com.example.jpa.dto.ResponseDto;
import com.example.jpa.dto.SimpleCRUD;
import com.example.jpa.dto.UserDto;
import com.example.jpa.model.User;
import com.example.jpa.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "user")
public record UserController(UserService userService) implements SimpleCRUD<Integer, UserDto> {
    @Override
    @PostMapping(value = "/create")
    public ResponseDto<UserDto> create(@RequestBody @Valid UserDto dto) {
        return this.userService.create(dto);
    }

    @Override
    @GetMapping(value = "/get/{id}")
    public ResponseDto<UserDto> get(@PathVariable(value = "id") Integer entityId) {
        return this.userService.get(entityId);
    }

    @Override
    @PutMapping(value = "/update/{id}")
    public ResponseDto<UserDto> update(@PathVariable(value = "id") Integer entityId, @RequestBody UserDto dto) {
        return this.userService.update(entityId, dto);
    }

    @Override
    @DeleteMapping(value = "/delete/{id}")
    public ResponseDto<UserDto> delete(@PathVariable(value = "id") Integer entityId) {
        return this.userService.delete(entityId);
    }

    @GetMapping(value = "/get-all")
    public ResponseDto<List<UserDto>> getAllUsers(){
        return this.userService.getAllUsers();
    }

    @GetMapping(value = "/get-all-page")
    public ResponseDto<Page<UserDto>> fetAllPageUsers(@RequestParam Integer page, @RequestParam Integer size){
        return this.userService.getAllPageUsers(page, size);
    }

    @GetMapping(value = "/search-all-name")
    public ResponseDto<List<UserDto>> searchUserByName(@RequestParam String value){
        return this.userService.searchUserByName(value);
    }

    @GetMapping(value = "/search-by-user-page")
    public ResponseDto<Page<UserDto>> searchAllUserByValue(@RequestParam Integer page, @RequestParam Integer size, @RequestParam String value){
        return this.userService.searchAllUserByValue(page, size, value);
    }

    @GetMapping(value = "/basic-search")
    public ResponseDto<Page<UserDto>> getAllUserByBasicSearch(@RequestParam Map<String, String> params){
        return this.userService.getAllUserByBasicSearch(params);
    }
}
