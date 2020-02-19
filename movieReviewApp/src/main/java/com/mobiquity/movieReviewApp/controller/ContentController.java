package com.mobiquity.movieReviewApp.controller;

import com.mobiquity.movieReviewApp.Entity.Movie;
import com.mobiquity.movieReviewApp.model.ResponseMovieApp;
import com.mobiquity.movieReviewApp.service.MovieService;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping("/movie")
  public ResponseEntity<Object> getMovie(@RequestParam(name = "name") String name) {
    if(movieService.getMovie(name) != null){
      return new ResponseEntity<>(movieService.getMovie(name), HttpStatus.OK);
    }
    return new ResponseEntity<>(new ResponseMovieApp(Arrays.asList("Movie not found")), HttpStatus.NOT_FOUND);
  }

  /*
  @PostMapping("/addSeries")
  public ResponseEntity<Object> addSeries(@RequestBody Series series) {
    return new ResponseEntity<>(new Success(seriesService.addSeries(series)), HttpStatus.OK);
  }

   */

}
