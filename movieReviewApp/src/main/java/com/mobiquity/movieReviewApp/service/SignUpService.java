package com.mobiquity.movieReviewApp.service;


import com.mobiquity.movieReviewApp.Entity.UserProfile;
import com.mobiquity.movieReviewApp.model.UserInformation;
import java.util.Optional;


public interface SignUpService {

  /**
   * To Save user in the database with status false and send activation link
   * @param userInformation emailId, password,name
   * @return message or userException
   */
  String saveUser(UserInformation userInformation);

  /**
   * To register user in the database with status true
   * @param token JWT token
   * @return message or userException
   */
  String registerAccount(String token);

  Optional<UserProfile> findUserProfileByEmailId(String emailId);

}
