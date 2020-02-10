package com.mobiquity.movieReviewApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.mobiquity.movieReviewApp.exception.UserException;
import com.mobiquity.movieReviewApp.model.Login;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@DBRider
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("unit-test")
class LoginServiceImpIT {

  @Autowired
  private LoginService loginService;


  @Test
  @DataSet(value = "login.xml")
  void checkLoginSuccessEnteredValidCredential() {
    String result = loginService
        .checkLogin(new Login("xyz@gmail.com", "pass"));
    assertEquals("Login Successful", result);
  }


  @Test
  void checkLoginFailsIfUserStatusFalseEnteredValidCredentials() {
    assertThrows(UserException.class, () -> loginService
        .checkLogin(new Login("abc@gmail.com", "pwd")));
  }

  @Test
  void checkLoginFailsIfUserStatusTrueEnteredWrong_Email() {
    assertThrows(UserException.class, () -> loginService
        .checkLogin(
            new Login("wrongEmail@gmail.com", "pwd")));
  }

  @Test
  void checkLoginFailsIfUserStatusTrueEnteredWrongPassword() {
    assertThrows(UserException.class, () -> loginService
        .checkLogin(
            new Login("xyz", "xyz@gmail.com")));
  }

  @Test
  void checkLoginFailsIfUserStatusFalseEnteredWrongEmail() {
    assertThrows(UserException.class, () -> loginService
        .checkLogin(
            new Login("wrongEmail@gmail.com", "pwd")));
  }

  @Test
  void checkLoginFailsIfUserStatusFalseEnteredWrongPassword() {
    assertThrows(UserException.class, () -> loginService
        .checkLogin(
            new Login("abc", "abc@gmail.com")));
  }


}