package com.loginms.services;

import com.loginms.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService {
    UserDetailsService userDetailsService();
   Optional<User> findByEmail(String email);
}
