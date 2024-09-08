package ru.zed.app.controller.account.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.zed.app.Model.entity.User.UserEntity;
import ru.zed.app.Model.entity.User.user_info.Roles;
import ru.zed.app.service.LWUserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/LinkWorld")
public class AdminController {
    private final LWUserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("admin")
    public String adminPanel(Model model) {
        model.addAttribute("users", this.userService.findAllUsers());
        return "admin/panel";
    }
}
