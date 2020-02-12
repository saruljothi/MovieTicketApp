package com.mobiquity.movieReviewApp.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.mobiquity.movieReviewApp.model.ResetPassword;
import com.mobiquity.movieReviewApp.model.UserProfile;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@DBRider
//@DBUnit(leakHunter = true)
@ActiveProfiles("unit-test")
class SignUpServiceImplTest {

  @Autowired
  SignUpService signUpService;


  private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzQGdtYWlsLmNvbSAyIiwiZXhwIjoxNTgwOTA2NDg2LCJpYXQiOjE1ODA4MjAwODZ9.1EAgHyfosb4f7N9zIlPDlxiZBIekEgq5ZWHu0eCQft-B5Nd2C9gyAuiVO-dJ98f5JcLkHtG2W8cVDm2R_mf7KQ";

  /*
  @Test
  public void checkIfEmailIdIsNotAlreadyRegistered() {
    String result = signUpService.saveUser(
        new UserProfile(1l,"movie", "s@gmail.com", "asdfg", false, LocalDateTime.now(),
            LocalDateTime.now()));
    assertEquals("Activate your link", result);
  }

  @Test
  public void checkIfEmailIdIsAlreadyRegistered() {
    String result = signUpService.saveUser(
        new UserProfile(2l, "movie", "s@gmail.com", "asdertc", false, LocalDateTime.now(),
            LocalDateTime.now()));
    assertEquals("Your email is already registered.", result);
  }
   */

  @Test
  public void checkIfTokenIsExpired() {
    String result = signUpService.registerAccount(token);
    assertEquals("Your activation link got expired", result);
  }

  @Test
// @DataSet(value = "data.xml")
  public void checkIfTokenIsNotValid() {
    String token = "eJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzQGdtYWlsLmNvbSAxIiwiZXhwIjoxNTgwOTAxNTgxLCJpYXQiOjE1ODA4MTUxODF9.lydIGViOyEewJ8Yo8ApsuJetQtMcQDjGVetuWjPIFKfK7JAceRlgJHOQ5PmeJiKFIJIJCC4WEqg12rQ9EC4iyQ";
    String result = signUpService.registerAccount(token);
    assertEquals("Activation link is not valid", result);
  }

 }