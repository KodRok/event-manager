package dev.sorokin.eventmanager.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageResponse> handleValidation(
            MethodArgumentNotValidException ex) {
        log.warn("Validation error occurred: {}", ex.getMessage());

        String detailedMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        var body = new ErrorMessageResponse(
                "Ошибка валидации",
                detailedMessage,
                LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleNotFound(
            LocationNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());

        var body = new ErrorMessageResponse(
                "Ресурс не найден",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageResponse> handleUnexpected(Exception ex) {
        log.error("Unexpected internal server error: ", ex);

        var body = new ErrorMessageResponse(
                "Внутренняя ошибка сервера",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorMessageResponse response = new ErrorMessageResponse(
                "Сущность не найдена",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        ErrorMessageResponse response = new ErrorMessageResponse(
                "Некорректный запрос",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorMessageResponse> handleAuthFailed(AuthenticationFailedException ex) {
        ErrorMessageResponse response = new ErrorMessageResponse(
                "Необходима аутентификация",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessageResponse> handleConstraintViolation(
            ConstraintViolationException ex) {
        log.warn("Constraint violation: {}", ex.getMessage());

        String detailedMessage = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining("; "));

        var body = new ErrorMessageResponse(
                "Ошибка валидации параметров",
                detailedMessage,
                LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessageResponse> handleDataIntegrity(
            DataIntegrityViolationException ex) {
        log.warn("Database constraint violation: {}", ex.getMessage());

        var body = new ErrorMessageResponse(
                "Конфликт данных",
                "Нарушение уникальности данных или целостности записи",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}