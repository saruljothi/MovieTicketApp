package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.Entity.UserProfile;
import com.mobiquity.movieReviewApp.model.UserInformation;

public interface SignUpService {

  String saveUser(UserInformation userInformation);

  String registerAccount(String token);

}
