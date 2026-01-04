package org.example.service;


import dto.UserDTO;
import org.example.converte.UserConverter;
import org.example.entity.UserEntity;

import org.example.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import request.LoginRequest;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;

    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * 登录方法（返回UserDTO，不返回Entity）
     */
    public UserDTO login(LoginRequest request) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(request.getUsername());

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            // 验证密码
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                // 转换为DTO返回
                return userConverter.toDTO(user);
            }

            // 如果密码是明文，先加密再验证（兼容旧数据）
            if (isPlainPassword(user.getPassword())) {
                if (user.getPassword().equals(request.getPassword())) {
                    // 将明文密码加密保存
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    userRepository.save(user);
                    return userConverter.toDTO(user);
                }
            }
        }

        return null; // 登录失败
    }

    /**
     * 判断密码是否为明文
     */
    private boolean isPlainPassword(String password) {
        // BCrypt密码通常以$2a$开头，长度60
        return !password.startsWith("$2a$") || password.length() != 60;
    }

    /**
     * 注册方法
     */
    public boolean register(UserEntity user) {
        // 检查用户名是否已存在
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return false;
        }

        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    /**
     * 根据ID获取用户（返回DTO）
     */
    public UserDTO getUserById(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        return userOptional.map(userConverter::toDTO).orElse(null);
    }
}