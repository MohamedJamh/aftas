package com.aftas.web.rest;

import com.aftas.domain.dto.request.auth.SigninRequestDto;
import com.aftas.domain.dto.request.jwt.RefreshTokenRequestDto;
import com.aftas.domain.dto.request.user.UserRequestDto;
import com.aftas.domain.dto.response.auth.JwtAuthenticationResponseDto;
import com.aftas.domain.dto.response.jwt.RefreshTokenResponseDTO;
import com.aftas.domain.entities.User;
import com.aftas.domain.mapper.UserMapper;
import com.aftas.exception.custom.BadRequestException;
import com.aftas.exception.custom.InValidRefreshTokenException;
import com.aftas.exception.custom.ValidationException;
import com.aftas.service.AuthenticationService;
import com.aftas.service.RefreshTokenService;
import com.aftas.utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthRest {
    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private UserMapper userMapper;

    public AuthRest(
            AuthenticationService authenticationService,
            RefreshTokenService refreshTokenService,
            UserMapper userMapper
    ) {
        this.authenticationService = authenticationService;
        this.refreshTokenService = refreshTokenService;
        this.userMapper = userMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<Response<JwtAuthenticationResponseDto>> signup(@RequestBody @Valid UserRequestDto userRequestDto) throws ValidationException {
        Response<JwtAuthenticationResponseDto> response = new Response<>();
        User user = userMapper.toUser(userRequestDto);
        JwtAuthenticationResponseDto jwtAuthenticationResponseDto = authenticationService.signup(user);
        jwtAuthenticationResponseDto.setRefreshToken(
                refreshTokenService.getOrCreateRefreshToken(user.getEmail())
                .getToken()
        );
        response.setResult(jwtAuthenticationResponseDto);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<Response<JwtAuthenticationResponseDto>> signin(@RequestBody @Valid SigninRequestDto signinRequestDto) throws BadRequestException {
        Response<JwtAuthenticationResponseDto> response = new Response<>();
        User user = User.builder()
                .email(signinRequestDto.getEmail())
                .password(signinRequestDto.getPassword())
                .build();
        JwtAuthenticationResponseDto jwtAuthenticationResponseDto =  authenticationService.signin(user);
        jwtAuthenticationResponseDto.setRefreshToken(
                refreshTokenService.getOrCreateRefreshToken(user.getEmail())
                        .getToken()
        );
        response.setResult(jwtAuthenticationResponseDto);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Response<RefreshTokenResponseDTO>> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDTO) throws InValidRefreshTokenException {
        Response<RefreshTokenResponseDTO> response = new Response<>();
        String refToken = authenticationService.refreshToken(refreshTokenRequestDTO.getRefreshToken());
        response.setMessage("Token refreshed successfully");
        response.setResult(
                RefreshTokenResponseDTO.builder()
                .accessToken(refToken)
                .build()
        );
        return ResponseEntity.ok().body(response);
    }
}
