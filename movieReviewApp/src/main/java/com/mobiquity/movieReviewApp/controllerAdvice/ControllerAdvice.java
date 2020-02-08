package com.mobiquity.movieReviewApp.controllerAdvice;

import com.mobiquity.movieReviewApp.exception.LoginException;
import com.mobiquity.movieReviewApp.model.LoginFail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(value = LoginException.class)
  public ResponseEntity<Object> loginException(LoginException ex) {
    return new ResponseEntity<>(new LoginFail(HttpStatus.UNAUTHORIZED.value(), "Login Failed"),
        HttpStatus.UNAUTHORIZED);
  }

}
