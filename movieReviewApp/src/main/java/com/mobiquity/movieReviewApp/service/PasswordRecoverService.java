package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.model.ForgotPassword;
import com.mobiquity.movieReviewApp.model.ResetPassword;

public interface PasswordRecoverService {

  /**
   * To Reset password after user logged in.
   * @param resetPassword emailId,newPassword,oldPassword
   * @return message or userException
   */
  String resetPassword(ResetPassword resetPassword);

  /**
   * To send activationlink to the user to verify the user.
   * @param emailId String
   * @return message or userException
   */
  String passwordActivationLink(String emailId);

  /**
   * To upadate the password, if user forgets password
   * @param forgotPassword emailId,Password
   * @return message or userException
   */
  String UpdatePassword(ForgotPassword forgotPassword);

  /**
   * To get the emailId for setting up new password
   * @param token String
   * @return message or userException
   */
  String getEmailIdForNewPassword(String token);
}
