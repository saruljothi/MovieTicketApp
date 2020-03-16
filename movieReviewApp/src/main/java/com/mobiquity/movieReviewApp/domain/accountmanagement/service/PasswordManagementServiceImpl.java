package com.mobiquity.movieReviewApp.domain.accountmanagement.service;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.PasswordException;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.UserException;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordReset;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordUpdate;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
public class PasswordManagementServiceImpl implements PasswordManagementService {

    private UserRepository userRepository;
    private UtilityService utilityService;
    private MessageSource messageSource; //For reading messages from message.properties

    public PasswordManagementServiceImpl(UserRepository userRepository,
        UtilityService utilityService, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.utilityService = utilityService;
        this.messageSource = messageSource;
    }

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
      throw new PasswordException(messageSource.getMessage("user.password.oldpassword.not.match",null, LocaleContextHolder.getLocale()));
    }
    throw new PasswordException("Your emailId is invalid");

  }

  @Override
  @Transactional
  public String forgotPasswordLink(String emailId) {
    UserProfile user = userRepository.findByEmailId(emailId)
        .orElseThrow(() -> new PasswordException(messageSource.getMessage("user.password.not.valid.email",null,
            LocaleContextHolder.getLocale())));

    utilityService.sendPasswordForgotLink(user);
    user.setForgotPasswordStatus(false);
    userRepository.save(user);
    return messageSource.getMessage("user.password.link.activate",null,LocaleContextHolder.getLocale());
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
          .orElseThrow(() -> new PasswordException(messageSource.getMessage("user.password.token.invalid",null,
              LocaleContextHolder.getLocale())));


      if (!user.isForgotPasswordStatus()) {
        user.setForgotPasswordStatus(true);
        user.setPassword(passwordAndToken.getPassword());
        return updateUserPassword(user);
      } else {
        throw new PasswordException(messageSource.getMessage("user.password.updated.already",null,LocaleContextHolder.getLocale()));
      }
    } catch (ExpiredJwtException e) {
      throw new PasswordException(messageSource.getMessage("user.link.expire",null,LocaleContextHolder.getLocale()));
    } catch (MalformedJwtException | SignatureException e) {
      throw new PasswordException(messageSource.getMessage("user.link.not.valid",null,LocaleContextHolder.getLocale()));
    }
  }

  private String updateUserPassword(UserProfile user) {
    String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
    user.setPassword(hashedPassword);
    userRepository.save(user);
    return messageSource.getMessage("user.password.update.success",null,LocaleContextHolder.getLocale());
  }


}
