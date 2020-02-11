package com.mobiquity.movieReviewApp.controller;

import com.mobiquity.movieReviewApp.model.Login;
import com.mobiquity.movieReviewApp.model.ResetPassword;
import com.mobiquity.movieReviewApp.model.ResponseMovieApp;
import com.mobiquity.movieReviewApp.model.UserProfile;
import com.mobiquity.movieReviewApp.service.LoginService;
import com.mobiquity.movieReviewApp.service.PasswordRecoverService;
import com.mobiquity.movieReviewApp.service.SignUpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  public UserController(SignUpService signUpService, PasswordRecoverService passwordRecoverService,
      LoginService loginService) {
    this.signUpService = signUpService;
    this.passwordRecoverService = passwordRecoverService;
    this.loginService = loginService;
  }

  @PostMapping("/signUp")
  public String signUp(@RequestBody UserProfile userProfile) {
    return signUpService.saveUser(userProfile);
  }

  @GetMapping("/activationLink")
  public String activateLink(@RequestParam String token) {
    return signUpService.registerAccount(token);
  }

  @PostMapping("/resetPassword")
  public String resetPassword(@RequestBody ResetPassword resetPassword) {
    return passwordRecoverService.resetPassword(resetPassword);
  }

  @GetMapping("/forgotPassword")
  public String forgotPassword(@RequestParam String emailId) {
    return passwordRecoverService.passwordActivationLink(emailId);
  }

  @PostMapping("/setNewPassword")
  public String setNewPassword(@RequestBody ResetPassword resetPassword) {
    return passwordRecoverService.UpdatePassword(resetPassword);
  }

  @GetMapping("/activationLinkForNewPassword")
  public String getEmailIdForActivationLink(@RequestParam String token) {
    return passwordRecoverService.getEmailIdForNewPassword(token);
  }


  /**
   * @param login Enter Registered Email and Password
   * @return whether login is Successful or Failed
   */
  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody Login login) {
    return new ResponseEntity<>(
        new ResponseMovieApp(loginService.checkLogin(login)),
        HttpStatus.OK);
  }

}