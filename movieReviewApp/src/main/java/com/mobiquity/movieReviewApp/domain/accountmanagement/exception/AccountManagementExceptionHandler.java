package com.mobiquity.movieReviewApp.domain.accountmanagement.exception;

import com.mobiquity.movieReviewApp.domain.accountmanagement.controller.GreetingsController;
import com.mobiquity.movieReviewApp.domain.accountmanagement.controller.PasswordController;
import com.mobiquity.movieReviewApp.domain.accountmanagement.controller.UserRegistrationController;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.ResponseMovieApp;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {
        GreetingsController.class,
        PasswordController.class,
        UserRegistrationController.class
})
public class AccountManagementExceptionHandler {

  @ExceptionHandler(value = {UserException.class})
  protected ResponseEntity<Object> userException(UserException ue) {
    return new ResponseEntity<>(new ResponseMovieApp(Collections.singletonList(ue.getLocalizedMessage())),
        HttpStatus.UNAUTHORIZED);
  }
}
