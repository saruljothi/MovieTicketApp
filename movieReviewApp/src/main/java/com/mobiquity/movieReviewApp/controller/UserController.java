package com.mobiquity.movieReviewApp.controller;

import com.mobiquity.movieReviewApp.model.UserProfile;
import com.mobiquity.movieReviewApp.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signUp")
  public String signUp(@RequestBody UserProfile userProfile) {
   return userService.saveUser(userProfile);

  }

  @PostMapping("/activatelink")
  public String activateLink(@RequestParam String token){

    return userService.registerAccount(token);
  }

}
