package com.mobiquity.movieReviewApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInformation {

  private String emailId;
  private String name;
  private String password;
}
