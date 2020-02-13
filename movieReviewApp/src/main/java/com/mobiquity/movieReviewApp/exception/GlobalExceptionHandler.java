package com.mobiquity.movieReviewApp.exception;

import com.mobiquity.movieReviewApp.model.ResponseMovieApp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {UserException.class})
  protected ResponseEntity<Object> userException(UserException ue) {
    return new ResponseEntity<>(new ResponseMovieApp(ue.getLocalizedMessage()),
        HttpStatus.UNAUTHORIZED);
  }
}
