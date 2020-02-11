package com.mobiquity.movieReviewApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.mobiquity.movieReviewApp.exception.UserException;
import com.mobiquity.movieReviewApp.model.ResetPassword;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PasswordRecoverServiceImplTest {

  @Autowired
  PasswordRecoverService passwordRecoverService;
  @Autowired
  SignUpService signUpService;

  @Test
  public void checkIfOldPasswordIsPresent() {
   /* signUpService.saveUser(
        new UserProfile(1l, "movie", "ds@gmail.com", "payal", false, LocalDateTime.now(),
            LocalDateTime.now()));*/
    ResetPassword resetPassword = new ResetPassword();
    resetPassword.setEmailId("ds@gmail.com");
    resetPassword.setOldPassword("payal");
    resetPassword.setNewPassword("qwerty");
    String result = passwordRecoverService.resetPassword(resetPassword);
    assertEquals("Password Updated", result);
  }

  @Test
  public void checkIfOldPasswordIsNotCorrect() {
   /* signUpService.saveUser(
        new UserProfile(1l, "movie", "ds@gmail.com", "payal", false, LocalDateTime.now(),
            LocalDateTime.now()));*/
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

  @Test
  public void checkIfNewPasswordIsUpdated() {
    ResetPassword resetPassword = new ResetPassword();
    resetPassword.setEmailId("ds@gmail.com");
    //resetPassword.setOldPassword("asdfg");
    resetPassword.setNewPassword("qwerty");
    String result = passwordRecoverService.UpdatePassword(resetPassword);
    assertEquals("New Password is Updated", result);

  }

  @Test
  public void checkIfEmailIdRetrievedCorrectlyForForgotPasswordLink() {
    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkc0BnbWFpbC5jb20gMCIsImV4cCI6MTU4MTA2OTAyMCwiaWF0IjoxNTgwOTgyNjIwfQ.t-HoaiqaPz__OVqccUsl2PaA2NrdxCm2yVyvGU1jVjljbFKFi5s78_06t1xc2xu2sbDwmIsNPyqm_mVouSYhyQ";
    assertThrows(UserException.class,()->passwordRecoverService.getEmailIdForNewPassword(token));

  }


}