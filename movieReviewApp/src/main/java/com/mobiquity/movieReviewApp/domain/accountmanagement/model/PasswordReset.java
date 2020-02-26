package com.mobiquity.movieReviewApp.domain.accountmanagement.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordReset {
  private String password;
  private String token;
}
