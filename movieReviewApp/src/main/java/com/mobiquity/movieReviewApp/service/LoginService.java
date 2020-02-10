package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.model.Login;

public interface LoginService {

  /**
   * @param login Enter Registered Email and Password
   * @return whether login is Successful or Failed
   */
  String checkLogin(Login login);

}
