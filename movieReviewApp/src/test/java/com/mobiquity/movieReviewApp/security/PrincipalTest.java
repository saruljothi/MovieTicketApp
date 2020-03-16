package com.mobiquity.movieReviewApp.security;

import java.security.Principal;
import javax.security.auth.Subject;

public class PrincipalTest implements Principal {

  private String username;

  public PrincipalTest(String username) {
    this.username = username;
  }

  @Override
  public String getName() {
    return username;
  }

  @Override
  public boolean implies(Subject subject) {
    return false;
  }
}
