package com.mobiquity.movieReviewApp.domain.accountmanagement.service;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.PasswordException;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.UserException;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UtilityService {

  private static final int EXPIRATION_ONE_DAY = 1000 * 60 * 60 * 24;
  private static final int EXPIRATION_THIRTY_MINUTES = 1000 * 60 * 30;
  private Dotenv dotenv;
  private JavaMailSender javaMailSender;
  private SimpleMailMessage email = new SimpleMailMessage();

  public UtilityService(Dotenv dotenv,
      JavaMailSender javaMailSender) {
    this.dotenv = dotenv;
    this.javaMailSender = javaMailSender;
  }

  public Claims retrieveDataFromClaim(String token) {
    return Jwts.parser().setSigningKey(dotenv.get("SECRET")).parseClaimsJws(token).getBody();
  }

  public void sendActivationLink(String emailId, Long userId) {
    try {
      email.setTo(emailId);
      email.setSubject("activation link");
      email.setText(
          "activation link valid for 24 hrs" + "\n"
              + " http://localhost:8086/v1/signUp/activationLink?token="
              + generateJwtToken(emailId, userId, EXPIRATION_ONE_DAY));
      javaMailSender.send(email);
    } catch (MailException me) {
      throw new UserException("Unable to Send ActivationLink to your EmailId");
    }
  }

  public void sendPasswordForgotLink(UserProfile user) {
    try {
      email.setTo(user.getEmailId());
      email.setSubject("Password reset");
      email.setText(
          "reset link valid for 30 minutes" + "\n"
              + " http://localhost:8086/v1/forgotPassword/resetLink?token="
              + generateJwtToken(user.getEmailId(), user.getUserId(), EXPIRATION_THIRTY_MINUTES));
      javaMailSender.send(email);
    } catch (MailException me) {
      throw new PasswordException("Unable to Send ForgotPasswordActivationLink to your EmailId");
    }
  }


  private String generateJwtToken(String emailId, long userId, int expiration) {

    return Jwts.builder().setClaims(new HashMap<>())
        .setSubject(emailId + " " + userId)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration)).signWith(
            SignatureAlgorithm.HS512, dotenv.get("SECRET")).compact();
  }


}
