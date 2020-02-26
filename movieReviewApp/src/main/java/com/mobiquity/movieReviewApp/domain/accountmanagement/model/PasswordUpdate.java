package com.mobiquity.movieReviewApp.domain.accountmanagement.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordUpdate {
private String emailId;
private String oldPassword;
private String newPassword;
}
