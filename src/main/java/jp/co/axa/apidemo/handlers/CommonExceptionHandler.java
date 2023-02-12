package jp.co.axa.apidemo.handlers;

import jp.co.axa.apidemo.dto.responses.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {
    // Handle badly formatted request body (e.g. missing required fields) with error code 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        e.getMessage(),
                        LocalDateTime.now()
                ), HttpStatus.BAD_REQUEST);
    }

    // Handle all other exceptions with error code 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception occurred", e);
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        e.getMessage(),
                        LocalDateTime.now()
                ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
