package ru.zed.app.controller.auth;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.zed.app.Model.Mapping.Mapping;
import ru.zed.app.Model.entity.User.user_info.Roles;
import ru.zed.app.Model.entity.User.user_info.UserDTO;
import ru.zed.app.Model.entity.User.UserEntity;
import ru.zed.app.service.LWUserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/LinkWorld/auth/")
@Slf4j
public class Register {
    private final LWUserService userService;
    private final PasswordEncoder encoder;
    private static final String REDIRECT_TO_ACCOUNT_PAGE = "/LinkWorld/user/account";

    @GetMapping("register")
    public String registerPage() {
        return "auth/register";
    }

    @ResponseBody
    @PostMapping("register")
    public ResponseEntity<?> createUser(@Valid @ModelAttribute UserDTO dto,
                                        @RequestParam MultipartFile userImage,
                                        HttpSession session) {
        try {
            UserEntity entity = Mapping.toEntity(dto);
            entity.setRoles(List.of(Roles.ADMIN));
            entity.setPassword(encoder.encode(entity.getPassword()));;

            session.setAttribute("user", entity);

            log.info("saved user: {}", entity.getUsername());
            userService.saveUserToDatabase(entity, userImage);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, REDIRECT_TO_ACCOUNT_PAGE).build();
        } catch (Exception e) {
            log.error("Ошибка при создании пользователя", e);
            return ResponseEntity.badRequest()
                    .body("Ошибка при создании пользователя" + e.getMessage());
        }
    }
}
