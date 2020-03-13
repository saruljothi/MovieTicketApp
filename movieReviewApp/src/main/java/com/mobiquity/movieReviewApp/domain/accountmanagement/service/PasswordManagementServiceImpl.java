package com.mobiquity.movieReviewApp.domain.accountmanagement.service;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.PasswordException;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.UserException;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordReset;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordUpdate;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswordManagementServiceImpl implements PasswordManagementService {

  private final UserRepository userRepository;
  private final UtilityService utilityService;


  @Transactional
  @Override
  @Secured("ROLE_USER")
  public String updatePassword(PasswordUpdate passwordUpdate) {
    if (getContext().getAuthentication().getName().equals(passwordUpdate
        .getEmailId())) {
      UserProfile user = userRepository.findByEmailId(passwordUpdate.getEmailId()).orElseThrow(
          UserException::new);
      if (null != user.getPassword() && BCrypt
          .checkpw(passwordUpdate.getOldPassword(), user.getPassword())) {
        user.setPassword(passwordUpdate.getNewPassword());
        return updateUserPassword(user);
      }
      throw new PasswordException("OldPassword is Not Matching");
    }
    throw new PasswordException("Your emailId is invalid");

  }

  @Override
  @Transactional
  public String forgotPasswordLink(String emailId) {
    UserProfile user = userRepository.findByEmailId(emailId)
        .orElseThrow(() -> new PasswordException("No user with that email exists"));

    utilityService.sendPasswordForgotLink(user);
    user.setForgotPasswordStatus(false);
    userRepository.save(user);
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

      UserProfile user = userRepository.findByEmailId(emailIdFromToken)
          .orElseThrow(() -> new PasswordException("token invalid!"));

      if (!user.isForgotPasswordStatus()) {
        user.setForgotPasswordStatus(true);
        user.setPassword(passwordAndToken.getPassword());
        return updateUserPassword(user);
      } else {
        throw new PasswordException("password Already updated!");
      }
    } catch (ExpiredJwtException e) {
      throw new PasswordException("Your activation link got expired");
    } catch (MalformedJwtException | SignatureException e) {
      throw new PasswordException("Activation link is not valid");
    }
  }

  private String updateUserPassword(UserProfile user) {
    String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
    user.setPassword(hashedPassword);
    userRepository.save(user);
    return "Password Updated";
  }


}
