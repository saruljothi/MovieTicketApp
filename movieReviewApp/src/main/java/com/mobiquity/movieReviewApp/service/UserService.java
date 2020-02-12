package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.model.UserProfile;

public interface UserService {

  /**
   * @param userProfile Enter Registered Email and Password
   * @return  whether login is Successful or Failed
   */
  String checkLogin(UserProfile userProfile);


  UserProfile findUserProfileByEmailId(String emailId);
}
