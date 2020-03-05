package com.mobiquity.movieReviewApp.security.integrationtestresources;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class StubUserProfile extends UserProfile {

    private String unsecuredPassword;

    StubUserProfile(boolean status, String email, String password, String name) {
        super();
        super.setStatus(status);
        super.setEmailId(email);
        super.setName(name);
        this.unsecuredPassword = password;
        super.setPassword(setSecurePassword());
    }

    StubUserProfile(){
        this(true, "email@email.com", "pass", "name");
    }

    private String setSecurePassword() {
        return BCrypt.hashpw(this.unsecuredPassword, BCrypt.gensalt());
    }

    UserProfile returnStub() {
        UserProfile user = new UserProfile();
        user.setPassword(super.getPassword());
        user.setEmailId(super.getEmailId());
        user.setStatus(super.isStatus());
        user.setName(super.getName());
        return user;
    }

    public String getUnsecuredPassword() {
        return unsecuredPassword;
    }
}
