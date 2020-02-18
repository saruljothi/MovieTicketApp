package com.mobiquity.movieReviewApp.controller;

import com.mobiquity.movieReviewApp.Entity.Movie;
import com.mobiquity.movieReviewApp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content")
public class ContentController {

  private MovieService movieService;

  public ContentController(MovieService movieService) {
    this.movieService = movieService;
  }

  //@Autowired
  //private SeriesService seriesService;

  @PostMapping("/addMovie")
  public ResponseEntity<Object> addMovie(@RequestBody Movie movie) {
    return new ResponseEntity<>(movieService.addMovie(movie), HttpStatus.OK);
  }

  /*
  @PostMapping("/addSeries")
  public ResponseEntity<Object> addSeries(@RequestBody Series series) {
    return new ResponseEntity<>(new Success(seriesService.addSeries(series)), HttpStatus.OK);
  }

   */

}
