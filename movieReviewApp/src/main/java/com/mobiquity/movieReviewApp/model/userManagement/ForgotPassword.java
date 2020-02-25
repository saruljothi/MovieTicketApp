package com.mobiquity.movieReviewApp.model.userManagement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPassword {
  private String password;
  private String token;
}
