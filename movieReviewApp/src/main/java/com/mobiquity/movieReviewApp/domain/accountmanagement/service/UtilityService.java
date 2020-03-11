package com.mobiquity.movieReviewApp.domain.accountmanagement.service;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.UserException;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.*;
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
                        + generateJwtToken(user.getEmailId(), user.getPassword()));
        javaMailSender.send(email);
    }

    String generateJwtToken(String emailId, long userId) {
        return Jwts.builder().setClaims(new HashMap<>())
                .setSubject(emailId + " " + userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_ONE_DAY)).signWith(
                        SignatureAlgorithm.HS512, dotenv.get("secret")).compact();
    }

    String generateJwtToken(String emailId, String password) {
        return Jwts.builder().setClaims(new HashMap<>())
                .setSubject(emailId + " " + password)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_THIRTY_MINUTES)).signWith(
                        SignatureAlgorithm.HS512, dotenv.get("secret")).compact();
    }

    public Map<String, String> unWrapToken(String token) {
        Map<String, String> tokenInfo = new HashMap<>();
        String[] tokenContents;
        try {
            tokenContents = retrieveDataFromClaim(token).getSubject().split(" ");
        } catch (ExpiredJwtException e) {
            throw new UserException("Your activation link got expired");
        } catch (MalformedJwtException | SignatureException e) {
            throw new UserException("Activation link is not valid");
        }

        tokenInfo.put("email", tokenContents[0]);
        tokenInfo.put("password", tokenContents[1]);
        return tokenInfo;
    }
}
