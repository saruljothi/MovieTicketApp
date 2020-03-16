package com.mobiquity.movieReviewApp.domain.accountmanagement.validation;

import com.mobiquity.movieReviewApp.domain.accountmanagement.model.UserInformation;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.SignUpService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

  private SignUpService signUpService;
  private MessageSource messageSource;
  private String emailId ="emailId";
  public UserValidator(SignUpService signUpService,
      MessageSource messageSource) {
    this.signUpService = signUpService;
    this.messageSource = messageSource;
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
        .rejectIfEmptyOrWhitespace(errors, emailId, messageSource.getMessage("emailId.empty",null,
            LocaleContextHolder.getLocale()));
    if (!emailValidator.validate(userInformation.getEmailId())) {
      errors.rejectValue(emailId, messageSource.getMessage("emailId.not.valid",null,LocaleContextHolder.getLocale()));
    }

    if (signUpService.findUserProfileByEmailId(userInformation.getEmailId()).isPresent()) {
      errors.rejectValue(emailId, messageSource.getMessage("emailId.in.use",null,LocaleContextHolder.getLocale()));
    }

    ValidationUtils
        .rejectIfEmptyOrWhitespace(errors, "password", messageSource.getMessage("password.empty",null,
            LocaleContextHolder.getLocale()));
    if (userInformation.getPassword().length() < 2) {
      errors.rejectValue("password", messageSource.getMessage("password.length",null,LocaleContextHolder.getLocale()));
    }

    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirmation",
        messageSource.getMessage("passwordconfirmation.empty",null,LocaleContextHolder.getLocale()));
    if (!userInformation.getPasswordConfirmation().equals(userInformation.getPassword())) {
      errors.rejectValue("passwordConfirmation", messageSource.getMessage("passwords.no.match",null,
          LocaleContextHolder.getLocale()));
    }

  }
}
