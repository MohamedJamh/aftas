package com.aftas.web.rest;

import com.aftas.domain.dto.request.user.SaveUserRequestDto;
import com.aftas.domain.dto.request.user.UserRequestDto;
import com.aftas.domain.dto.response.member.UserPageableDto;
import com.aftas.domain.dto.response.user.UserResponseDto;
import com.aftas.domain.entities.User;
import com.aftas.domain.mapper.UserMapper;
import com.aftas.exception.custom.ValidationException;
import com.aftas.service.UserService;
import com.aftas.utils.Response;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<Response<UserPageableDto>> getUsers(
            @RequestParam(value="pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(value="pageSize", required = false, defaultValue = "5") Integer pageSize
    ) {
        Response<UserPageableDto> response = new Response<>();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        Page<User> userPages = userService.getAllUsers(pageNo,pageSize);
        userPages.stream().map(userMapper::toDto).forEach(userResponseDtos::add);
        response.setMessage("User retrieved successfully");
        response.setResult(
                UserPageableDto.builder()
                        .users(userResponseDtos)
                        .totalPages(userPages.getTotalPages())
                        .currentPage(userPages.getNumber() + 1)
                        .totalElements(userPages.getTotalElements())
                        .build()
        );
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/search")
    public ResponseEntity<Response<UserPageableDto>> findByCriteria(
            @RequestParam(value="value") String searchValue,
            @RequestParam(value="pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(value="pageSize", required = false, defaultValue = "5") Integer pageSize
    ) {
        Response<UserPageableDto> response = new Response<>();
        Page<User> userPages = userService.findByCriteria(searchValue,pageNo,pageSize);
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        if(userPages.isEmpty())
            response.setMessage("No member found");
        else response.setMessage("User retrieved successfully");
        userPages.stream().map(userMapper::toDto).forEach(userResponseDtos::add);
        response.setResult(
                UserPageableDto.builder()
                        .users(userResponseDtos)
                        .totalPages(userPages.getTotalPages())
                        .currentPage(userPages.getNumber() + 1)
                        .totalElements(userPages.getTotalElements())
                        .build()
        );
        return ResponseEntity.ok().body(response);
    }


    @PostMapping
    public ResponseEntity<Response<UserResponseDto>> saveUser(@RequestBody @Valid SaveUserRequestDto userRequestDto) throws ValidationException {
        Response<UserResponseDto> response = new Response<>();
        User user = userMapper.toUser(userRequestDto);
        User saveUser = userService.createUser(user);
        response.setMessage("User created successfully");
        response.setResult(userMapper.toDto(saveUser));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/disabled")
    public ResponseEntity<Response<UserPageableDto>> getDisabledUsers(
            @RequestParam(value="pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(value="pageSize", required = false, defaultValue = "5") Integer pageSize
    ){
        Response<UserPageableDto> response = new Response<>();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        Page<User> userPages = userService.getDisabledUsers(pageNo,pageSize);
        userPages.stream().map(userMapper::toDto).forEach(userResponseDtos::add);
        response.setResult(
                UserPageableDto.builder()
                        .users(userResponseDtos)
                        .totalPages(userPages.getTotalPages())
                        .currentPage(userPages.getNumber() + 1)
                        .totalElements(userPages.getTotalElements())
                        .build()
        );
        response.setMessage("Disabled user retrieved successfully");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/{userNumber}/enable")
    public ResponseEntity<Response<UserResponseDto>> enableUser(@PathVariable Integer userNumber) throws ValidationException {
        Response<UserResponseDto> response = new Response<>();
        response.setResult(
                userMapper.toDto(userService.enableUser(userNumber))
        );
        response.setMessage("User enabled successfully");
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
