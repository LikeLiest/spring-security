package ru.zed.app.Model.User;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
@Valid
public class UserDTO {
    @Column(unique = true)
    @NotEmpty(message = "Введите логин")
    @Size(message = "Логин должен содержать от 3 до 20 символов", max = 20, min = 3)
    private String username;

    @NotEmpty(message = "Введите пароль")
    @Size(max = 20, min = 3, message = "Пароль должен быть от 3 до 20 символов")
    private String password;

    @Column(unique = true)
    @Email(message = "Введите корректный email")
    private String email;

    private String city;
    private String country;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Pattern(regexp = "^(Male|Female)$", message = "Пол должен быть 'Male', 'Female'")
    private String sex;

    @Min(value = 0, message = "Возраст не может быть отрицательным")
    private int age;
}
