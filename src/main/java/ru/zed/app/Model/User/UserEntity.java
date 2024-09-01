package ru.zed.app.Model.User;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Valid
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "Введите логин")
    @Size(message = "Логин должен содержать от 3 до 20 символов", max = 20, min = 3)
    private String username;

    @NotEmpty(message = "Введите пароль")
    @Size(max = 100, min = 3, message = "Пароль слишком длинный")
    private String password;

    @Column(unique = true)
    @Email(message = "Введите корректный email")
    private String email;

    private String city;
    private String country;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Roles.class)
    @Enumerated(EnumType.STRING)
    private List<Roles> roles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Language language;

    @Pattern(regexp = "^(Male|Female)$", message = "Пол должен быть 'Male', 'Female'")
    private String sex;

    @Min(value = 0, message = "Возраст не может быть отрицательным")
    private int age;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_image_id")
    private UserImage userImage;

    public void addImageToOwner(UserImage image) {
        image.setUserEntity(this);
        this.userImage  = image;
    }
}
