package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.model.UserProfile;

public interface UserService {
  UserProfile findUserProfileByEmailId(String emailId);
}
