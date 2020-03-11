package com.mobiquity.movieReviewApp.security.controllers.accountmanagement;

import com.mobiquity.movieReviewApp.domain.accountmanagement.controller.GreetingsController;
import com.mobiquity.movieReviewApp.domain.accountmanagement.controller.PasswordController;
import com.mobiquity.movieReviewApp.domain.accountmanagement.controller.UserRegistrationController;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.PasswordManagementService;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.SignUpService;
import com.mobiquity.movieReviewApp.domain.accountmanagement.validation.UserValidator;
import com.mobiquity.movieReviewApp.security.controllers.ControllerSecurityTest;
import com.mobiquity.movieReviewApp.security.controllers.PrivateEndpointRule;
import com.mobiquity.movieReviewApp.security.controllers.PublicEndpointRule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = "com.mobiquity.movieReviewApp.domain.accountmanagement.controller")
@WebMvcTest(controllers = {
        GreetingsController.class,
        PasswordController.class,
        UserRegistrationController.class
})
@MockBeans(value = {
        @MockBean(PasswordManagementService.class),
        @MockBean(SignUpService.class),
        @MockBean(UserValidator.class),
})
class AccountManagementEndpointSecurityTests extends ControllerSecurityTest
        implements PrivateEndpointRule, PublicEndpointRule {

    @Override
    @ParameterizedTest
    @ValueSource(strings = {
            "/v1/forgotPassword/reset",
            "/v1/signUp/activationLink",
            "/v1/forgotPassword/newPassword",
            "/v1/signUp/login"
    })
    public void publicEndpoint_unAuthorizedUser_hasAccess(String url) throws Exception {
        assertTrue(super.isAuthenticated(url));
    }

    @Override
    @WithMockUser(roles = "USER")
    @ParameterizedTest
    @ValueSource(strings = {
            "/v1/forgotPassword/reset",
            "/v1/signUp/activationLink",
            "/v1/forgotPassword/newPassword",
            "/v1/signUp/login"
    })
    public void publicEndpoint_authorizedUser_hasAccess(String url) throws Exception {
        assertTrue(super.isAuthenticated(url));
    }

    @Override
    @ParameterizedTest
    @ValueSource(strings = {
            "/welcome",
            "/v1/changePassword"
    })
    public void privateEndpoint_unauthorizedUser_isUnauthorizedResponse(String url) throws Exception {
        assertFalse(super.isAuthenticated(url));
    }

    @Override
    @WithMockUser(roles = "USER")
    @ParameterizedTest
    @ValueSource(strings = {
            "/welcome",
            "/v1/changePassword"
    })
    public void privateEndpoint_authorizedUser_hasAccess(String url) throws Exception {
        assertTrue(super.isAuthenticated(url));
    }


}
