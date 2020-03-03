package com.mobiquity.movieReviewApp.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class OmdbServiceImplTest {

  @Autowired
  OmdbService omdbService;

  @Test
  public void checkIfResponseIsGoodFromOmdb()
  {
    omdbService.getMovies();
  }
}