package com.mobiquity.movieReviewApp.service;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mobiquity.movieReviewApp.model.UserProfile;
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
    String result  = userService.saveUser(new UserProfile(1l,"movie","MovieReviewApplicationMob@gmail.com","asdertc"));
    assertEquals("Activate ur link",result);
  }

}