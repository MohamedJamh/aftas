package com.auth0.service.impl;

import com.auth0.domain.dto.response.auth.JwtAuthenticationResponseDto;
import com.auth0.domain.entity.User;
import com.auth0.exception.customexceptions.BadRequestException;
import com.auth0.exception.customexceptions.ValidationException;
import com.auth0.repository.RoleRepository;
import com.auth0.repository.UserRepository;
import com.auth0.service.AuthenticationService;
import com.auth0.service.JwtService;
import com.auth0.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
                    List.of(
                            ErrorMessage.builder()
                                    .field("email")
                                    .message("Email already exists")
                                    .build()
                    )
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
