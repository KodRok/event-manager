package dev.sorokin.eventmanager.exception;

import lombok.extern.slf4j.Slf4j;
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
}