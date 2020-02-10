package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.exception.LoginException;
import com.mobiquity.movieReviewApp.model.UserProfile;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService {

  private UserRepository userRepository;

  public LoginServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public String checkLogin(UserProfile userProfile) throws LoginException {
    Optional<UserProfile> user = userRepository.findByEmailId(userProfile.getEmailId());
    if (!user.isPresent()) {
      throw new LoginException();
    }
    boolean status = user.get().isStatus();
    String password = user.get().getPassword();
    if (!BCrypt.checkpw(userProfile.getPassword(), password) || !status) {
      throw new LoginException();
    }
    return "Login Successful";
  }

}