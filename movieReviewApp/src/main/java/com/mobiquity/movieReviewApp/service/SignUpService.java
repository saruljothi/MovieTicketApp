package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.model.UserProfile;

public interface SignUpService {

  String saveUser(UserProfile userProfile);

  String registerAccount(String token);

}
