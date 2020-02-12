package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.model.ResetPassword;

public interface PasswordRecoverService {

  String resetPassword(ResetPassword resetPassword);

  String passwordActivationLink(String emailId);

  String UpdatePassword(ResetPassword resetPassword);

  String getEmailIdForNewPassword(String token);
}
