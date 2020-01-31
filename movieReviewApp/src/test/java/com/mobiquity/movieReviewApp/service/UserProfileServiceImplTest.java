package com.mobiquity.movieReviewApp.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
  @Test
  public void checkIfEmailIdIsValid(){
    String result  = userService.saveUser(new UserProfile(1l,"movie","MovieReviewApplicationMob@gmail.com","asdertc",null,false,
        LocalDateTime.now(),LocalDateTime.now()));
    assertEquals("Activate your link",result);
  }
 /* @Test
  public void testExpireDate()
  {
    LocalDateTime localDateTime = userService.calculateExpiryDate(1440);
    assertTrue(localDateTime instanceof LocalDateTime);

  }*/

  @Test
  public void checkIfValidationTokenIsValid()
  {
  //  userService.signUpUser();
  }

}