package com.mobiquity.movieReviewApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.mobiquity.movieReviewApp.exception.UserException;
import com.mobiquity.movieReviewApp.model.ForgotPassword;
import com.mobiquity.movieReviewApp.model.ResetPassword;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DBRider
class PasswordRecoverServiceImplTest {

  @Autowired
  PasswordRecoverService passwordRecoverService;
  @Autowired
  SignUpService signUpService;

  @Test
  @DataSet(value ="password.xml")//password hashed value of qwerty
  public void checkIfOldPasswordIsPresent() {

    ResetPassword resetPassword = new ResetPassword();
    resetPassword.setEmailId("ds@gmail.com");
    resetPassword.setOldPassword("qwerty");
    resetPassword.setNewPassword("payal");
    String result = passwordRecoverService.resetPassword(resetPassword);
    assertEquals("Password Updated", result);
  }

  @Test
  @DataSet(value="password.xml")
  public void checkIfOldPasswordIsNotCorrect() {
    ResetPassword resetPassword = new ResetPassword();
    resetPassword.setEmailId("ds@gmail.com");
    resetPassword.setOldPassword("dfgh");
    resetPassword.setNewPassword("qwerty");
    assertThrows(UserException.class, ()->passwordRecoverService.resetPassword(resetPassword));
  }

  @Test
  public void checkIfActivationLinkForPasswordIsSent() {
    String result = passwordRecoverService.passwordActivationLink("ss@gmail.com");
    assertEquals("Password Reset link sent to your email", result);
  }

  // Test for Forgot password.
  @Test
  @DataSet(value ="password.xml")
  public void checkIfNewPasswordIsUpdated() {
    ForgotPassword forgotPassword = new ForgotPassword();
    forgotPassword.setToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2RmZ2hAZ21haWwuY29tIDEiLCJleHAiOjE1ODE1ODk2MjUsImlhdCI6MTU4MTUwMzIyNX0.yRREsDXalL-aIV2u3rXyHIgpx2NL4ZZ5bk4BwUyRcsCyD18YuZzOPx_sblMs059ZdYAsNIJWwDIfnqxEsqjjfA");
    forgotPassword.setPassword("zxcvb");
    String result = passwordRecoverService.UpdatePassword(forgotPassword);
    assertEquals("New Password is Updated", result);

  }

  @Test
  public void checkIfEmailIdRetrievedCorrectlyForForgotPasswordLink() {
    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkc0BnbWFpbC5jb20gMCIsImV4cCI6MTU4MTA2OTAyMCwiaWF0IjoxNTgwOTgyNjIwfQ.t-HoaiqaPz__OVqccUsl2PaA2NrdxCm2yVyvGU1jVjljbFKFi5s78_06t1xc2xu2sbDwmIsNPyqm_mVouSYhyQ";
    assertThrows(UserException.class,()->passwordRecoverService.getEmailIdForNewPassword(token));

  }


}