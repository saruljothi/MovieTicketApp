package com.mobiquity.movieReviewApp.controller;

import com.mobiquity.movieReviewApp.model.ResetPassword;
import com.mobiquity.movieReviewApp.model.Success;
import com.mobiquity.movieReviewApp.model.UserProfile;
import com.mobiquity.movieReviewApp.service.PasswordRecoverService;
import com.mobiquity.movieReviewApp.service.SignUpService;
import org.springframework.web.bind.annotation.GetMapping;
import com.mobiquity.movieReviewApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  private UserService userService;

  public UserController(SignUpService signUpService,PasswordRecoverService passwordRecoverService,UserService userService) {
    this.signUpService = signUpService;
    this.passwordRecoverService = passwordRecoverService;
    this.userService = userService;
  }

  @PostMapping("/signUp")
  public String signUp(@RequestBody UserProfile userProfile) {
    return signUpService.saveUser(userProfile);
  }

  @PostMapping("/activationLink")
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
  public String setNewPassword(@RequestBody ResetPassword resetPassword){
    return passwordRecoverService.UpdatePassword(resetPassword);
  }

  @GetMapping("/activationLinkForNewPassword")
  public String getEmailIdForActivationLink(@RequestParam String token)
  {
    return passwordRecoverService.getEmailIdForNewPassword(token);
  }
  /**
   * @param userProfile Enter Registered Email and Password
   * @return whether login is Successful or Failed
   */
  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody UserProfile userProfile) {
    return new ResponseEntity<>(
        new Success(userService.checkLogin(userProfile)), HttpStatus.OK);
  }

}
