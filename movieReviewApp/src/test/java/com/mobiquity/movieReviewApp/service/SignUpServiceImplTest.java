package com.mobiquity.movieReviewApp.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.mobiquity.movieReviewApp.exception.UserException;
import com.mobiquity.movieReviewApp.Entity.UserProfile;
import com.mobiquity.movieReviewApp.model.UserInformation;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DBRider
@ActiveProfiles()
class SignUpServiceImplTest {

  @Autowired
  SignUpService signUpService;

  @Test
  public void checkIfEmailIdIsNotAlreadyRegistered() {
    String result = signUpService.saveUser(
        new UserInformation("asdfgh@gmail.com","movie","asdfg"));
    assertEquals("Activate your link", result);
  }

  @Test
  @DataSet(value = "data.xml")
  public void checkIfEmailIdIsAlreadyRegistered() {
    assertThrows(UserException.class, () -> signUpService.saveUser(
        new UserInformation("www@gmail.com","movie","asdfg")));
  }

  @Test
  public void checkIfTokenIsExpired() {
    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzQGdtYWlsLmNvbSAyIiwiZXhwIjoxNTgwOTA2NDg2LCJpYXQiOjE1ODA4MjAwODZ9.1EAgHyfosb4f7N9zIlPDlxiZBIekEgq5ZWHu0eCQft-B5Nd2C9gyAuiVO-dJ98f5JcLkHtG2W8cVDm2R_mf7KQ";
    assertThrows(UserException.class, () -> signUpService.registerAccount(token));

  }

  @Test

  public void checkIfTokenIsNotValid() {
    String token = "eJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzQGdtYWlsLmNvbSAxIiwiZXhwIjoxNTgwOTAxNTgxLCJpYXQiOjE1ODA4MTUxODF9.lydIGViOyEewJ8Yo8ApsuJetQtMcQDjGVetuWjPIFKfK7JAceRlgJHOQ5PmeJiKFIJIJCC4WEqg12rQ9EC4iyQ";
    assertThrows(UserException.class, () -> signUpService.registerAccount(token));

  }

}