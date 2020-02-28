package com.mobiquity.movieReviewApp.security;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthenticationIT {

    private MockMvc mockMvc;

    @Autowired
    public AuthenticationIT(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void unauthorisedUser_privateDomain_unauthorized() throws Exception {
        mockMvc.perform(get("/welcome/"))
                .andExpect(status().isFound());
    }

    @Test
    void unauthorisedUser_publicDomain_success() throws Exception {
        mockMvc.perform(post("/v1/signUp/"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/v1/forgotPassword/reset", "/v1/signUp/activationLink"})
    void unauthorisedUser_GET_publicDomain_badRequest_parametersMissing(String someValue) throws Exception {
        System.out.println(someValue);
        mockMvc.perform(get(someValue))
                .andExpect(status().isBadRequest());
    }

    @Test
    void test() throws Exception{
        mockMvc.perform(put("/v1/changePassword/"))
                .andExpect(status().isForbidden());
    }

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



    //OPEN ENDPOINTS

    // /v1/forgotPassword/reset  --GET
    // /v1/forgotPassword/newPassword  --PUT

    // /v1/signUp/activationLink  --GET
    // /v1/signUp/login  --POST



}
