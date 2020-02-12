package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.exception.UserException;
import com.mobiquity.movieReviewApp.model.ForgotPassword;
import com.mobiquity.movieReviewApp.model.ResetPassword;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
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

    if (password != null && BCrypt.checkpw(resetPassword.getOldPassword(), password)) {
      updateHashedPassword(resetPassword.getNewPassword(),resetPassword.getEmailId());
      return "Password Updated";
    } else {
      throw new UserException("OldPassword is Not Matching");
    }
  }


  @Override
  public String passwordActivationLink(String emailId) {
    utilityService.sendActivationLink(emailId, 0);
    return "Password Reset link sent to your email";
  }

  @Override
  @Transactional
  public String UpdatePassword(ForgotPassword forgotPassword) {
    updateHashedPassword(forgotPassword.getPassword(),getEmailIdForNewPassword(forgotPassword.getToken()));
    return "New Password is Updated";
  }

  @Override
  public String getEmailIdForNewPassword(String token) {
    try {
      claim = utilityService.retrieveDataFromClaim(token);
      return claim.getSubject().split(" ")[0];
    } catch (ExpiredJwtException e) {
      throw new UserException("Your activation link got expired");
    } catch (MalformedJwtException e) {
      throw new UserException( "Activation link is not valid");
    }
  }

  private void updateHashedPassword(String password,String emailId) {
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    System.out.println(hashedPassword);
    userRepository
        .updatePassword(emailId, hashedPassword);
  }


}
