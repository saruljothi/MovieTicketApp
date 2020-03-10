package com.mobiquity.movieReviewApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.UserException;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordReset;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordUpdate;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.PasswordManagementServiceImpl;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.UtilityService;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PasswordMangementServiceUnitTest extends SignUpPasswordManagementHelperClass{

  @InjectMocks
  PasswordManagementServiceImpl passwordManagementService;
  @Mock
  UserRepository userRepository;
  @Mock
  UtilityService utilityService;

  @Test
  public void checkIfPasswordIsUpdatedSuccessfully() {
    when(userRepository.findPasswordByEmailId("ds@gmail.com"))
        .thenReturn("$2y$12$vAqr3amTKDFABIol9FIa6ebuHX8tAJKbOog2R3P5fbaf/E3pjkF4u");
    assertEquals("Password Updated",
        passwordManagementService.updatePassword(getNewPasswordUpdate()));
  }

  @Test
  public void checkIfOldPasswordIsNotMatching() {
    when(userRepository.findPasswordByEmailId("ds@gmail.com"))
        .thenReturn("$2y$12$co8Pepawly9JZqpulfOyNu9zVi7Dmg1P5IAr1NXhDTjY4FGlHCLGG");
    UserException ue = assertThrows(UserException.class,
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
  public void checkIfThereIsNoSuchEmailIdExists(){
    UserException ue = assertThrows(UserException.class,()-> passwordManagementService.forgotPasswordLink("ds@gmail.com"));
    assertEquals("No user with that email exists",
        ue.getLocalizedMessage());
  }

  @Test
  public void checkIfPassWordIsUpdatedForForgotPassword(){
    expiration=1000*30*60;
    when(userRepository.findByEmailId("ds@gmail.com")).thenReturn(
        java.util.Optional.of(getUserProfile()));
    String token = getToken(expiration);
    when(utilityService.retrieveDataFromClaim(any())).thenReturn(getClaim(token));

   assertEquals("Password Updated", passwordManagementService.updateForgottenPasswordWithNewPassword(getPasswordReset()));
  }

  private PasswordReset getPasswordReset() {
    PasswordReset passwordReset = new PasswordReset();
    passwordReset.setPassword("qwerty");
    passwordReset.setToken(getToken(24*60*60));
    return passwordReset;
  }

  private UserProfile getUserProfile() {
    UserProfile userProfile = new UserProfile();
    userProfile.setUserId(1L);
    userProfile.setEmailId("ds@gmail.com");
    userProfile.setPassword("$2y$12$vAqr3amTKDFABIol9FIa6ebuHX8tAJKbOog2R3P5fbaf/E3pjkF4u");
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
