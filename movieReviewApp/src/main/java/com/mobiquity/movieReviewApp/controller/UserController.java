package com.mobiquity.movieReviewApp.controller;

import com.mobiquity.movieReviewApp.model.userManagement.ForgotPassword;
import com.mobiquity.movieReviewApp.model.userManagement.Login;
import com.mobiquity.movieReviewApp.model.userManagement.ResetPassword;
import com.mobiquity.movieReviewApp.model.userManagement.ResponseMovieApp;
import com.mobiquity.movieReviewApp.model.userManagement.UserInformation;
import com.mobiquity.movieReviewApp.service.LoginService;
import com.mobiquity.movieReviewApp.service.PasswordRecoverService;
import com.mobiquity.movieReviewApp.service.SignUpService;
import com.mobiquity.movieReviewApp.validation.UserValidator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class UserController {

  private SignUpService signUpService;
  private PasswordRecoverService passwordRecoverService;
  private LoginService loginService;
  private UserValidator userValidator;

  public UserController(SignUpService signUpService, PasswordRecoverService passwordRecoverService,
      UserValidator userValidator) {
    this.signUpService = signUpService;
    this.userValidator = userValidator;
    this.passwordRecoverService = passwordRecoverService;
  }

  @PostMapping("/signUp")
  public ResponseEntity<ResponseMovieApp> signUp(@RequestBody UserInformation userInformation,
      BindingResult bindingResult) {

    userValidator.validate(userInformation, bindingResult);

    if (bindingResult.hasErrors()) {
      List<String> issues = new ArrayList<>();
      List<ObjectError> errors = bindingResult.getAllErrors();
      for (ObjectError error : errors) {
        issues.add(error.getCode());
      }
      return new ResponseEntity<>(new ResponseMovieApp(issues),
          HttpStatus.FORBIDDEN);
    }
    return new ResponseEntity<>(new ResponseMovieApp(Arrays.asList(signUpService.saveUser(userInformation))),
          HttpStatus.OK);

  }

  @GetMapping("/activationLink")
  public ResponseEntity<ResponseMovieApp> activateLink(@RequestParam String token) {
    return new ResponseEntity<>(new ResponseMovieApp(Arrays.asList(signUpService.registerAccount(token))),
        HttpStatus.OK);
  }

  @PostMapping("/resetPassword")
  public ResponseEntity<Object> resetPassword(@RequestBody ResetPassword resetPassword) {
    return new ResponseEntity<>(
        new ResponseMovieApp(Arrays.asList(passwordRecoverService.resetPassword(resetPassword))), HttpStatus.OK);
  }

  @GetMapping("/forgotPassword")
  public ResponseEntity<Object> forgotPassword(@RequestParam String emailId) {
    return new ResponseEntity<>(
        new ResponseMovieApp(Arrays.asList(passwordRecoverService.passwordActivationLink(emailId))),
        HttpStatus.OK);
  }

  @PostMapping("/setNewPassword")
  public ResponseEntity<Object> setNewPassword(@RequestBody ForgotPassword forgotPassword) {
    return new ResponseEntity<>(
        new ResponseMovieApp(Arrays.asList(passwordRecoverService.UpdatePassword(forgotPassword))), HttpStatus.OK);
  }

/*  @GetMapping("/activationLinkForNewPassword")
  public ResponseEntity<Object> getEmailIdForActivationLink(@RequestParam String token)
  {
    return new ResponseEntity<>(new ResponseMovieApp(passwordRecoverService.getEmailIdForNewPassword(token)),HttpStatus.OK);
  }*/

  /**
   * @param login Enter Registered Email and Password
   * @return whether login is Successful or Failed
   */
  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody Login login) {
    return new ResponseEntity<>(
        new ResponseMovieApp(Arrays.asList(loginService.checkLogin(login))),
        HttpStatus.OK);
  }

}
