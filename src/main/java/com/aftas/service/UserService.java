package com.aftas.service;

import com.aftas.domain.entities.User;
import com.aftas.exception.custom.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDetailsService userDetailsService();
    UserDetails getUserIfExitOrThrowException(String email);
    User getUserProfile();
    User createUser(User user) throws ValidationException;
    Page<User> getAllUsers(Integer pageNo, Integer pageSize);
    User getUserIfExistsById(Long memberId) throws ValidationException;
    User getUserIfExistsByNumber(Integer memberCode) throws ValidationException;
    Page<User> findByCriteria(String searchValue,Integer pageNo, Integer pageSize);
    Page<User> getDisabledUsers(Integer pageNo, Integer pageSize);
    User enableUser(Integer userNumber) throws ValidationException;
}
