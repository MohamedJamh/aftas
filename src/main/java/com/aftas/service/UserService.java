package com.aftas.service;

import com.aftas.domain.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDetailsService userDetailsService();
    UserDetails getUserIfExitOrThrowException(String email);

    List<User> getAllUsers();

    User getUserProfile();
}
