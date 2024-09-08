package ru.zed.app.model.entity.User.user_info;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import ru.zed.app.model.entity.User.UserEntity;

import java.util.Arrays;

@Data
@Entity
public class UserImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String originalFileName;
    private Long size;
    private String contentType;
    private byte[] bytes;

    @Override
    public String toString() {
        return "UserImage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", size=" + size +
                ", contentType='" + contentType + '\'' +
                ", bytes=" + Arrays.toString(bytes) +
                '}';
    }

    @JsonBackReference("user-image")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_entity_id")
    private UserEntity userEntity;
}
