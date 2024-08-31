package ru.zed.app.controller.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.zed.app.Model.User.UserEntity;
import ru.zed.app.service.LWUserService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/LinkWorld")
public class DeleteUserController {
    private final LWUserService userService;

    @DeleteMapping("deleteUser/{id:\\d+}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        return deleteUserFromDatabase(id);
    }

    @DeleteMapping("deleteUser/{username:[a-zA-Z]+}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
        return deleteUserFromDatabase(username);
    }

    @DeleteMapping("deleteAllUsers")
    public ResponseEntity<String> deleteAllUsers() {
        this.userService.deleteAllUsers();
        if (this.userService.findAllUsers().isEmpty()) {
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
}
