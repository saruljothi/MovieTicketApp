
package com.mobiquity.movieReviewApp.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.mobiquity.movieReviewApp.Entity.UserProfile;
import com.mobiquity.movieReviewApp.exception.UserException;
import com.mobiquity.movieReviewApp.model.userManagement.Login;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

  @InjectMocks
  LoginServiceImpl userServiceImpl;

  @Mock
  UserRepository userRepository;

  private UserProfile userProfile = new UserProfile();

  @Test
  void checkLoginSuccess() {
    userProfile.setPassword("$2a$10$JAs.w36Ij6Wp1mrfPXgq8uLB2yVFZHXXNR6cKygNvchzc1JcArTJq");
    userProfile.setEmailId("xyz@gmail.com");
    userProfile.setStatus(true);

    when(userRepository.findByEmailId(Mockito.anyString())).thenReturn(
        java.util.Optional.of(userProfile));
    String result = userServiceImpl
        .checkLogin(new Login("xyz@gmail.com", "pass"));
    assertEquals("Login Successful", result);
  }

  @Test
  void checkLoginFail() {
    userProfile.setPassword("$2a$10$HtlIiJ6SnJarZk1igROz3ezvhWfmDYcrTy/NMP1qKNe8hCPoR7yRa");
    userProfile.setEmailId("abc@gmail.com");
    userProfile.setStatus(false);
    when(userRepository.findByEmailId(Mockito.anyString())).thenReturn(
        java.util.Optional.of(userProfile));
    assertThrows(UserException.class, () -> userServiceImpl
        .checkLogin(new Login("abc@gmail.com", "pwd")));
  }
}
