package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.model.UserProfile;

public interface LoginService {

  /**
   * @param userProfile Enter Registered Email and Password
   * @return  whether login is Successful or Failed
   */
  String checkLogin(UserProfile userProfile);


}
