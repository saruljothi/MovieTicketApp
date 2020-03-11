package com.mobiquity.movieReviewApp.security.integrationtestresources;

import com.mobiquity.movieReviewApp.domain.accountmanagement.model.UserInformation;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.SignUpService;
import com.mobiquity.movieReviewApp.domain.accountmanagement.validation.UserValidator;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@MockBeans(value = {
        @MockBean(SignUpService.class),
        @MockBean(UserValidator.class),
})
public abstract class AuthenticationITSetup {
    protected static String urlPrefix;

    @LocalServerPort
    protected Integer randomServerPort;
    protected UserInformation defaultUserInformation;
    protected StubUserProfile defaultUserProfile;
    protected MockMvc mockMvc;
    protected UserRepository userRepository;

    public AuthenticationITSetup() {
        this.defaultUserProfile = new StubUserProfile();
        this.defaultUserInformation = new StubUserInformation();
    }

    @Autowired
    void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    void setMockMvc(MockMvc mock) {
        this.mockMvc = mock;
    }

    @BeforeEach
    void setUp() {
        if (urlPrefix == null) {
            urlPrefix = "http://localhost:" + randomServerPort;
        }
        addUserToUserRepository();
    }

    @AfterEach
    void removeUserProfile() {
        userRepository.deleteAll();
    }

    @Transactional
    public void addUserToUserRepository() {
        userRepository.save(defaultUserProfile.returnStub());
    }
}
