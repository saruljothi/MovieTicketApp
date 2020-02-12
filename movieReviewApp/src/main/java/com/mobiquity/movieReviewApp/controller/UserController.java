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
  private LoginService loginService;

  public UserController(SignUpService signUpService,PasswordRecoverService passwordRecoverService,LoginService loginService) {
    this.signUpService = signUpService;
    this.passwordRecoverService = passwordRecoverService;
    this.loginService = loginService;
  }

  @PostMapping("/signUp")
  public ResponseEntity<Object> signUp(@RequestBody UserInformation userInformation) {

    return new ResponseEntity<>(new ResponseMovieApp(signUpService.saveUser(userInformation)),HttpStatus.OK);
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
  public ResponseEntity<Object> setNewPassword(@RequestBody ForgotPassword forgotPassword){
    return new ResponseEntity<>(new ResponseMovieApp(passwordRecoverService.UpdatePassword(forgotPassword)),HttpStatus.OK);
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
        new ResponseMovieApp(loginService.checkLogin(login)),
        HttpStatus.OK);
  }

}
