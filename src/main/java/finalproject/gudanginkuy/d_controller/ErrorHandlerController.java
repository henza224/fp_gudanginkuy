package finalproject.gudanginkuy.d_controller;

import finalproject.gudanginkuy.utils.response.Res;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandlerController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleConstraintViolation(MethodArgumentNotValidException e) {
        return Res.renderJson(null, e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
