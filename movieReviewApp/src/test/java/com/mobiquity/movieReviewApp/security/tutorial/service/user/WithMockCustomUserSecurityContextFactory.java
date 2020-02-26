package com.mobiquity.movieReviewApp.security.tutorial.service.user;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        CustomUserDetails principal = new CustomUserDetails(customUser.name(), customUser.email());
        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, customUser.password(), principal.getAuthorities());
        context.setAuthentication(auth);

        return context;
    }
}
