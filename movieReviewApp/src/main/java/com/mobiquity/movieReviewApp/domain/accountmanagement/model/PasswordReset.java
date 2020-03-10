package com.mobiquity.movieReviewApp.domain.accountmanagement.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PasswordReset {
  private String password;
  private String token;
}
