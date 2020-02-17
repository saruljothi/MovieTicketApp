package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.model.UserProfile;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserProfile findUserProfileByEmailId(String emailId) {
    return userRepository.findUserProfileByEmailId(emailId);
  }

}