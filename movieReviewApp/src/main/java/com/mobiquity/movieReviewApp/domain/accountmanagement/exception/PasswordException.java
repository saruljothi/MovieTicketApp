package com.mobiquity.movieReviewApp.domain.accountmanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PasswordException extends RuntimeException {

  public PasswordException(String message) {
    super(message);

  }
}
