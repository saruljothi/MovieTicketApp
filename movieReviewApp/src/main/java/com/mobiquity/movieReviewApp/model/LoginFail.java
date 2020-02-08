package com.mobiquity.movieReviewApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class LoginFail {

  private Integer code;
  private String message;
}
