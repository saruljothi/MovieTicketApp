package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.model.UserProfile;

public interface UserService {

  /**
   * @param userProfile
   * @return Check whether login is Successful or Failed
   */
  String checkLogin(UserProfile userProfile);


}
