package ru.zed.app.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/LinkWorld/auth")
public class LoginController {

    @GetMapping("login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("register")
    public String registerPage() {
        return "auth/register";
    }
}
