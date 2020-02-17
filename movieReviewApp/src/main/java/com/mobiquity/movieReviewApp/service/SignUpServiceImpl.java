package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.model.UserProfile;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class SignUpServiceImpl implements SignUpService {

  private static final int FIXEDRATE = 60 * 60 * 24 * 7 * 1000;
  private UserRepository userRepository;
  private Claims claim;
  private UtilityService utilityService;

  public SignUpServiceImpl(UserRepository userRepository,UtilityService utilityService) {
    this.userRepository = userRepository;
    this.utilityService = utilityService;
  }

  @Transactional
  @Override
  public String saveUser(UserProfile userProfile) {
    userProfile.setPassword(BCrypt.hashpw(userProfile.getPassword(), BCrypt.gensalt()));
    UserProfile user = userRepository.save(userProfile);
    utilityService.sendActivationLink(user.getEmailId(), user.getUserId());

    return "Activate your link";
  }

  @Transactional
  @Override
  public String registerAccount(String token) {
    try {
      claim = utilityService.retrieveDataFromClaim(token);
      long id = Long.parseLong(claim.getSubject().split(" ")[1]);
      userRepository.updateStatus(id);
      return "You are Registered Successfully";
    } catch (ExpiredJwtException e) {
      return "Your activation link got expired";
    } catch (MalformedJwtException e) {
      return "Activation link is not valid";
    }
  }

  @Scheduled(fixedRate = FIXEDRATE)
  @Transactional
  public void setScheduler() {
    userRepository.deleteByCreatedOnAndStatus(LocalDateTime.now().minusDays(1));
  }

  @Override
  public Optional<UserProfile> findUserProfileByEmailId(String emailId) {
    return userRepository.findByEmailId(emailId);
  }

}
