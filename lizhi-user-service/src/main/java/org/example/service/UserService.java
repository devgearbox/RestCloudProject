package org.example.service;


import org.example.entity.UserEntity;

import org.example.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserEntity loginWithRole(String username, String password) {
        return (UserEntity) userRepository.findByUsername(username)
                .filter(user -> {
                    if (user.getPassword().startsWith("$2a$")) {
                        return passwordEncoder.matches(password, user.getPassword());
                    } else {
                        boolean matches = user.getPassword().equals(password);
                        if (matches) {
                            user.setPassword(passwordEncoder.encode(password));
                            userRepository.save(user);
                        }
                        return matches;
                    }
                })
                .orElse(null);
    }

    public boolean register(UserEntity user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
}