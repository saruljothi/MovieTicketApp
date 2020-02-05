package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.model.UserProfile;

public interface UserService {

  String saveUser(UserProfile userProfile);

  String registerAccount(String token);


}
