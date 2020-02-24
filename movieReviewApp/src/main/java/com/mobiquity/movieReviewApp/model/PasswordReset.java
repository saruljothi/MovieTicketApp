package com.mobiquity.movieReviewApp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordReset {
  private String password;
  private String token;
}
