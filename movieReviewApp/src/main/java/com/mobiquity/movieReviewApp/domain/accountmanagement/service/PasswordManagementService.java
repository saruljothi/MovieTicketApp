package com.mobiquity.movieReviewApp.domain.accountmanagement.service;

import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordReset;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordUpdate;

public interface PasswordManagementService {

  /**
   * To Reset password after user logged in.
   * @param passwordUpdate emailId,newPassword,oldPassword
   * @return message or userException
   */
  String updatePassword(PasswordUpdate passwordUpdate);

  /**
   * To send activationlink to the user to verify the user.
   * @param emailId String
   * @return message or userException
   */
  String forgotPasswordLink(String emailId);

  /**
   * To upadate the password, if user forgets password
   * @param passwordAndToken emailId,Password
   * @return message or userException
   */
  String updateForgottenPasswordWithNewPassword(PasswordReset passwordAndToken);

}
