package ru.zed.app.controller.register;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.zed.app.model.Mapping.Mapping;
import ru.zed.app.model.entity.User.UserEntity;
import ru.zed.app.model.entity.User.user_info.Roles;
import ru.zed.app.model.entity.User.user_info.UserDTO;
import ru.zed.app.service.LWUserService;

import java.io.IOException;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/LinkWorld/auth/")
@Slf4j
public class RegisterNewUser {
    private final LWUserService userService;
    private final PasswordEncoder encoder;
    private static final String REDIRECT_TO_LOGIN_PAGE = "http://localhost:8080/LinkWorld/auth/login";

    @PostMapping("register")
    public String createUser(@Valid @ModelAttribute UserDTO dto,
                                                 BindingResult bindingResult,
                                                 @RequestParam MultipartFile userImage,
                                                 Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDTO", dto);
            return "register/register";
        }

        UserEntity entity = Mapping.toEntity(dto);
        entity.setRoles(List.of(Roles.USER));
        entity.setPassword(encoder.encode(entity.getPassword()));
        userService.saveUserToDatabase(entity, userImage);
        log.info("Saved user: {}", entity.getUsername());

        return "auth/login";
    }
}
