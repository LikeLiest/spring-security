package ru.zed.app.controller.account.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.zed.app.model.entity.User.UserEntity;
import ru.zed.app.model.entity.User.user_info.Roles;
import ru.zed.app.service.LWUserService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/LinkWorld")
@Slf4j
public class SetAuthority {
    private final LWUserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/setAuthority")
    public ResponseEntity<?> setUserRole(@RequestParam Long id,
                                         @RequestParam String role) {
        if ("ADMIN".equalsIgnoreCase(role)) {
            setAuthority(Roles.ADMIN, id);
        } else if ("USER".equalsIgnoreCase(role)) {
            setAuthority(Roles.USER, id);
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "http://localhost:8080/LinkWorld/admin")
                .build();
    }

    private void setAuthority(Roles role, Long id) {
        Optional<UserEntity> user = this.userService.findUserByID(id);
        if (user.isPresent()) {
            UserEntity entity = user.get();
            userService.updateUserRole(entity, role);
            log.info("Устанавливаем пользователю роль: " + role);
        }
    }
}
