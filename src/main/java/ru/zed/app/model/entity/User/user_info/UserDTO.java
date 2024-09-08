package ru.zed.app.model.entity.User.user_info;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;


@Data
@Validated
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Size(message = "Логин должен содержать от 3 до 20 символов", max = 20, min = 3)
    private String username;

    @Size(max = 100, min = 3, message = "Пароль должен от 3 до 100 символов")
    private String password;

    @Column(unique = true)
    @NotBlank(message = "Введите почту")
    @Email(message = "Введите корректный email")
    private String email;

    @NotNull(message = "Укажите свой город")
    @NotBlank(message = "Введите город")
    private String city;

    @NotNull(message = "Укажите свою страну")
    @NotBlank(message = "Введите страну")
    private String country;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Roles.class)
    @Enumerated(EnumType.STRING)
    private List<Roles> roles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Language language;

    @Pattern(regexp = "^(Male|Female)$", message = "Пол должен быть 'Male', 'Female'")
    private String gender;

    @NotNull(message = "Укажите свой возраст")
    @Max(value = 100, message = "Возраст не может быть больше 100")
    @Min(value = 0, message = "Возраст не может быть отрицательным")
    private int age;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", roles=" + roles +
                ", language=" + language +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                '}';
    }
}
