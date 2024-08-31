package ru.zed.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zed.app.Model.User.UserImage;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Long> {
}
