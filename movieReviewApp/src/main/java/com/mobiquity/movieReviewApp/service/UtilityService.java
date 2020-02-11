package com.mobiquity.movieReviewApp.service;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UtilityService {

  private Dotenv dotenv;
  private static final int EXPIRATION = 60 * 24 * 60 * 1000;
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
        "activation link valid for 24 hrs" + "\n" + " http://localhost:8086/v1/activationLink?token="
            + generateJwtToken(emailId, userId));
    javaMailSender.send(email);
  }

  String generateJwtToken(String emailId, long userId) {
    Map<String, Object> claims = new HashMap<>();
    return Jwts.builder().setClaims(claims)
        .setSubject(emailId + " " + userId)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION)).signWith(
            SignatureAlgorithm.HS512, dotenv.get("secret")).compact();
  }


}
