package com.aftas.service.impl;


import com.aftas.domain.dto.response.auth.JwtAuthenticationResponseDto;
import com.aftas.domain.entities.User;
import com.aftas.exception.custom.BadRequestException;
import com.aftas.exception.custom.ValidationException;
import com.aftas.repository.RoleRepository;
import com.aftas.repository.UserRepository;
import com.aftas.service.AuthenticationService;
import com.aftas.service.JwtService;
import com.aftas.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            @Qualifier("bcryptPasswordEncoder")
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    @Transactional
    public JwtAuthenticationResponseDto signup(User user) throws ValidationException {
        if(userRepository.findByEmail(user.getEmail()).isPresent())
            throw new ValidationException(
                ErrorMessage.builder()
                        .message("Email already exists")
                        .build()
            );
        roleRepository.findByName("USER").ifPresent(role -> user.setRoles(Set.of(role)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return JwtAuthenticationResponseDto.builder()
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
                .accessToken(jwtService.generateToken(user))
                .build();
    }
}
