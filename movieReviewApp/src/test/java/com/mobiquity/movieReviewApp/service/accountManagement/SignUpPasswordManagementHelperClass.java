package com.mobiquity.movieReviewApp.service.accountManagement;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class SignUpPasswordManagementHelperClass {

  protected int expiration;

  protected String getToken(int expiration) {
    return Jwts.builder().setClaims(new HashMap<>())
        .setSubject("ds@gmail.com" + " " + 1L)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration)).signWith(
            SignatureAlgorithm.HS512, "testmobiquity").compact();
  }

  protected Claims getClaim(String token) {
    return Jwts.parser().setSigningKey("testmobiquity").parseClaimsJws(token).getBody();
  }

  public JavaMailSender javaMailSender() {

    Dotenv dotenv = Dotenv.load();
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);

    mailSender.setUsername("movie@gmail.com");
    mailSender.setPassword("password");

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");
    return mailSender;
  }
}
