package com.mobiquity.movieReviewApp.service;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mobiquity.movieReviewApp.model.UserProfile;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("unit-test")
class UserProfileServiceImplTest {

  @Autowired
  UserService userService;

  private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNb3ZpZVJldmlld0FwcGxpY2F0aW9uTW9iQGdtYWlsLmNvbTEiLCJleHAiOjE1ODA3Mjg3MzEsImlhdCI6MTU4MDcyNzI5MX0.u0viWSyo5zkAk2eWsqiyEvWMKHCPutONKzMZeIpTxxWrUuCS6E7fxnhrPumVTzr3lDZGJo65nLd8VZFyi9YhQQ";

  @Test
  public void checkIfEmailIdIsValid() {
    String result = userService.saveUser(
        new UserProfile(3l, "movie", "s@gmail.com", "asdertc", false, LocalDateTime.now(),
            LocalDateTime.now()));
    assertEquals("Activate your link", result);
  }

  @Test
  public void checkIfTokenIsExpired() {
    String result = userService.registerAccount(token);
    assertEquals("Your activation link got expired", result);
  }

  @Test
  public void checkIfTokenIsValid() {
    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzQGdtYWlsLmNvbSAzIiwiZXhwIjoxNTgwODI3MzE5LCJpYXQiOjE1ODA3NDA5MTl9.c10XXvlWEnVUtermplIi6try-tx3ch8bQPISYd6wUT2Eq5SBCvjW1BVZLWwGCpkeJPEvK5TuybFKwrylWX5C3Q";
    String result = userService.registerAccount(token);
    assertEquals("You are Registered Successfully", result);
  }

}