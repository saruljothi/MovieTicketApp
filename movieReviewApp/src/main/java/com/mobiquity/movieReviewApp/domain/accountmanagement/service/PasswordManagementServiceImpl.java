package com.mobiquity.movieReviewApp.domain.accountmanagement.service;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.UserException;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordReset;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordUpdate;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
public class PasswordManagementServiceImpl implements PasswordManagementService {

    private UserRepository userRepository;
    private UtilityService utilityService;

    public PasswordManagementServiceImpl(UserRepository userRepository, UtilityService utilityService) {
        this.userRepository = userRepository;
        this.utilityService = utilityService;
    }

    @Transactional
    @Override
    @Secured("ROLE_USER")
    public String updatePassword(PasswordUpdate passwordUpdate) {
        String password = userRepository.findPasswordByEmailId(passwordUpdate.getEmailId());

        if (password != null && BCrypt.checkpw(passwordUpdate.getOldPassword(), password)) {
            return updateUserPassword(
                    passwordUpdate.getNewPassword(),
                    passwordUpdate.getEmailId()
            );
        } else {
            throw new UserException("OldPassword is Not Matching");
        }
    }

    @Override
    public String forgotPasswordLink(String emailId) {
        Optional<UserProfile> user = userRepository.findByEmailId(emailId);
        utilityService.sendPasswordForgotLink(user.orElseThrow(
                () -> new UserException("No user with that email exists")));

        return "Password Reset link sent to your email";
    }

    //If password that made up first token matches password of db, then can change password, else assume that token
    //has already been used.
    @Override
    @Transactional
    public String updateForgottenPasswordWithNewPassword(PasswordReset passwordAndToken) {

        Map<String, String> tokenUnwrapped = utilityService.unWrapToken(passwordAndToken.getToken());
        String email = tokenUnwrapped.get("email");
        String password = tokenUnwrapped.get("password");

        UserProfile user = userRepository.findByEmailId(email).orElseThrow(
                () -> new UserException("token invalid")
        );

        if (password.equals(user.getPassword())) {
            return updateUserPassword(passwordAndToken.getPassword(), user.getEmailId());
        }
        throw new UserException("This token has been used already");
    }

    private String updateUserPassword(String password, String emailId) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        userRepository.updatePassword(emailId, hashedPassword);
        return "Password Updated";
    }


}
