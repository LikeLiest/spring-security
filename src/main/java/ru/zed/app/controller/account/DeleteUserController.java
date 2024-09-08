package ru.zed.app.controller.account;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zed.app.Model.entity.User.UserEntity;
import ru.zed.app.service.LWUserService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/LinkWorld")
public class DeleteUserController {
    private final LWUserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("deleteUser/{id:\\d+}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id, HttpSession session) {
        return deleteUserFromDatabase(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("deleteUser/{username:[a-zA-Z]+}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username, HttpSession session) {
        return deleteUserFromDatabase(username);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("deleteAllUsers")
    public ResponseEntity<String> deleteAllUsers(HttpSession session) {
        this.userService.deleteAllUsers();

        if (this.userService.findAllUsers().isEmpty()) {
            session.invalidate();
            return ResponseEntity.ok("Все пользователи удалены");

        }
        return ResponseEntity.badRequest().body("Не удалось удалить всех пользователей");
    }

    private <S> ResponseEntity<String> deleteUserFromDatabase(S identifier) {
        Optional<UserEntity> entity;

        if (identifier instanceof Long) {
            userService.deleteUserFromDatabase((Long) identifier);
            entity = userService.findUserByID((Long) identifier);
        } else if (identifier instanceof String) {
            userService.deleteUserFromDatabase((String) identifier);
            entity = userService.findUserByUsername((String) identifier);
        } else {
            return ResponseEntity.badRequest().body("Неподдерживаемый тип идентификатора. Используйте Long или String");
        }

        return entity.map(userEntity -> ResponseEntity.ok("Пользователь успешно удален" + userEntity.getUsername()))
                .orElseGet(() -> ResponseEntity.ok("Пользователь успешно удален"));
    }

    private void clearCookies(HttpServletResponse response) {
        Cookie cookie = new Cookie("username", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setValue("");
        response.addCookie(cookie);
    }
}
