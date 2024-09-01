package ru.zed.app.controller.auth;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.zed.app.Model.entity.User.UserEntity;
import ru.zed.app.service.LWUserService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/LinkWorld/auth")
public class Auth {
    private final LWUserService userService;
    private final PasswordEncoder encoder;
    private static final Logger logger = LoggerFactory.getLogger(Auth.class);

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        Optional<UserEntity> userEntity = this.userService.findUserByUsername(username);

        if (userEntity.isPresent()) {
            UserEntity entity = userEntity.get();
            if (encoder.matches(password, entity.getPassword())) {
                logger.info("Пароли совпали");
                session.setAttribute("user", entity);
                return ResponseEntity.ok()
                        .header(HttpHeaders.LOCATION, "/LinkWorld/account").build();
            } else {
                logger.info("Пароли не совпали");
                return  ResponseEntity.badRequest()
                        .body("Пароли не совпали");
            }
        } else {
            logger.info("Ошибка");
            return ResponseEntity.badRequest()
                    .header(HttpHeaders.LOCATION, "LinkWorld:/login?error").build();
        }
    }
}
