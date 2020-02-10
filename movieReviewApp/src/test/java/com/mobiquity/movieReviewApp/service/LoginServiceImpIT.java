package com.mobiquity.movieReviewApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.mobiquity.movieReviewApp.exception.LoginException;
import com.mobiquity.movieReviewApp.model.UserProfile;
import java.time.LocalDateTime;
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
        .checkLogin(new UserProfile(2L, "xyz", "xyz@gmail.com", "pass", true, LocalDateTime.now(),
            LocalDateTime.now()));
    assertEquals("Login Successful", result);
  }


  @Test
  void checkLoginFailsIfUserStatusFalseEnteredValidCredentials() {
    assertThrows(LoginException.class, () -> loginService
        .checkLogin(new UserProfile(1L, "abc", "abc@gmail.com", "pwd", false, LocalDateTime.now(),
            LocalDateTime.now())));
  }

  @Test
  void checkLoginFailsIfUserStatusTrueEnteredWrong_Email() {
    assertThrows(LoginException.class, () -> loginService
        .checkLogin(
            new UserProfile(1L, "abc", "wrongEmail@gmail.com", "pwd", true, LocalDateTime.now(),
                LocalDateTime.now())));
  }

  @Test
  void checkLoginFailsIfUserStatusTrueEnteredWrongPassword() {
    assertThrows(LoginException.class, () -> loginService
        .checkLogin(
            new UserProfile(2L, "xyz", "xyz@gmail.com", "wrongPassword", true, LocalDateTime.now(),
                LocalDateTime.now())));
  }

  @Test
  void checkLoginFailsIfUserStatusFalseEnteredWrongEmail() {
    assertThrows(LoginException.class, () -> loginService
        .checkLogin(
            new UserProfile(1L, "abc", "wrongEmail@gmail.com", "pwd", false, LocalDateTime.now(),
                LocalDateTime.now())));
  }

  @Test
  void checkLoginFailsIfUserStatusFalseEnteredWrongPassword() {
    assertThrows(LoginException.class, () -> loginService
        .checkLogin(
            new UserProfile(1L, "abc", "abc@gmail.com", "wrongPassword", false, LocalDateTime.now(),
                LocalDateTime.now())));
  }


}