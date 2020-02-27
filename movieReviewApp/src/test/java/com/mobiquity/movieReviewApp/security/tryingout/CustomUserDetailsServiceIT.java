package com.mobiquity.movieReviewApp.security.tryingout;

import com.mobiquity.movieReviewApp.security.AuthenticationConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.security.Principal;

//@ContextConfiguration(classes = {AuthenticationConfig.class,
//        SecurityConfig.class})
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@WebAppConfiguration
public class CustomUserDetailsServiceIT {

    private AuthenticationConfig securityConfig;
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsServiceIT.class);;

    @Autowired
    public CustomUserDetailsServiceIT(AuthenticationConfig securityConfig) {
        this.securityConfig = securityConfig;
    }


    @Test
    void userAuthenticated() {

        Principal testPrinc = new PrinceTest("Richard");
        Authentication testAuth = new AuthTest(testPrinc);

        try {
            securityConfig.authenticationManagerBean().authenticate(testAuth);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        System.out.println("Stop");
    }

    @Test
    void unauthorisedUser_publicDomain_success() {
        System.out.println("Stop");

    }

    @Test
    void unauthorisedUser_privateDomain_redirectedToLogin() {

    }

    @Test
    void unauthorisedUser_signIn_incorrectDetails_throwsError() {

    }

    @Test
    void unauthorisedUser_signIn_correctDetails_success() {

    }

    @Test
    void unauthorisedUser_securedMethod_failure_exceptionThrown() {

    }

    @Test
    void unauthorisedUser_logout_failure() {

    }

    @Test
    void authorisedUser_publicDomain_success() {

    }

    @Test
    void authorisedUser_privateDomain_success() {

    }

    @Test
    void authorisedUser_privateMethod_success() {

    }

    @Test
    void authorisedUser_logOut_success_and_redirected() {

    }

    @Test
    void loggedOutUser_accessPublic_success() {

    }

    @Test
    void loggedOutUser_accessPrivate_redirectToLogin() {

    }

    @AfterEach
    void tearDown() {

    }

}
