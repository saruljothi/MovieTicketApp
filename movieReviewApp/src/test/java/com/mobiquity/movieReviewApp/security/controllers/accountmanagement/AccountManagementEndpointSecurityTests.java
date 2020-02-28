package com.mobiquity.movieReviewApp.security.controllers.accountmanagement;


import com.mobiquity.movieReviewApp.domain.accountmanagement.controller.GreetingsController;
import com.mobiquity.movieReviewApp.domain.accountmanagement.controller.PasswordController;
import com.mobiquity.movieReviewApp.domain.accountmanagement.controller.UserRegistrationController;
import com.mobiquity.movieReviewApp.security.controllers.ControllerSecurityTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = "com.mobiquity.movieReviewApp.domain.accountmanagement.controller")
@WebMvcTest(controllers = {
        GreetingsController.class,
        PasswordController.class,
        UserRegistrationController.class
})
//@ContextConfiguration(classes = {
//        TestOneConfig.class,
//        ApplicationContext.class,
//        AuthenticationConfig.class
//})
//@MockBeans(value = {
//        @MockBean(PasswordManagementService.class),
//        @MockBean(SignUpService.class),
//        @MockBean(UserValidator.class),
//})
public class AccountManagementEndpointSecurityTests extends ControllerSecurityTest {

    //400 means that it has been found, but parameters are missing.. this is a fine test for checking auth

    //if user MUST BE AUTHORIZED then expect isFound() redirect 302
    //if user IS AUTHORIZED then expect either 200 or 400 (depending on parameters)
    //NO NEED TO DIFFERENTIATE BETWEEN GET, POST OR PUT. Authorized validation takes place before these endpoint are ever hit
    //not 401 or 302

    @Autowired
    private MockMvc mockMvc;

    //OPEN ENDPOINTS

//    "/v1/forgotPassword/reset"
//    "/v1/signUp/activationLink"
//    "/v1/forgotPassword/newPassword"
//    "/v1/signUp/login"

    //PROTECTED ENDPOINTS

    // /welcome  --GET
    // /v1/changePassword --PUT
    // /content/addMovie --POST
    // /content/movie --GET
    // /content/addSeries --POST
    // /content/series --GET
    // /watchlist/addMovie --POST
    // /watchlist/removeMovie --POST
    // /watchlist/addSeries --POST
    // /watchlist/removeSeries --POST

    // /v1/forgotPassword/reset  --GET
    // /v1/forgotPassword/newPassword  --PUT
    // /v1/signUp/activationLink  --GET
    // /v1/signUp/login  --POST

    @ParameterizedTest
    @ValueSource(strings = {
            "/v1/forgotPassword/reset",
            "/v1/signUp/activationLink",
            "/v1/forgotPassword/newPassword",
            "/v1/signUp/login",
            "/v1/changePassword"
    })
    void publicEndpoint_GET_unauthorizedUser_isNot401_or302(String url) throws Exception {
        int actualStatus = mockMvc.perform(get(url)).andReturn().getResponse().getStatus();
        assertNotEquals(302, actualStatus);
        assertNotEquals(401, actualStatus);
    }

    @Test
    void checkMore() throws Exception {
        mockMvc.perform(get("/v1/changePassword"))
                .andExpect(status().isOk());
    }


    @WithMockUser(roles = "USER")
    @Test
    void checkSomething() throws Exception {
        mockMvc.perform(get("/v1/signUp/activationLink").param("token", "tok"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "USER")
    @Test
    void checkPut() throws Exception {
        mockMvc.perform(put("/v1/changePassword"))
                .andExpect(status().isFound());
    }


//    @WithMockUser(roles = "USER")
//    @ParameterizedTest
//    @ValueSource(strings = {"/v1/forgotPassword/reset", "/v1/signUp/activationLink"})
//    void publicEndpoint_GET_authorizedUser_isOk(String url) throws Exception {
//        mockMvc.perform(get(url))
//                .andExpect(status().isOk());
//    }
//    @ParameterizedTest
//    void publicEndpoint_PUT_unauthorizedUser_isOk() {
//
//    }
//    @ParameterizedTest
//    void publicEndpoint_PUT_authorizedUser_isOk() {
//
//    }
//    @ParameterizedTest
//    void publicEndpoint_POST_unauthorizedUser_isOk() {
//
//    }
//    @ParameterizedTest
//    void publicEndpoint_POST_authorizedUser_isOk() {
//
//    }

    //    @Test
//    void check() throws Exception{
//        mockMvc.perform(get("/v1/signUp/test"))
//                .andExpect(status().isOk());
//    }
    @Test
    void check() throws Exception {
        mockMvc.perform(get("/welcome"))
                .andExpect(status().isOk());
    }


    @WithMockUser(roles = "USER")
    @Test
    public void endpoint_requiresAuthorization_authorizedUser_isOk() throws Exception {
        mockMvc.perform(get("/welcome/"))
                .andExpect(status().isOk());
    }


}
