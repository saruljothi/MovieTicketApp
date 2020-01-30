package com.mobiquity.movieReviewApp.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class VerificationTokenTest {

  VerificationToken verificationToken =new VerificationToken();

  @Test
  public void testExpireDate()
  {
    LocalDateTime localDateTime = verificationToken.calculateExpiryDate(1440);
    assertTrue(localDateTime instanceof LocalDateTime);

  }

}