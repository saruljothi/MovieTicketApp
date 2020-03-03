package com.mobiquity.movieReviewApp.domain.accountmanagement.exception;

import com.mobiquity.movieReviewApp.domain.content.model.ResponseMovieApp;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {UserException.class})
  protected ResponseEntity<Object> userException(UserException ue) {
    return new ResponseEntity<>(new ResponseMovieApp(Arrays.asList(ue.getLocalizedMessage())),
        HttpStatus.UNAUTHORIZED);
  }
}
