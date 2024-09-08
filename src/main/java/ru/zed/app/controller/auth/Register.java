package ru.zed.app.controller.auth;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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
    private final RestTemplate template;
    private static final String LOGIN_URL = "http://localhost:8080/LinkWorld/auth/login";
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
            entity.setRoles(List.of(Roles.USER));
            entity.setPassword(encoder.encode(entity.getPassword()));
            userService.saveUserToDatabase(entity, userImage);
            log.info("Saved user: {}", entity.getUsername());

            if (encoder.matches(dto.getPassword(), entity.getPassword())) {
                log.info("Успешный вход");
                session.setAttribute("user", entity);
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, REDIRECT_TO_ACCOUNT_PAGE).build();
            } else {
                log.info("Не успешный вход");
                return ResponseEntity.badRequest().body("Ошибка при входе после регистрации.");
            }

        } catch (Exception e) {
            log.error("Ошибка при создании пользователя", e);
            return ResponseEntity.badRequest().body("Ошибка при создании пользователя: " + e.getMessage());
        }
    }
}
