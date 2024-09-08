package ru.zed.app.model.Mapping;

import org.springframework.beans.BeanUtils;
import ru.zed.app.model.entity.User.user_info.UserDTO;
import ru.zed.app.model.entity.User.UserEntity;

public class Mapping {

    public static UserDTO fromEntity(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userEntity, userDTO);
        return userDTO;
    }

    public static UserEntity toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDTO, userEntity);
        return userEntity;
    }
}
