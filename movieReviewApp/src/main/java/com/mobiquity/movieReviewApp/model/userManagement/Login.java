package com.mobiquity.movieReviewApp.model.userManagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {
  private String emailId;
  private String password;
}
