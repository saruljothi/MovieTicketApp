package com.mobiquity.movieReviewApp.domain.accountmanagement.validation;

import com.mobiquity.movieReviewApp.domain.accountmanagement.model.UserInformation;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.SignUpService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

  private SignUpService signUpService;

  public UserValidator(SignUpService signUpService) {
    this.signUpService = signUpService;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return UserInformation.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    UserInformation userInformation = (UserInformation) o;

    EmailValidator emailValidator = new EmailValidator();

    ValidationUtils
        .rejectIfEmptyOrWhitespace(errors, "emailId", "Email field should not be empty.");
    if (!emailValidator.validate(userInformation.getEmailId())) {
      errors.rejectValue("emailId", "Please enter a valid e-mail address.");
    }

    if (signUpService.findUserProfileByEmailId(userInformation.getEmailId()).isPresent()) {
      errors.rejectValue("emailId", "This email is already in use.");
    }

    ValidationUtils
        .rejectIfEmptyOrWhitespace(errors, "password", "Password field should not be empty.");
    if (userInformation.getPassword().length() < 2) {
      errors.rejectValue("password", "Password should be at least 8 characters.");
    }

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirmation",
        "Password confirmation field should not be empty.");
    if (!userInformation.getPasswordConfirmation().equals(userInformation.getPassword())) {
      errors.rejectValue("passwordConfirmation", "Passwords do not match.");
    }

  }
}
