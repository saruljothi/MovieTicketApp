package com.mobiquity.movieReviewApp.domain.accountmanagement.service;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.UserException;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.*;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UtilityService {

    private static final int EXPIRATION_ONE_DAY = 1000 * 60 * 60 * 24;
    private static final int EXPIRATION_THIRTY_MINUTES = 1000 * 60 * 30;
    private Dotenv dotenv;
    private JavaMailSender javaMailSender;
    private MessageSource messageSource;

    public UtilityService(Dotenv dotenv,
        JavaMailSender javaMailSender, MessageSource messageSource) {
        this.dotenv = dotenv;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
    }

  public Claims retrieveDataFromClaim(String token) {
    return Jwts.parser().setSigningKey(dotenv.get("SECRET")).parseClaimsJws(token).getBody();
  }

   public void sendActivationLink(String emailId, long userId) {
      try {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(emailId);
        email.setSubject("activation link");
        email.setText(
            "activation link valid for 24 hrs" + "\n"
                + " http://localhost:8086/v1/signUp/activationLink?token="
                + generateJwtToken(emailId, userId));
        javaMailSender.send(email);
      } catch (MailException me) {
        throw new UserException("Unable to Send ActivationLink to your EmailId");
      }
    }

    public void sendPasswordForgotLink(UserProfile user) {
      try{
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmailId());
        email.setSubject("Password reset");
        email.setText(
                messageSource.getMessage("user.password.link.valid.time",null,LocaleContextHolder.getLocale() )+ "\n"
                        + " http://localhost:8086/v1/forgotPassword/resetLink?token="
                        + generateJwtToken(user.getEmailId(), user.getPassword()));
        javaMailSender.send(email);
    }catch (MailException me) {
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
