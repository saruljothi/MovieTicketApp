package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.model.UserProfile;
import java.util.Optional;

public interface SignUpService {

  String saveUser(UserProfile userProfile);

  String registerAccount(String token);

  Optional<UserProfile> findUserProfileByEmailId(String emailId);

}
