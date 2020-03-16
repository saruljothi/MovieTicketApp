package com.mobiquity.movieReviewApp.service.accountManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.PasswordException;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordReset;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordUpdate;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.PasswordManagementService;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.SignUpService;
import com.mobiquity.movieReviewApp.security.AuthTest;
import com.mobiquity.movieReviewApp.security.PrincipalTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DBRider
class PasswordManagementServiceImplIT {

  @Autowired
  PasswordManagementService passwordManagementService;

  @Autowired
  SignUpService signUpService;

  @BeforeEach
  public void setUP() {
    SecurityContextHolder.getContext()
        .setAuthentication(new AuthTest(new PrincipalTest("ds@gmail.com")));
  }

  @Test
  @DataSet(value = "password.xml")//password hashed value of qwerty
  public void checkIfOldPasswordIsPresent() {
    PasswordUpdate passwordUpdate = new PasswordUpdate();
    passwordUpdate.setEmailId("ds@gmail.com");
    passwordUpdate.setOldPassword("qwerty");
    passwordUpdate.setNewPassword("payal");
    String result = passwordManagementService.updatePassword(passwordUpdate);
    assertEquals("Password Updated Successfully!", result);
  }

  @Test
  @DataSet(value = "password.xml")
  public void checkIfOldPasswordIsNotCorrect() {
    PasswordUpdate passwordUpdate = new PasswordUpdate();
    passwordUpdate.setEmailId("ds@gmail.com");
    passwordUpdate.setOldPassword("dfgh");
    passwordUpdate.setNewPassword("qwerty");
    PasswordException exception = assertThrows(PasswordException.class,
        () -> passwordManagementService.updatePassword(passwordUpdate));
    assertEquals("OldPassword is Not Matching!", exception.getLocalizedMessage());
  }

  @Test
  @DataSet(value = "password.xml")
  public void checkIfActivationLinkForPasswordIsSent() {
    String result = passwordManagementService.forgotPasswordLink("ds@gmail.com");
    assertEquals("Password Reset link sent to your email.", result);
  }

  // Test for Forgot password.
  @Test
  @DataSet(value = "password.xml")
  public void checkIfNewPasswordIsUpdated() {
    PasswordReset passwordReset = new PasswordReset();
    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2RmZ2hAZ21haWwuY29tIDEiLCJleHAiOjE1ODE1ODk2MjUsImlhdCI6MTU4MTUwMzIyNX0.yRREsDXalL-aIV2u3rXyHIgpx2NL4ZZ5bk4BwUyRcsCyD18YuZzOPx_sblMs059ZdYAsNIJWwDIfnqxEsqjjfA";
    passwordReset.setToken(token);
    passwordReset.setPassword("zxcvb");
    PasswordException exception = assertThrows(PasswordException.class,
        () -> passwordManagementService.updateForgottenPasswordWithNewPassword(passwordReset));
    assertEquals("Your activation link got expired.", exception.getLocalizedMessage());
  }
}