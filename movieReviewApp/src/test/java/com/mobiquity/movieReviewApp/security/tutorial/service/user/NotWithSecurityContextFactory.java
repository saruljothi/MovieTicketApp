package com.mobiquity.movieReviewApp.security.tutorial.service.user;

import org.springframework.security.core.context.SecurityContext;

public interface NotWithSecurityContextFactory<T> {
    public SecurityContext createSecurityContext(T data);
}
