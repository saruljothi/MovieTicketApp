package com.mobiquity.movieReviewApp.domain.content.service;

public enum ErrorMessages {
  NO_MOVIE_PRESENT("no moviePresent");
  private final String message;
  ErrorMessages(String s) {
    this.message = s;
  }
  @Override
  public String toString(){
    return message;
  }
}
