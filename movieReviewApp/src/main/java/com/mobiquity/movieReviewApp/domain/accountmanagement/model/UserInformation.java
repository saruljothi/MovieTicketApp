package com.mobiquity.movieReviewApp.domain.accountmanagement.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInformation {
  private String emailId;
  private String name;
  private String password;
  private String passwordConfirmation;
}
