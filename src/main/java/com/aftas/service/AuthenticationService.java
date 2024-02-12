package com.aftas.service;


import com.aftas.domain.dto.response.auth.JwtAuthenticationResponseDto;
import com.aftas.domain.dto.response.jwt.RefreshTokenResponseDTO;
import com.aftas.domain.entities.User;
import com.aftas.exception.custom.BadRequestException;
import com.aftas.exception.custom.InValidRefreshTokenException;
import com.aftas.exception.custom.ValidationException;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    JwtAuthenticationResponseDto signup(User request) throws ValidationException;
    JwtAuthenticationResponseDto signin(User request) throws BadRequestException;
    String refreshToken(String refreshToken) throws InValidRefreshTokenException;
}
