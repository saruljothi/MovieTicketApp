package com.mobiquity.movieReviewApp.model.userManagement;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPassword {
private String emailId;
private String oldPassword;
private String newPassword;
}
