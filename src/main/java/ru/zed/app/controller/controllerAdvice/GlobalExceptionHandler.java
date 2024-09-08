package ru.zed.app.controller.controllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

//@ControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseBody
//    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e) {
//        FieldError error = e.getBindingResult().getFieldError();
//
//        if (error != null) {
//            return new ResponseEntity<>(error.getDefaultMessage(), HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>("Произошла ошибка валидации", HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    @ResponseBody
//    public ResponseEntity<String> handHttpMessageNotReadableException(HttpMessageNotReadableException e) {
//        if (e.getCause() instanceof InvalidFormatException ife) {
//            String message = String.format("Некорректное значение для поля '%s' : '%s' не является допустимым значением.",
//                    ife.getPath().getFirst().getFieldName(), ife.getValue());
//            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>("Не удалось обработать запрос. Проверьте данные и попробуйте снова", HttpStatus.BAD_REQUEST);
//    }
//}

//@ControllerAdvice(basePackages = "ru/zed/app")
//public class GlobalExceptionHandler {

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<String> handleGeneralException(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла внутренняя ошибка сервера");
//    }
//}

