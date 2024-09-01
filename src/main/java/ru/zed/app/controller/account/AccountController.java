package ru.zed.app.controller.account;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.zed.app.Model.User.UserEntity;
import ru.zed.app.service.LWUserService;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/LinkWorld")
public class AccountController{
    private final LWUserService userService;

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("account")
    public String accountPage(Model model, Principal principal) {
        Optional<UserEntity> user = this.userService.findUserByUsername(principal.getName());
        if (user.isPresent()) {
            UserEntity entity = user.get();
            model.addAttribute("user", entity);
        }
        return "account";
    }
}