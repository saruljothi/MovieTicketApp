package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.exception.LoginException;
import com.mobiquity.movieReviewApp.model.Login;
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
  public String checkLogin(Login login) throws LoginException {
    Optional<UserProfile> user = userRepository.findByEmailId(login.getEmailId());
    if (!user.isPresent()) {
      throw new LoginException();
    }
    boolean status = user.get().isStatus();
    String password = user.get().getPassword();
    if (!BCrypt.checkpw(login.getPassword(), password) || !status) {
      throw new LoginException();
    }
    return "Login Successful";
  }

}