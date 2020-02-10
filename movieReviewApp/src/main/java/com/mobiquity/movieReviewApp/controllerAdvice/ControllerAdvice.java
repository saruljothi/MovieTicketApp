package com.mobiquity.movieReviewApp.controllerAdvice;

import com.mobiquity.movieReviewApp.exception.UserException;
import com.mobiquity.movieReviewApp.model.ResponseMovieApp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(value = UserException.class)
  public ResponseEntity<Object> userException(UserException ex) {
    return new ResponseEntity<Object>(new ResponseMovieApp(HttpStatus.FORBIDDEN.getReasonPhrase()),
        HttpStatus.FORBIDDEN);
  }

}
