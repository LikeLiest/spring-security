package ru.zed.app.controller.register;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zed.app.model.entity.User.user_info.UserDTO;

@Controller
@RequiredArgsConstructor
@RequestMapping("/LinkWorld/auth/")
@Slf4j
public class RegisterController {
    @GetMapping("register")
    public String registerPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register/register";
    }
}