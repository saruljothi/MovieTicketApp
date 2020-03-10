package com.mobiquity.movieReviewApp.domain.accountmanagement.controller;

import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordReset;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.PasswordUpdate;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.ResponseMovieApp;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.PasswordManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/v1")
public class PasswordController {

    private PasswordManagementService passwordManagementService;

    public PasswordController(PasswordManagementService passwordManagementService) {
        this.passwordManagementService = passwordManagementService;
    }

    @GetMapping("/forgotPassword/reset")
    public ResponseEntity<Object> forgotPassword(@RequestParam String emailId) {
        return new ResponseEntity<>(
                new ResponseMovieApp(Collections.singletonList(passwordManagementService.forgotPasswordLink(emailId))),
                HttpStatus.OK);
    }

    @PutMapping("/forgotPassword/newPassword")
    public ResponseEntity<Object> setNewPassword(@RequestBody PasswordReset passwordAndToken) {
        return new ResponseEntity<>(
                new ResponseMovieApp(
                        Collections.singletonList(
                                passwordManagementService.updateForgottenPasswordWithNewPassword(passwordAndToken))
                ), HttpStatus.OK);
    }

   /* @GetMapping("forgotPassword/resetLink")
    public RedirectView redirectToUrl(@RequestParam String token){
        RedirectView rv = new RedirectView();
        rv.setPropagateQueryParams(true);
        rv.setUrl("http://localhost:8086/v1/forgotPassword/resetLink");
         return rv;
    }*/

    @PutMapping("/changePassword")
    //Todo: Validate the user so only the user can change their own password
    public ResponseEntity<Object> updatePassword(@RequestBody PasswordUpdate passwordChangeAndAccount) {
        return new ResponseEntity<>(
                new ResponseMovieApp(
                        Collections.singletonList(passwordManagementService.updatePassword(passwordChangeAndAccount))
                ), HttpStatus.OK);
    }
}
