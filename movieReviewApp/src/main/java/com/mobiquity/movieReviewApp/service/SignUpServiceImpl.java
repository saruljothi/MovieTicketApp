package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.model.UserProfile;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SignUpServiceImpl implements UserService {
private String token;
  private static final int EXPIRATION = 60 * 24;
  private JavaMailSender javaMailSender;

  private UserRepository userRepository;

  public SignUpServiceImpl(UserRepository userRepository, JavaMailSender javaMailSender) {
    this.userRepository = userRepository;
    this.javaMailSender = javaMailSender;
  }

  @Override
  public String saveUser(UserProfile userProfile) {

    try {
      token = generateRandomToken();
      userProfile.setToken(token);
      UserProfile user = userRepository.save(userProfile);
      sendActivationLink(user.getEmailId());

      return "Activate your link";
    } catch (Exception e) {
      return "Your email is already registered.";
    }

  }


  private void sendActivationLink(String emailId) {
    SimpleMailMessage email = new SimpleMailMessage();


    email.setTo(emailId);
    email.setSubject("activation link");
    email.setText(
        "activation link valid for 24 hrs" + "\n" + " http://localhost:8080/v1/activatelink?token="
            + token);

    javaMailSender.send(email);
  }


  private String generateRandomToken() {
    return UUID.randomUUID().toString();
  }

  @Override
  public String signUpUser(String token) {
    return null;
  }

  public LocalDateTime calculateExpiryDate(int expiryTimeInMinutes) {

    return LocalDateTime.now().plusMinutes(EXPIRATION);
  }

}
