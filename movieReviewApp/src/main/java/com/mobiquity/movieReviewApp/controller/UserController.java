package com.mobiquity.movieReviewApp.controller;

import com.mobiquity.movieReviewApp.model.ResetPassword;
import com.mobiquity.movieReviewApp.model.UserProfile;
import com.mobiquity.movieReviewApp.service.SignUpService;
import com.mobiquity.movieReviewApp.validation.UserValidator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private SignUpService signUpService;

  @Autowired
  private UserValidator userValidator;

  /*
  public UserController(SignUpService signUpService) {
    this.signUpService = signUpService;
  }

   */

  @PostMapping("/signUp")
  public String signUp(@RequestBody UserProfile userProfile, BindingResult bindingResult) {
    userValidator.validate(userProfile, bindingResult);

    if(bindingResult.hasErrors()){
      String issue = "";
      List<ObjectError> errors = bindingResult.getAllErrors();
      for(ObjectError error : errors){
        issue += error.getCode() + "\n";
      }
      return issue;
    }

    return signUpService.saveUser(userProfile);
  }

  @PostMapping("/activatelink")
  public String activateLink(@RequestParam String token) {
    return signUpService.registerAccount(token);
  }

  @PostMapping("/resetPassword")
  public String resetPassword(@RequestBody ResetPassword resetPassword) {
    return signUpService.resetPassword(resetPassword);
  }

  @GetMapping("/forgotPassword")
  public String forgotPassword(@RequestParam String emailId) {
    return signUpService.passwordActivationLink(emailId);
  }

  @PostMapping("/setNewPassword")
  public String setNewPassword(@RequestBody ResetPassword resetPassword){
    return signUpService.UpdatePassword(resetPassword);
  }


}
