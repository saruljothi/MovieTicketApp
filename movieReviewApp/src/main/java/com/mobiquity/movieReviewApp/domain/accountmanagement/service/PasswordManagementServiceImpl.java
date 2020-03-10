package com.mobiquity.movieReviewApp.domain.accountmanagement.service;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.UserException;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordReset;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordUpdate;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class

PasswordManagementServiceImpl implements PasswordManagementService {

  private UserRepository userRepository;
  private UtilityService utilityService;

  public PasswordManagementServiceImpl(UserRepository userRepository,
      UtilityService utilityService) {
    this.userRepository = userRepository;
    this.utilityService = utilityService;
  }

  @Transactional
  @Override
  @Secured("ROLE_USER")
  public String updatePassword(PasswordUpdate passwordUpdate) {
    String password = userRepository.findPasswordByEmailId(passwordUpdate.getEmailId());

    if (password != null && BCrypt.checkpw(passwordUpdate.getOldPassword(), password)) {
      return updateUserPassword(
          passwordUpdate.getNewPassword(),
          passwordUpdate.getEmailId()
      );
    } else {
      throw new UserException("OldPassword is Not Matching");
    }
  }

  @Override
  @Transactional
  public String forgotPasswordLink(String emailId) {
    Optional<UserProfile> user = userRepository.findByEmailId(emailId);
    utilityService.sendPasswordForgotLink(user.orElseThrow(
        () -> new UserException("No user with that email exists")));
    userRepository.updatePasswordStatusByEmailId(user.get().getEmailId(), false);
    return "Password Reset link sent to your email";
  }

  //If password that made up first token matches password of db, then can change password, else assume that token
  //has already been used.
  @Override
  @Transactional
  public String updateForgottenPasswordWithNewPassword(PasswordReset passwordAndToken) {
      try {
          String emailIdFromToken =
              utilityService.retrieveDataFromClaim(passwordAndToken.getToken()).getSubject()
                  .split(" ")[0];

          UserProfile user = userRepository.findByEmailId(emailIdFromToken).orElseThrow(
              () -> new UserException("token invalid!")
          );
          if (!user.isForgotPasswordStatus()) {
              userRepository.updatePasswordStatusByEmailId(user.getEmailId(), true);
              return updateUserPassword(passwordAndToken.getPassword(), user.getEmailId());
          } else {
              return "password Already updated!";
          }
      } catch (ExpiredJwtException e) {
          throw new UserException("Your activation link got expired");
      } catch (MalformedJwtException | SignatureException e) {
          throw new UserException("Activation link is not valid");
      }
  }

  private String updateUserPassword(String password, String emailId) {
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    userRepository.updatePassword(emailId, hashedPassword);
    return "Password Updated";
  }


}
