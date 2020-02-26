package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.Entity.UserProfile;
import com.mobiquity.movieReviewApp.exception.UserException;
import com.mobiquity.movieReviewApp.model.PasswordReset;
import com.mobiquity.movieReviewApp.model.PasswordUpdate;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Override
    @Transactional
    public String updateForgottenPasswordWithNewPassword(PasswordReset passwordAndToken) {
        String email = getEmailFromToken(passwordAndToken.getToken());
        UserProfile user = userRepository.findByEmailId(email).orElseThrow(
                () -> new UserException("token invalid")
        );

        return updateUserPassword(passwordAndToken.getPassword(), user.getEmailId());
    }

    private String updateUserPassword(String password, String emailId) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        userRepository.updatePassword(emailId, hashedPassword);
        return "Password Updated";
    }

    private String getEmailFromToken(String token) {
        try {
            return utilityService.retrieveDataFromClaim(token).getSubject();
        } catch (ExpiredJwtException e) {
            throw new UserException("Your activation link got expired");
        } catch (MalformedJwtException | SignatureException e) {
            throw new UserException("Activation link is not valid");
        }
    }


}
