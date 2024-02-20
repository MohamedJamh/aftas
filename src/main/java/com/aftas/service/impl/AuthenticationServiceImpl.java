package com.aftas.service.impl;


import com.aftas.domain.dto.response.auth.JwtAuthenticationResponseDto;
import com.aftas.domain.dto.response.jwt.RefreshTokenResponseDTO;
import com.aftas.domain.entities.RefreshToken;
import com.aftas.domain.entities.User;
import com.aftas.domain.mapper.UserMapper;
import com.aftas.exception.custom.BadRequestException;
import com.aftas.exception.custom.InValidRefreshTokenException;
import com.aftas.exception.custom.ValidationException;
import com.aftas.repository.RoleRepository;
import com.aftas.repository.UserRepository;
import com.aftas.service.AuthenticationService;
import com.aftas.service.JwtService;
import com.aftas.service.RefreshTokenService;
import com.aftas.service.UserService;
import com.aftas.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RefreshTokenService refreshTokenService;
    private UserService userService;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            @Qualifier("bcryptPasswordEncoder")
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            RoleRepository roleRepository,
            RefreshTokenService refreshTokenService,
            UserService userService,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
        this.userMapper = userMapper;
    }
    @Override
    @Transactional
    public JwtAuthenticationResponseDto signup(User user) throws ValidationException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userService.createUser(user);
        return JwtAuthenticationResponseDto.builder()
                .user(userMapper.toDto(user))
                .accessToken(jwtService.generateToken(user))
                .build();
    }

    @Override
    public JwtAuthenticationResponseDto signin(User user) throws BadRequestException {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        var optionalUser = userRepository.findByEmail(user.getEmail());
        if(optionalUser.isEmpty())
            throw new BadCredentialsException("Invalid email or password");
        return JwtAuthenticationResponseDto.builder()
                .user(userMapper.toDto(optionalUser.get()))
                .accessToken(jwtService.generateToken(user))
                .build();
    }

    @Override
    public String refreshToken(String refToken) throws InValidRefreshTokenException {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenService.findByToken(refToken);
        RefreshToken refreshToken = null;
        if (optionalRefreshToken.isEmpty())
            refreshTokenService.throwInValidRefreshTokenException("invalid refresh token.");
        else {
            refreshToken = optionalRefreshToken.get();
            if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())){
                refreshTokenService.delete(refreshToken);
                refreshTokenService.throwInValidRefreshTokenException("Refresh token was expired. Please make a new signin.");
            }
        }
        UserDetails userDetails = userService.getUserIfExitOrThrowException(refreshToken.getUser().getEmail());
        return jwtService.generateToken(userDetails);
    }
}
