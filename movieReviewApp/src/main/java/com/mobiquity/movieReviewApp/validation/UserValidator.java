package com.mobiquity.movieReviewApp.validation;

import com.mobiquity.movieReviewApp.model.UserProfile;
import com.mobiquity.movieReviewApp.service.UserService;
import com.mobiquity.movieReviewApp.validation.utils.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

  private UserService userService;

  public UserValidator(UserService userService) {
    this.userService = userService;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return UserProfile.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    UserProfile userProfile = (UserProfile) o;

    String email = userProfile.getEmailId();
    EmailValidator emailValidator = new EmailValidator();

    ValidationUtils
        .rejectIfEmptyOrWhitespace(errors, "emailId", "Email field should not be empty.");
    if (!emailValidator.validate(userProfile.getEmailId())) {
      errors.rejectValue("emailId", "Please enter a valid e-mail address.");
    }
    if (userService.findUserProfileByEmailId(userProfile.getEmailId()) != null) {
      errors.rejectValue("emailId", "This email is already in use.");
    }

    ValidationUtils
        .rejectIfEmptyOrWhitespace(errors, "password", "Password field should not be empty.");
    if (userProfile.getPassword().length() < 8) {
      errors.rejectValue("password", "Password should be at least 8 characters.");
    }

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirmation",
        "Password confirmation field should not be empty.");
    if (!userProfile.getPasswordConfirmation().equals(userProfile.getPassword())) {
      errors.rejectValue("passwordConfirmation", "Passwords do not match.");
    }

  }
}
