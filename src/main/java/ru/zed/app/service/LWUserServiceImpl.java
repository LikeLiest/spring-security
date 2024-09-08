package ru.zed.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.zed.app.model.entity.User.UserEntity;
import ru.zed.app.model.entity.User.user_info.Roles;
import ru.zed.app.model.entity.User.user_info.UserImage;
import ru.zed.app.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LWUserServiceImpl implements LWUserService {
    private final UserRepository userRepository;

    @Override
    public Optional<UserEntity> findUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserEntity> findUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findUserByID(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return this.userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUserFromDatabase(String username) {
        this.userRepository.deleteByUsername(username);
    }

    @Transactional
    @Override
    public void deleteUserFromDatabase(Long id) {
        this.userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void saveUserToDatabase(UserEntity entity, MultipartFile file) {
        UserImage image;
        if (file.getSize() != 0) {
            image = toImageEntity(file);
            entity.addImageToOwner(image);
        }

        this.userRepository.save(entity);
    }

    @Transactional
    @Override
    public void updateUserRole(UserEntity entity, Roles role) {
        Optional<UserEntity> user = userRepository.findByUsername(entity.getUsername());
        if (user.isPresent()) {
            UserEntity updatedUser = user.get();
            updatedUser.setRoles(List.of(role));
        }
    }

    @Override
    public void deleteAllUsers() {
        this.userRepository.deleteAll();
    }

    private UserImage toImageEntity(MultipartFile file) {
        UserImage image = new UserImage();
        try {
            image.setName(file.getName());
            image.setOriginalFileName(file.getOriginalFilename());
            image.setContentType(file.getContentType());
            image.setSize(file.getSize());
            image.setBytes(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }
}
