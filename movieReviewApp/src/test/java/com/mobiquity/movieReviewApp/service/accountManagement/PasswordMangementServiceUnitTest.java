package com.mobiquity.movieReviewApp.service.accountManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.PasswordException;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordReset;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordUpdate;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.PasswordManagementServiceImpl;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.UtilityService;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import com.mobiquity.movieReviewApp.security.AuthTest;
import com.mobiquity.movieReviewApp.security.PrincipalTest;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
public class PasswordMangementServiceUnitTest extends SignUpPasswordManagementHelperClass {

  @InjectMocks
  PasswordManagementServiceImpl passwordManagementService;
  @Mock
  UserRepository userRepository;
  @Mock
  UtilityService utilityService;

  @Test
  public void checkIfPasswordIsUpdatedSuccessfully() {
    SecurityContextHolder.getContext()
        .setAuthentication(new AuthTest(new PrincipalTest("ds@gmail.com")));
    UserProfile userProfile = getUserProfile();
    userProfile.setPassword("$2y$12$vAqr3amTKDFABIol9FIa6ebuHX8tAJKbOog2R3P5fbaf/E3pjkF4u");
    when(userRepository.findByEmailId("ds@gmail.com")).thenReturn(
        java.util.Optional.of(userProfile));
    assertEquals("Password Updated",
        passwordManagementService.updatePassword(getNewPasswordUpdate()));
  }

  @Test
  public void checkIfOldPasswordIsNotMatching() {
    when(userRepository.findByEmailId("ds@gmail.com")).thenReturn(
        java.util.Optional.of(getUserProfile()));
    PasswordException ue = assertThrows(PasswordException.class,
        () -> passwordManagementService.updatePassword(getNewPasswordUpdate()));
    assertEquals("OldPassword is Not Matching", ue.getLocalizedMessage());

  }

  @Test
  public void checkIfForgotPasswordLinkIsSentCorrectly() {
    when(userRepository.findByEmailId("ds@gmail.com")).thenReturn(
        java.util.Optional.of(getUserProfile()));
    assertEquals("Password Reset link sent to your email",
        passwordManagementService.forgotPasswordLink("ds@gmail.com"));

  }

  @Test
  public void checkIfThereIsNoSuchEmailIdExists() {
    PasswordException ue = assertThrows(PasswordException.class,
        () -> passwordManagementService.forgotPasswordLink("ds@gmail.com"));
    assertEquals("No user with that email exists",
        ue.getLocalizedMessage());
  }

  @Test
  public void checkIfPassWordIsUpdatedForForgotPassword() {
    UserProfile userProfile = getUserProfile();
    userProfile.setForgotPasswordStatus(false);
    when(userRepository.findByEmailId("ds@gmail.com")).thenReturn(
        java.util.Optional.of(userProfile));
    when(utilityService.retrieveDataFromClaim(any()))
        .thenReturn(getClaim(getToken(60 * 30 * 1000)));

    assertEquals("Password Updated",
        passwordManagementService.updateForgottenPasswordWithNewPassword(getPasswordReset()));
  }

  @Test
  public void checkIfTokenIsInvalid() {
    when(utilityService.retrieveDataFromClaim(any()))
        .thenReturn(getClaim(getToken(30 * 60 * 1000)));
    assertEquals("token invalid!",
        assertThrows(PasswordException.class, () -> passwordManagementService
            .updateForgottenPasswordWithNewPassword(getPasswordReset())).getLocalizedMessage());
  }

  @Test
  public void checkIfpasswordIsUpdatedOnceForTheSentForgotPasswordLink() {
    when(utilityService.retrieveDataFromClaim(any()))
        .thenReturn(getClaim(getToken(30 * 60 * 1000)));
    when(userRepository.findByEmailId("ds@gmail.com"))
        .thenReturn(java.util.Optional.of(getUserProfile()));
    assertEquals("password Already updated!",
        assertThrows(PasswordException.class, () -> passwordManagementService
            .updateForgottenPasswordWithNewPassword(getPasswordReset())).getLocalizedMessage());
  }
@Test
public void checkIfTokenPassedIsWrong(){
    when(utilityService.retrieveDataFromClaim(any())).thenThrow(MalformedJwtException.class);
  assertEquals("Activation link is not valid",
      assertThrows(PasswordException.class, () -> passwordManagementService
          .updateForgottenPasswordWithNewPassword(getPasswordReset())).getLocalizedMessage());
}

@Test
public void checkIfActivationLinkgotExpired(){
  when(utilityService.retrieveDataFromClaim(any())).thenThrow(ExpiredJwtException.class);
  assertEquals("Your activation link got expired",
      assertThrows(PasswordException.class, () -> passwordManagementService
          .updateForgottenPasswordWithNewPassword(getPasswordReset())).getLocalizedMessage());
}
  private PasswordReset getPasswordReset() {
    PasswordReset passwordReset = new PasswordReset();
    passwordReset.setPassword("qwerty");
    passwordReset.setToken(getToken(24 * 60 * 60));
    return passwordReset;
  }

  private UserProfile getUserProfile() {
    UserProfile userProfile = new UserProfile();
    userProfile.setUserId(1L);
    userProfile.setEmailId("ds@gmail.com");
    userProfile.setPassword("$2y$12$vAqr3amTKDFABIol9FIa6ebuHX8tAJKbOog2R3P5fbaf/E3pjkF4i");
    userProfile.setForgotPasswordStatus(true);
    return userProfile;
  }

  private PasswordUpdate getNewPasswordUpdate() {
    PasswordUpdate passwordUpdate = new PasswordUpdate();
    passwordUpdate.setEmailId("ds@gmail.com");
    passwordUpdate.setOldPassword("qwerty");
    passwordUpdate.setNewPassword("asdfgh");
    return passwordUpdate;
  }
}