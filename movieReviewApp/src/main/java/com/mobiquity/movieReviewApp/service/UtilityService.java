package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.Entity.UserProfile;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UtilityService {

    private static final int EXPIRATION = 60 * 24 * 60 * 1000;
    private Dotenv dotenv;
    private JavaMailSender javaMailSender;

    public UtilityService(Dotenv dotenv,
                          JavaMailSender javaMailSender) {
        this.dotenv = dotenv;
        this.javaMailSender = javaMailSender;
    }

    Claims retrieveDataFromClaim(String token) {
        return Jwts.parser().setSigningKey(dotenv.get("secret")).parseClaimsJws(token).getBody();
    }

    void sendActivationLink(String emailId, long userId) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(emailId);
        email.setSubject("activation link");
        email.setText(
                "activation link valid for 24 hrs" + "\n"
                        + " http://localhost:8086/v1/signUp/activationLink?token="
                        + generateJwtToken(emailId, userId));
        javaMailSender.send(email);
    }

    public void sendPasswordForgotLink(UserProfile user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmailId());
        email.setSubject("Password reset");
        email.setText(
                "reset link valid for 24 hrs" + "\n"
                        + " http://localhost:8086/v1/forgotPassword/resetLink?token="
                        + generateJwtToken(user.getEmailId()));
        javaMailSender.send(email);
    }

    String generateJwtToken(String emailId, long userId) {
        return Jwts.builder().setClaims(new HashMap<>())
                .setSubject(emailId + " " + userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION)).signWith(
                        SignatureAlgorithm.HS512, dotenv.get("secret")).compact();
    }
    String generateJwtToken(String emailId) {
      return Jwts.builder().setClaims(new HashMap<>())
              .setSubject(emailId)
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION)).signWith(
                      SignatureAlgorithm.HS512, dotenv.get("secret")).compact();
    }


}
