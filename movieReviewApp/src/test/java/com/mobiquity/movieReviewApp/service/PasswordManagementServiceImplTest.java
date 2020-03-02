//package com.mobiquity.movieReviewApp.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import com.github.database.rider.core.api.dataset.DataSet;
//import com.github.database.rider.junit5.api.DBRider;
//import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.UserException;
//import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordReset;
//import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordUpdate;
//import com.mobiquity.movieReviewApp.domain.accountmanagement.service.PasswordManagementService;
//import com.mobiquity.movieReviewApp.domain.accountmanagement.service.SignUpService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.security.test.context.support.WithMockUser;
//
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@DBRider
//class PasswordManagementServiceImplTest {
//
//  @Autowired
//  PasswordManagementService passwordManagementService;
//
//  @Autowired
//  SignUpService signUpService;
//
//  @Test
//  @DataSet(value = "password.xml")//password hashed value of qwerty
//  public void checkIfOldPasswordIsPresent() {
//    PasswordUpdate passwordUpdate = new PasswordUpdate();
//    passwordUpdate.setEmailId("ds@gmail.com");
//    passwordUpdate.setOldPassword("qwerty");
//    passwordUpdate.setNewPassword("payal");
//    String result = passwordManagementService.updatePassword(passwordUpdate);
//    assertEquals("Password Updated", result);
//  }
//
//  @Test
//  @DataSet(value = "password.xml")
//  public void checkIfOldPasswordIsNotCorrect() {
//    PasswordUpdate passwordUpdate = new PasswordUpdate();
//    passwordUpdate.setEmailId("ds@gmail.com");
//    passwordUpdate.setOldPassword("dfgh");
//    passwordUpdate.setNewPassword("qwerty");
//    UserException exception = assertThrows(UserException.class,
//        () -> passwordManagementService.updatePassword(passwordUpdate));
//    assertEquals("OldPassword is Not Matching",exception.getLocalizedMessage());
//  }
//
////  @Test
////  public void checkIfActivationLinkForPasswordIsSent() {
////    String result = passwordManagementService.forgotPasswordLink("ss@gmail.com");
////    assertEquals("Password Reset link sent to your email", result);
////  }
//
//  // Test for Forgot password.
////  @Test
////  @DataSet(value = "password.xml")
////  public void checkIfNewPasswordIsUpdated() {
////    PasswordReset passwordReset = new PasswordReset();
////    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhc2RmZ2hAZ21haWwuY29tIDEiLCJleHAiOjE1ODE1ODk2MjUsImlhdCI6MTU4MTUwMzIyNX0.yRREsDXalL-aIV2u3rXyHIgpx2NL4ZZ5bk4BwUyRcsCyD18YuZzOPx_sblMs059ZdYAsNIJWwDIfnqxEsqjjfA";
////    passwordReset.setToken(token);
////    passwordReset.setPassword("zxcvb");
////    UserException exception = assertThrows(UserException.class,
////        () -> passwordManagementService.updateForgottenPasswordWithNewPassword(passwordReset));
////    assertEquals("Your activation link got expired", exception.getLocalizedMessage());
////  }
//
////  @Test
////  public void checkIfEmailIdRetrievedCorrectlyForForgotPasswordLink() {
////    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkc0BnbWFpbC5jb20gMCIsImV4cCI6MTU4MTA2OTAyMsdfCwiaWF0IjoxNTgwOTgyNjIwfQ.t-HoaiqaPz__OVqccUsl2PaA2NrdxCm2yVyvGU1jVjljbFKFi5s78_06t1xc2xu2sbDwmIsNPyqm_mVouSYhyQ";
////    UserException exception = assertThrows(UserException.class, () -> passwordManagementService.getEmailIdForNewPassword(token));
////    assertEquals("Activation link is not valid",exception.getLocalizedMessage());
////  }
//}