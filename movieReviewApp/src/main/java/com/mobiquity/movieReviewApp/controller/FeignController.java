package com.mobiquity.movieReviewApp.controller;

import com.mobiquity.movieReviewApp.client.OmdbFeignClient;
import com.mobiquity.movieReviewApp.model.omdb.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

  @Autowired
  OmdbFeignClient omdbFeignClient;

  @GetMapping(value = "/movies")
  public Search getMovies()
  {
    return omdbFeignClient.getMovies("The Social NetWork", "53c922c5");
  }
}
