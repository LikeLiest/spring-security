package ru.zed.app.controller.account;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.zed.app.model.entity.User.user_info.UserImage;
import ru.zed.app.repository.UserImageRepository;

import java.io.ByteArrayInputStream;

@RestController
@RequiredArgsConstructor
public class RestImageController {
    private final UserImageRepository userImageRepository;

    @GetMapping("/api/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable Long id) {
        UserImage image = userImageRepository.findById(id).orElse(null);
        assert image != null;
        return ResponseEntity.ok()
                .header("fileName", image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }
}
