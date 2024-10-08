package ru.zed.app.service;

import org.springframework.web.multipart.MultipartFile;
import ru.zed.app.model.entity.User.UserEntity;
import ru.zed.app.model.entity.User.user_info.Roles;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface LWUserService {
    Optional<UserEntity> findUserByUsername(String username);
    Optional<UserEntity> findUserByEmail(String email);
    Optional<UserEntity> findUserByID(Long id);
    List<UserEntity> findAllUsers();

    void deleteUserFromDatabase(String username);
    void deleteUserFromDatabase(Long id);

    void saveUserToDatabase(UserEntity entity, MultipartFile image) throws IOException;

    void updateUserRole(UserEntity entity, Roles role);

    void deleteAllUsers();
}