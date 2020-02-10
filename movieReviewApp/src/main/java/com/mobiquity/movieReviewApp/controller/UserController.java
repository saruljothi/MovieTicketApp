package com.mobiquity.movieReviewApp.controller;

import com.mobiquity.movieReviewApp.exception.LoginException;
import com.mobiquity.movieReviewApp.model.LoginResponse;
import com.mobiquity.movieReviewApp.model.UserProfile;
import com.mobiquity.movieReviewApp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class UserController {

  private LoginService loginService;

  @Autowired
  public UserController(LoginService loginService) {
    this.loginService = loginService;
  }


  /**
   * @param userProfile Enter Registered Email and Password
   * @return whether login is Successful or Failed
   */
 @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody UserProfile userProfile) {
    try {
      return new ResponseEntity<>(
          new LoginResponse(loginService.checkLogin(userProfile)),
          HttpStatus.OK);
    } catch (LoginException ex) {
      return new ResponseEntity<>(new LoginResponse("Login Failed"),
          HttpStatus.UNAUTHORIZED);
    }
  }

/*  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody UserProfile userProfile) {
    try {
      return new ResponseEntity<String>(userService.checkLogin(userProfile),
          HttpStatus.OK);
    } catch (LoginException ex) {
      return new ResponseEntity<String>(userService.checkLogin(userProfile),
          HttpStatus.UNAUTHORIZED);
    }
  }*/

}
