package ru.zed.app.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.zed.app.Model.entity.User.user_info.Roles;
import ru.zed.app.Model.entity.User.UserEntity;
import ru.zed.app.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));
        userEntity.setRoles(List.of(Roles.ADMIN));
        log.info(userEntity.getRoles().toString());
        return new MyUserDetails(userEntity);
    }
}
