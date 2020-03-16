package com.mobiquity.movieReviewApp.service.accountManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.PasswordException;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.UserException;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.UtilityService;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class UtilityServiceUnitTest extends SignUpPasswordManagementHelperClass {

  @InjectMocks
  private UtilityService utilityService;

  private JavaMailSender javaMailSender = javaMailSender();
  @Mock
  private Dotenv dotenv;

  @Mock
  MessageSource messageSource;

  @BeforeEach
  public void setUp() {
    when(dotenv.get("SECRET")).thenReturn("testmobiquity");
    ReflectionTestUtils.setField(utilityService, "email", new SimpleMailMessage());
    ReflectionTestUtils.setField(utilityService, "javaMailSender", javaMailSender);
  }

  @Test
  public void checkIfClaimIsRetrievedSuccessfully() {
    String token = getToken(60 * 60 * 24);
    Claims claim = utilityService.retrieveDataFromClaim(token);
    assertEquals(getClaim(token), claim);
  }

  @Test
  public void checkIfSendActivationLinkIsSentSuccessful() {
    when(messageSource
        .getMessage("user.signup.link.valid.time", null, LocaleContextHolder.getLocale()))
        .thenReturn("Activation link valid for 24 hrs");

    when(messageSource
        .getMessage("user.signup.link.not.send", null, LocaleContextHolder.getLocale()))
        .thenReturn("Unable to Send ActivationLink to your EmailId");

    assertEquals("Unable to Send ActivationLink to your EmailId",
        assertThrows(UserException.class,
            () -> utilityService.sendActivationLink("ds@gmail.com", 1L))
            .getLocalizedMessage());
  }

  @Test
  public void checkIfSendForgotPasswordLinkIsSentSuccessful() {
    when(messageSource
        .getMessage("user.password.link.valid.time", null, LocaleContextHolder.getLocale()))
        .thenReturn("Password reset link valid for 30 minutes");
    when(messageSource
        .getMessage("user.password.link.not.send", null, LocaleContextHolder.getLocale()))
        .thenReturn("Unable to Send ForgotPasswordActivationLink to your EmailId");
    assertEquals("Unable to Send ForgotPasswordActivationLink to your EmailId", assertThrows(
        PasswordException.class,
        () -> utilityService.sendPasswordForgotLink(getUserProfile())).getLocalizedMessage());

  }

  private UserProfile getUserProfile() {
    UserProfile userProfile = new UserProfile();
    userProfile.setUserId(1L);
    userProfile.setEmailId("ds@gmail.com");
    userProfile.setPassword("$2y$12$vAqr3amTKDFABIol9FIa6ebuHX8tAJKbOog2R3P5fbaf/E3pjkF4u");
    userProfile.setForgotPasswordStatus(true);
    return userProfile;
  }

}
