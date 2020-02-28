package com.mobiquity.movieReviewApp.security.controllers;


import com.mobiquity.movieReviewApp.domain.accountmanagement.service.PasswordManagementService;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.SignUpService;
import com.mobiquity.movieReviewApp.domain.accountmanagement.validation.UserValidator;
import com.mobiquity.movieReviewApp.security.AuthenticationConfig;
import com.mobiquity.movieReviewApp.security.TestOneConfig;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {
        TestOneConfig.class,
        ApplicationContext.class,
        AuthenticationConfig.class})
@MockBeans(value = {
        @MockBean(PasswordManagementService.class),
        @MockBean(SignUpService.class),
        @MockBean(UserValidator.class),
})
public abstract class ControllerSecurityTest {

}
