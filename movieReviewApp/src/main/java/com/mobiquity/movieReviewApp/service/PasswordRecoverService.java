package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.model.ForgotPassword;
import com.mobiquity.movieReviewApp.model.ResetPassword;

public interface PasswordRecoverService {

  String resetPassword(ResetPassword resetPassword);

  String passwordActivationLink(String emailId);

  String UpdatePassword(ForgotPassword forgotPassword);

  String getEmailIdForNewPassword(String token);
}
