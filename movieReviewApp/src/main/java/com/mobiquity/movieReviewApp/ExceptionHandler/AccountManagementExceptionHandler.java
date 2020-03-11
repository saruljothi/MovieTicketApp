package com.mobiquity.movieReviewApp.ExceptionHandler;

import com.mobiquity.movieReviewApp.domain.accountmanagement.controller.GreetingsController;
import com.mobiquity.movieReviewApp.domain.accountmanagement.controller.PasswordController;
import com.mobiquity.movieReviewApp.domain.accountmanagement.controller.UserRegistrationController;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.PasswordException;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.UserException;
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

  @ExceptionHandler(value = {UserException.class,PasswordException.class})
  protected ResponseEntity<Object> userException(Exception ue) {
    if (ue instanceof UserException) {
      return new ResponseEntity<>(
          new ResponseMovieApp(Collections.singletonList(ue.getLocalizedMessage())),
          HttpStatus.UNAUTHORIZED);
    } else if (ue instanceof PasswordException) {
      return new ResponseEntity<>(
          new ResponseMovieApp(Collections.singletonList(ue.getLocalizedMessage())),
          HttpStatus.FORBIDDEN);
    }else{
      return null;
    }
  }
}
