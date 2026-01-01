package org.example.controller;


import org.example.entity.UserEntity;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/user")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        UserEntity user = userService.loginWithRole(username, password);
        if (user != null) {
            session.setAttribute("currentUser", user);
            session.setAttribute("currentUserId", user.getId());

            // 使用网关重定向地址
            if (user.getRole() == 1 || user.getRole() == 2) {
                return "redirect:http://localhost:8080/purchasework"; // 通过网关
            } else if (user.getRole() == 3) {
                return "redirect:http://localhost:8080/suppliers";
            }
        }

        redirectAttributes.addFlashAttribute("error", "登录失败");
        return "redirect:/user/login";
    }

    // 其他方法保持基本不变，注意路径前缀
    @PostMapping("/sendCode")
    @ResponseBody
    public Map<String, String> sendCode(@RequestParam String phone) {
        // 原逻辑，但注意需要修改为微服务架构的存储方式
        // 暂时先用本地存储，后续改为Redis
        Map<String, String> result = new HashMap<>();
        // ... 实现代码
        return result;
    }

    // 模板文件需要复制到 resources/templates/ 目录下
}