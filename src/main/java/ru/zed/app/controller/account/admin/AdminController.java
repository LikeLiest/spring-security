package ru.zed.app.controller.account.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.zed.app.service.LWUserService;

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
