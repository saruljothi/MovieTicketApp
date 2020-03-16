package com.mobiquity.movieReviewApp.service.accountManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.UserException;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.UserInformation;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.SignUpServiceImpl;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.UtilityService;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@ExtendWith(MockitoExtension.class)

public class SignUpServiceUnitTest extends SignUpPasswordManagementHelperClass {

  @InjectMocks
  private SignUpServiceImpl signUpServiceimpl;
  @Mock
  private UserRepository userRepository;
  @Mock
  private UtilityService utilityService;

  private UserInformation userInformation;
  private UserProfile userProfile;

  @Test
  public void checkIfActivationLinkIsSentSuccessful() {
    when(userRepository.save(Mockito.any(UserProfile.class))).thenReturn(setUserProfile());
    assertEquals("Activate your link", signUpServiceimpl.saveUser(setUserInformation()));

  }

  @Test
  public void checkIfUserIsAlreadyPresent() {
    when(userRepository.save(Mockito.any(UserProfile.class)))
        .thenThrow(DataIntegrityViolationException.class);
    UserException userException = assertThrows(UserException.class,
        () -> signUpServiceimpl.saveUser(setUserInformation()));
    assertEquals("Your email is already registered.", userException.getLocalizedMessage());
  }

  @Test
  public void checkIfUserIsRegisteredSuccessfully() {
    expiration = 60 * 24 * 60;
    when(utilityService.retrieveDataFromClaim(any())).thenReturn(getClaim(getToken(expiration)));
    assertEquals("You are Registered Successfully",
        signUpServiceimpl.registerAccount(getToken(expiration)));
  }

  @Test
  public void checkIfTokenIsExpired() {
    expiration = 0;
    when(utilityService.retrieveDataFromClaim(any())).thenThrow((ExpiredJwtException.class));
    UserException userException = assertThrows(UserException.class,
        () -> signUpServiceimpl.registerAccount(getToken(expiration)));
    assertEquals("Your activation link got expired", userException.getLocalizedMessage());
  }

  @Test
  public void checkIfTokenIsNotValid() {
    when(utilityService.retrieveDataFromClaim(any())).thenThrow((MalformedJwtException.class));
    UserException userException = assertThrows(UserException.class,
        () -> signUpServiceimpl.registerAccount("iojdxend"));
    assertEquals("Activation link is not valid", userException.getLocalizedMessage());
  }

  @Test
  public void checkIfUserProfileRetrievedProperly(){
    when(userRepository.findByEmailId("ds@gmail.com")).thenReturn(
        java.util.Optional.ofNullable(setUserProfile()));
    assertEquals("ds@gmail.com",signUpServiceimpl.findUserProfileByEmailId("ds@gmail.com").get().getEmailId());
  }

  private UserInformation setUserInformation() {
    userInformation = new UserInformation();
    userInformation.setEmailId("ds@gmail.com");
    userInformation.setPassword("pass");
    userInformation.setName("ds");
    userInformation.setPasswordConfirmation("pass");
    return userInformation;
  }

  private UserProfile setUserProfile() {
    userProfile = new UserProfile();
    userProfile.setUserId(1L);
    userProfile.setEmailId("ds@gmail.com");
    userProfile.setPassword("pass");
    userProfile.setName("ds");
    return userProfile;
  }


}
