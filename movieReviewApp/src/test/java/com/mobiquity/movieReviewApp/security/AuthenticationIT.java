package com.mobiquity.movieReviewApp.security;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.security.integrationtestresources.AuthenticationITSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.RequestBuilder;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class AuthenticationIT extends AuthenticationITSetup {
    private static final String PRIVATE_ENDPOINT = "/welcome/";
    private static final String PUBLIC_ENDPOINT = "/v1/signUp/";

    @Test
    public void checkIfUserIsAdded() {
        Optional<UserProfile> user = userRepository.findByEmailId(defaultUserProfile.getEmailId());

        assertEquals(defaultUserProfile.getName(), user.orElseThrow().getName());
    }

    @Test
    void publicEndpoint_unAuthorizedUser_hasAccess() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json"));

        mockMvc.perform(post(PUBLIC_ENDPOINT)
                .headers(headers)
                .content(defaultUserInformation.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void privateEndpoint_unAuthorizedUser_getsRedirected() throws Exception {
        String expectedUri = "http://localhost:0" + PRIVATE_ENDPOINT;

        mockMvc.perform(formLogin().user("email@email.com").password("pass"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(expectedUri));
    }

    @Test
    void userLogin_incorrectDetails_redirectTo_errorLogin() throws Exception {
        String expectedUri = "/login?error";

        mockMvc.perform(formLogin()
                .user(defaultUserProfile.getEmailId() + "invalidate")
                .password(defaultUserInformation.getPassword()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(expectedUri));
    }

    //By running the request once with a valid email and password we validate the user and create a session
    //We then use that session to do the subsequent request so we do not get redirected (iow we get a 200 OK)
    @Test
    void privateEndpoint_authorizedUser_getsRedirected() throws Exception {

        mockMvc.perform(get(PRIVATE_ENDPOINT).sessionAttrs(
                createAndRetrieveSession(
                        formLogin()
                                .user(defaultUserProfile.getEmailId())
                                .password(defaultUserProfile.getUnsecuredPassword()))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private Map<String, Object> createAndRetrieveSession(RequestBuilder requestBuilder) throws Exception {
        String securityContext = "SPRING_SECURITY_CONTEXT";

        HttpSession session = mockMvc.perform(requestBuilder).andExpect(status().isFound())
                .andReturn().getRequest().getSession();
        assertNotNull(session);
        return Map.of(securityContext, session.getAttribute(securityContext));
    }


}
