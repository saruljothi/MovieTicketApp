package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.exception.UserException;
import com.mobiquity.movieReviewApp.model.UserProfile;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public String checkLogin(UserProfile userProfile) {
    try {
      Optional<UserProfile> user = userRepository.findByEmailId(userProfile.getEmailId());
      if (!user.isPresent()) {
        throw new UserException("Login Failed");
      }
      boolean status = user.get().isStatus();
      String password = user.get().getPassword();
      if (!BCrypt.checkpw(userProfile.getPassword(), password) || !status) {
        throw new UserException("Login Failed");
      }
      return "Login Successful";

    } catch (UserException ex) {
      throw new UserException("Login Failed");
    }
  }


}