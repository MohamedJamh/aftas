package com.aftas.service.impl;

import com.aftas.domain.entities.Role;
import com.aftas.domain.entities.User;
import com.aftas.exception.custom.ValidationException;
import com.aftas.repository.RoleRepository;
import com.aftas.repository.UserRepository;
import com.aftas.service.UserService;
import com.aftas.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Value("${users.default.password}")
    private String defaultPassword;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                //we used username as a variable name instead of email because of the spring security standard
                //since the username is anything unique to the user
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public UserDetails getUserIfExitOrThrowException(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User getUserProfile() {
        return  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public User createUser(User user) throws ValidationException {
        Optional<User> optionalUser = userRepository.findByIdentityNumber(user.getIdentityNumber());
        if(optionalUser.isPresent())
            throw new ValidationException(new ErrorMessage("User with identity number already exists"));
        roleRepository.findByName("MEMBER").ifPresent(memberRole -> user.setRoles(Set.of(memberRole)));
        user.setPassword(defaultPassword);
        user.setEmail(user.getLastName() + "." + user.getIdentityNumber() + "@aftas.com");
        Integer maxId = userRepository.getMaxId();
        if(maxId == null) maxId = 0;
        user.setAccessionDate(LocalDate.now());
        user.setNum( maxId + 1 + LocalDate.now().getYear());
        return userRepository.save(user);
    }

    @Override
    public Page<User> getAllUsers(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return userRepository.findAll(paging);
    }

    @Override
    public User getUserIfExistsById(Long userId) throws ValidationException {
        Optional<User> optionalMember = userRepository.findById(userId);
        if(optionalMember.isEmpty())
            throw new ValidationException(new ErrorMessage("Member not found"));
        return optionalMember.get();
    }

    @Override
    public User getUserIfExistsByNumber(Integer userCode) throws ValidationException {
        Optional<User> optionalMember = userRepository.findByNum(userCode);
        if(optionalMember.isEmpty())
            throw new ValidationException(new ErrorMessage("Member not found"));
        return optionalMember.get();
    }

    @Override
    public Page<User> findByCriteria(String searchValue, Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        try {
            Integer memberNum = Integer.valueOf(searchValue);
            return userRepository.findByNum(memberNum,paging);
        } catch (NumberFormatException e) {
            return userRepository.findByFirstNameOrLastName(searchValue, searchValue, paging);
        }
    }
}
