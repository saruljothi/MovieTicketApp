package com.mobiquity.movieReviewApp.security;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class SuccessAuthRedirectHandler extends SimpleUrlAuthenticationSuccessHandler {

    public SuccessAuthRedirectHandler(String defaultTargetUrl) {
        super();
        setDefaultTargetUrl(defaultTargetUrl);
    }

}
