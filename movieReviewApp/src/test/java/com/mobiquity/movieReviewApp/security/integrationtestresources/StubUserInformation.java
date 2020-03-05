package com.mobiquity.movieReviewApp.security.integrationtestresources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.UserInformation;

class StubUserInformation extends UserInformation {

    StubUserInformation() {
        this.setEmailId("notsaved@email.com");
        this.setName("unsaved name");
        this.setPassword("unsaved password");
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
