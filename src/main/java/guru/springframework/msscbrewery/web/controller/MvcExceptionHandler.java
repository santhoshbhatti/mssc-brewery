package guru.springframework.msscbrewery.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> handleValidationErros(ConstraintViolationException e){
        List<String> errors = e.getConstraintViolations().stream()
                .map(cv -> cv.getPropertyPath() +":" +cv.getMessage())
                .collect(Collectors.toList());
        return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);


    }
    @ExceptionHandler(BindException.class)
    public ResponseEntity handleBindException(BindException e){
        return new ResponseEntity(e.getAllErrors(),HttpStatus.BAD_REQUEST);
    }
}
