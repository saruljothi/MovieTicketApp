package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.Entity.UserProfile;

public interface UserService {

  /**
   * @param userProfile Enter Registered Email and Password
   * @return  whether login is Successful or Failed
   */
  String checkLogin(UserProfile userProfile);


}
