package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.model.ResetPassword;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import io.jsonwebtoken.Claims;
import javax.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordRecoverServiceImpl implements PasswordRecoverService {

  private UserRepository userRepository;

  private UtilityService utilityService;

  private Claims claim;

  public PasswordRecoverServiceImpl(UserRepository userRepository,UtilityService utilityService) {
    this.userRepository = userRepository;
    this.utilityService = utilityService;
  }

  @Transactional
  @Override
  public String resetPassword(ResetPassword resetPassword) {
    String password = userRepository.findPasswordByEmailId(resetPassword.getEmailId());

    if (BCrypt.checkpw(resetPassword.getOldPassword(), password)) {
      updateHashedPassword(resetPassword);
      return "Password Updated";
    } else {
      return "OldPassword is Not Matching";
    }
  }


  @Override
  public String passwordActivationLink(String emailId) {
    utilityService.sendActivationLink(emailId, 0);
    return "Password Reset link sent to your email";
  }

  @Override
  @Transactional
  public String UpdatePassword(ResetPassword resetPassword) {
    updateHashedPassword(resetPassword);
    return "New Password is Updated";
  }

  @Override
  public String getEmailIdForNewPassword(String token) {
    claim = utilityService.retrieveDataFromClaim(token);
    return claim.getSubject().split(" ")[0];
  }

  private void updateHashedPassword(ResetPassword resetPassword) {
    String hashedPassword = BCrypt.hashpw(resetPassword.getNewPassword(), BCrypt.gensalt());
    System.out.println(hashedPassword);
    userRepository
        .updatePassword(resetPassword.getEmailId(), hashedPassword);
  }


}
