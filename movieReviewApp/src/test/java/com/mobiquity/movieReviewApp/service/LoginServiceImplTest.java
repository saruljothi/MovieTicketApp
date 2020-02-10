
package com.mobiquity.movieReviewApp.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.mobiquity.movieReviewApp.exception.UserException;
import com.mobiquity.movieReviewApp.model.Login;
import com.mobiquity.movieReviewApp.model.UserProfile;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import java.time.LocalDateTime;
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

  @Test
  void checkLoginSuccess() {
    when(userRepository.findByEmailId(Mockito.anyString())).thenReturn(
        java.util.Optional.of(new UserProfile(2L, "xyz", "xyz@gmail.com",
            "$2a$10$JAs.w36Ij6Wp1mrfPXgq8uLB2yVFZHXXNR6cKygNvchzc1JcArTJq", true,
            LocalDateTime.now(), LocalDateTime.now())));
    String result = userServiceImpl
        .checkLogin(new Login("xyz@gmail.com", "pass"));
    assertEquals("Login Successful", result);
  }

  @Test
  void checkLoginFail() {
    when(userRepository.findByEmailId(Mockito.anyString())).thenReturn(
        java.util.Optional.of(new UserProfile(1L, "abc", "abc@gmail.com",
            "$2a$10$HtlIiJ6SnJarZk1igROz3ezvhWfmDYcrTy/NMP1qKNe8hCPoR7yRa", false,
            LocalDateTime.now(), LocalDateTime.now())));
    assertThrows(UserException.class, () -> userServiceImpl
        .checkLogin(new Login("abc@gmail.com", "pwd")));

  }
}
