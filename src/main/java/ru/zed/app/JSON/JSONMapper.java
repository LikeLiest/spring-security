package ru.zed.app.JSON;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor

// todo JSON данные перезаписываются каждый раз
public class JSONMapper {
    private final ObjectMapper objectMapper;

    public void appendToFile(Object newData, String filePath) {
        List<Object> existingData = readFromFile(filePath);

        if (existingData == null) {
            existingData = new ArrayList<>();
        }
        existingData.add(newData);

        writeToFile(existingData, filePath);
    }

    private void writeToFile(List<Object> data, String filePath) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
        } catch (IOException e) {
            log.error("Ошибка записи в файл ", e);
        }
    }

    private List<Object> readFromFile(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            return null;
        }

        try {
            return objectMapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            log.error("Ошибка записи в json-файл ", e);
            return null;
        }
    }
}
