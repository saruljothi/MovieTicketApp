package com.mobiquity.movieReviewApp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;

public class SignUpPasswordManagementHelperClass {
  protected int expiration;
  protected String getToken(int expiration) {
    return Jwts.builder().setClaims(new HashMap<>())
        .setSubject("ds@gmail.com"+" "+1L)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration)).signWith(
            SignatureAlgorithm.HS512, "testmobiquity").compact();
  }

  protected Claims getClaim(String token) {
    return Jwts.parser().setSigningKey("testmobiquity").parseClaimsJws(token).getBody();
  }
}
