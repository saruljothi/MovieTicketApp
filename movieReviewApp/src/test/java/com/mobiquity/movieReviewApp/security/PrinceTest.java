package com.mobiquity.movieReviewApp.security;

import javax.security.auth.Subject;
import java.security.Principal;

public class PrinceTest implements Principal {

    private String name;

    public PrinceTest(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean implies(Subject subject) {
        return true;
    }
}
