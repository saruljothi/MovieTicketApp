package com.mobiquity.movieReviewApp.domain.accountmanagement.service;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.UserException;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordReset;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordUpdate;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private MessageSource messageSource; //For reading messages from message.properties

    public PasswordManagementServiceImpl(UserRepository userRepository,
        UtilityService utilityService, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.utilityService = utilityService;
        this.messageSource = messageSource;
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
            throw new UserException(messageSource.getMessage("user.password.oldpassword.notmatch",null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public String forgotPasswordLink(String emailId) {
        Optional<UserProfile> user = userRepository.findByEmailId(emailId);
        utilityService.sendPasswordForgotLink(user.orElseThrow(
                () -> new UserException(messageSource.getMessage("user.password.notvalid.email",null,LocaleContextHolder.getLocale()))));

        return messageSource.getMessage("user.password.link.activate",null,LocaleContextHolder.getLocale());
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
            () -> new UserException(messageSource.getMessage("user.password.token.invalid",null,LocaleContextHolder.getLocale())));

        if (password.equals(user.getPassword())) {
            return updateUserPassword(passwordAndToken.getPassword(), user.getEmailId());
        }
        throw new UserException(messageSource.getMessage("user.password.token.used",null,LocaleContextHolder.getLocale()));
    }

    private String updateUserPassword(String password, String emailId) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        userRepository.updatePassword(emailId, hashedPassword);
        return messageSource.getMessage("user.password.update.success",null,LocaleContextHolder.getLocale());
    }


}
