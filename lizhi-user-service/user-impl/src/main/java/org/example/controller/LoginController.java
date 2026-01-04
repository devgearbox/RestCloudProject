package org.example.controller;


import dto.UserDTO;
import feign.UserFeignClient;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.*;
import request.LoginRequest;
import response.LoginResponse;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class LoginController implements UserFeignClient {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        UserDTO user = userService.login(request);

        if (user != null) {
            // 生成token（实际项目中应该使用JWT）
            String token = generateToken(user);
            return LoginResponse.success(token, user);
        }

        return LoginResponse.error("用户名或密码错误");
    }

    /**
     * 生成模拟Token（实际项目应该用JWT）
     */
    private String generateToken(UserDTO user) {
        return "Bearer_" + user.getId() + "_" + System.currentTimeMillis();
    }

    // 可以添加其他接口
    @GetMapping("/test")
    public String test() {
        return "用户服务运行正常！";
    }
}