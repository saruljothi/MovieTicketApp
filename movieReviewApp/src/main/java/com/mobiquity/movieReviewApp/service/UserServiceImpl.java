package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.model.UserProfile;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private JavaMailSender javaMailSender;

  private UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository,JavaMailSender javaMailSender) {
    this.userRepository = userRepository;
    this.javaMailSender = javaMailSender;
  }

  @Override
  public String saveUser(UserProfile userProfile) {

      if( findByEmailId(userProfile)) {
        return "You have already registered";
      }else{
        SendActivationLink(userProfile.getEmailId());
        return "Activate ur link";
    }

  }

  private void SendActivationLink(String emailId) {
    SimpleMailMessage email = new SimpleMailMessage();
    String token =generateRandomToken();
    email.setTo(emailId);
    email.setSubject("activation link");
    email.setText("activation link valid for 24 hrs"+"\n"+ " http://localhost:8080?token=" + token);

    javaMailSender.send(email);
  }

  private boolean findByEmailId(UserProfile userProfile) {
    Optional<UserProfile> user = userRepository.findByEmailId(userProfile.getEmailId());
    return user.isPresent();

  }

  private String generateRandomToken() {
    return UUID.randomUUID().toString();
  }
}
