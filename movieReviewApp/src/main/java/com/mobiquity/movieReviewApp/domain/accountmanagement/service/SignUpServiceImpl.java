package com.mobiquity.movieReviewApp.domain.accountmanagement.service;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.UserException;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.UserInformation;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SignUpServiceImpl implements SignUpService {

    private static final int FIXEDRATE = 60 * 60 * 24 * 7 * 1000;//scheduler for every week
    private UserRepository userRepository;
    private Claims claim;
    private UtilityService utilityService;

    public SignUpServiceImpl(UserRepository userRepository, UtilityService utilityService) {
        this.userRepository = userRepository;
        this.utilityService = utilityService;
    }

    @Transactional
    @Override
    public String saveUser(UserInformation userInformation) {
        try {
            userInformation.setPassword(BCrypt.hashpw(userInformation.getPassword(), BCrypt.gensalt()));
            UserProfile user = userRepository.save(setUserProfile(userInformation));
            utilityService.sendActivationLink(user.getEmailId(), user.getUserId());
            return "Activate your link";
        } catch (DataIntegrityViolationException e) {
            throw new UserException("Your email is already registered.");
        }
    }

    @Transactional
    @Override
    public String registerAccount(String token) {
        try {
            claim = utilityService.retrieveDataFromClaim(token);
            long id = Long.parseLong(claim.getSubject().split(" ")[1]);
            userRepository.updateStatus(id);
            return "You are Registered Successfully";
        } catch (ExpiredJwtException e) {
            throw new UserException("Your activation link got expired");
        } catch (MalformedJwtException | SignatureException e) {
            throw new UserException("Activation link is not valid");
        }
    }

    @Scheduled(fixedRate = FIXEDRATE)
    @Transactional
    public void setScheduler() {
        userRepository.deleteByCreatedOnAndStatus(LocalDateTime.now().minusDays(1));
    }


    public UserProfile setUserProfile(UserInformation userInformation) {
        UserProfile userProfile = new UserProfile();
        userProfile.setEmailId(userInformation.getEmailId());
        userProfile.setName(userInformation.getName());
        userProfile.setPassword(userInformation.getPassword());
        return userProfile;
    }

    @Override
    public Optional<UserProfile> findUserProfileByEmailId(String emailId) {
        return userRepository.findByEmailId(emailId);
    }

}
