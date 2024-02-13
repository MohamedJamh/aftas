package com.aftas.web.rest;

import com.aftas.domain.dto.response.user.UserResponseDto;
import com.aftas.domain.mapper.UserMapper;
import com.aftas.service.UserService;
import com.aftas.utils.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRest {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserRest(UserService userService,UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @GetMapping
    public ResponseEntity<Response<List<UserResponseDto>>> getAllUsers() {
        Response<List<UserResponseDto>> response = new Response<>();
        response.setResult(
                userService.getAllUsers().stream()
                        .map(userMapper::toDto)
                        .toList()
        );
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<Response<UserResponseDto>> getUserProfile() {
        Response<UserResponseDto> response = new Response<>();
        response.setMessage("User profile fetched successfully");
        response.setResult(userMapper.toDto(userService.getUserProfile()));
        return ResponseEntity.ok().body(response);
    }
}
