package ru.zed.app.controller.account;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.zed.app.Model.Mapping.Mapping;
import ru.zed.app.Model.User.UserDTO;
import ru.zed.app.Model.User.UserEntity;
import ru.zed.app.service.LWUserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/LinkWorld")
public class AccountController{
    private final LWUserService userService;

    private static final String REDIRECT_TO_ACCOUNT_PAGE = "/LinkWorld/account";

    @GetMapping("account")
    public String accountPage(Model model) {
        model.addAttribute("users", this.userService.findAllUsers());
        return "account";
    }

    @GetMapping("createUser")
    public String accountPage() {
        return "createUser";
    }

    @PostMapping("createUser")
    public ResponseEntity<?> createUser(@Valid @ModelAttribute UserDTO dto, @RequestParam MultipartFile userImage) {
        try {
            UserEntity entity = Mapping.toEntity(dto);
            userService.saveUserToDatabase(entity, userImage);
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, REDIRECT_TO_ACCOUNT_PAGE).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при создании пользователя" + e.getMessage());
        }
    }
}