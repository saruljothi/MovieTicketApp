package com.mobiquity.movieReviewApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.mobiquity.movieReviewApp.exception.LoginException;
import com.mobiquity.movieReviewApp.model.UserProfile;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserServiceImpIT {

  @Autowired
  private UserService userService;

  @Test
  void checkLoginSuccessEnteredValidCredential() {
    String result = userService
        .checkLogin(new UserProfile(2L, "xyz", "xyz@gmail.com", "pass", true, LocalDateTime.now(),LocalDateTime.now()));
    assertEquals("Login Successful", result);
  }

  @Test
  void checkLoginFailsIfUserStatusFalseEnteredValidCredentials() {
    assertThrows(LoginException.class, () -> userService
        .checkLogin(new UserProfile(1L, "abc", "abc@gmail.com", "pwd", false,LocalDateTime.now(),LocalDateTime.now())));
  }

  @Test
  void checkLoginFailsIfUserStatusTrueEnteredWrong_Email() {
    assertThrows(LoginException.class, () -> userService
        .checkLogin(new UserProfile(1L, "abc", "wrongEmail@gmail.com", "pwd", true,LocalDateTime.now(),LocalDateTime.now())));
  }

  @Test
  void checkLoginFailsIfUserStatusTrueEnteredWrongPassword() {
    assertThrows(LoginException.class, () -> userService
        .checkLogin(new UserProfile(2L, "xyz", "xyz@gmail.com", "wrongPassword", true,LocalDateTime.now(),LocalDateTime.now())));
  }

  @Test
  void checkLoginFailsIfUserStatusFalseEnteredWrongEmail() {
    assertThrows(LoginException.class, () -> userService
        .checkLogin(new UserProfile(1L, "abc", "wrongEmail@gmail.com", "pwd", false,LocalDateTime.now(),LocalDateTime.now())));
  }

  @Test
  void checkLoginFailsIfUserStatusFalseEnteredWrongPassword() {
    assertThrows(LoginException.class, () -> userService
        .checkLogin(new UserProfile(1L, "abc", "abc@gmail.com", "wrongPassword", false,LocalDateTime.now(),LocalDateTime.now())));
  }
}