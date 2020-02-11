package com.mobiquity.movieReviewApp.controller;

import com.mobiquity.movieReviewApp.model.ResetPassword;
import com.mobiquity.movieReviewApp.model.ResponseMovieApp;
import com.mobiquity.movieReviewApp.model.UserProfile;
import com.mobiquity.movieReviewApp.service.PasswordRecoverService;
import com.mobiquity.movieReviewApp.service.SignUpService;
import com.mobiquity.movieReviewApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1")
public class UserController {

  private SignUpService signUpService;
  private PasswordRecoverService passwordRecoverService;
  private UserService userService;

  public UserController(SignUpService signUpService,PasswordRecoverService passwordRecoverService,UserService userService) {
    this.signUpService = signUpService;
    this.passwordRecoverService = passwordRecoverService;
    this.userService = userService;
  }

  @PostMapping("/signUp")
  public ResponseEntity<Object> signUp(@RequestBody UserProfile userProfile) {

    return new ResponseEntity<>(new ResponseMovieApp(signUpService.saveUser(userProfile)),HttpStatus.OK);
  }

  @GetMapping("/activationLink")
  public ResponseEntity<Object> activateLink(@RequestParam String token) {
    return new ResponseEntity<>(new ResponseMovieApp(signUpService.registerAccount(token)),HttpStatus.OK);
  }

  @PostMapping("/resetPassword")
  public ResponseEntity<Object> resetPassword(@RequestBody ResetPassword resetPassword) {
    return new ResponseEntity<>(new ResponseMovieApp(passwordRecoverService.resetPassword(resetPassword)),HttpStatus.OK);
  }

  @GetMapping("/forgotPassword")
  public ResponseEntity<Object> forgotPassword(@RequestParam String emailId) {
    return new ResponseEntity<>(new ResponseMovieApp(passwordRecoverService.passwordActivationLink(emailId)),HttpStatus.OK);
  }

  @PostMapping("/setNewPassword")
  public ResponseEntity<Object> setNewPassword(@RequestBody ResetPassword resetPassword){
    return new ResponseEntity<>(new ResponseMovieApp(passwordRecoverService.UpdatePassword(resetPassword)),HttpStatus.OK);
  }

  @GetMapping("/activationLinkForNewPassword")
  public ResponseEntity<Object> getEmailIdForActivationLink(@RequestParam String token)
  {
    return new ResponseEntity<>(new ResponseMovieApp(passwordRecoverService.getEmailIdForNewPassword(token)),HttpStatus.OK);
  }
  /**
   * @param userProfile Enter Registered Email and Password
   * @return whether login is Successful or Failed
   */
  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody UserProfile userProfile) {
    return new ResponseEntity<>(new ResponseMovieApp(userService.checkLogin(userProfile)), HttpStatus.OK);
  }

}
