package com.mobiquity.movieReviewApp.security;

import com.mobiquity.movieReviewApp.security.tryingout.AuthTest;
import com.mobiquity.movieReviewApp.security.tryingout.PrinceTest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.security.Principal;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private Authentication authentication;

    public CustomAuthenticationProvider(Authentication authentication) {
        this.authentication = authentication;
    }

    Authentication getAuthentication() {
        return authenticate(this.authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return this.authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
