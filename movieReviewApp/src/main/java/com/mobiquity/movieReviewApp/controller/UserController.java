package com.mobiquity.movieReviewApp.controller;

import com.mobiquity.movieReviewApp.exception.LoginException;
import com.mobiquity.movieReviewApp.model.Login;
import com.mobiquity.movieReviewApp.model.ResponseMovieApp;
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
   * @param login Enter Registered Email and Password
   * @return whether login is Successful or Failed
   */
  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody Login login) {
    try {
      return new ResponseEntity<>(
          new ResponseMovieApp(loginService.checkLogin(login)),
          HttpStatus.OK);
    } catch (LoginException ex) {
      return new ResponseEntity<>(new ResponseMovieApp("Login Failed"),
          HttpStatus.UNAUTHORIZED);
    }
  }

}
