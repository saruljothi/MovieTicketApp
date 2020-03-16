package com.mobiquity.movieReviewApp.domain.content.controller;

import com.mobiquity.movieReviewApp.domain.accountmanagement.model.ResponseMovieApp;
import com.mobiquity.movieReviewApp.domain.content.entity.Movie;
import com.mobiquity.movieReviewApp.domain.content.entity.Series;
import com.mobiquity.movieReviewApp.domain.content.service.MovieService;
import com.mobiquity.movieReviewApp.domain.content.service.SeriesService;
import java.util.Collections;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
  private SeriesService seriesService;
  private MessageSource messageSource;

  public ContentController(MovieService movieService, SeriesService seriesService,
      MessageSource messageSource) {
    this.movieService = movieService;
    this.seriesService = seriesService;
    this.messageSource = messageSource;
  }

  @PostMapping("/addMovie")
  public ResponseEntity<ResponseMovieApp> addMovie(@RequestBody Movie movie) {
    return new ResponseEntity<>(
        new ResponseMovieApp(Collections.singletonList(movieService.addMovie(movie))),
        HttpStatus.OK);

  }

  @GetMapping("/movie")
  public ResponseEntity<Object> getMovie(@RequestParam(name = "name") String name) {
    Movie movie = movieService.getMovie(name);
    if (movie != null) {
      return new ResponseEntity<>(movie, HttpStatus.OK);
    }
    return new ResponseEntity<>(new ResponseMovieApp(
        Collections.singletonList(messageSource.getMessage("movie.not.found", null,
            LocaleContextHolder.getLocale()))), HttpStatus.NOT_FOUND);
  }


  @PostMapping("/addSeries")
  public ResponseEntity<Object> addSeries(@RequestBody Series series) {
    return new ResponseEntity<>(
        new ResponseMovieApp(Collections.singletonList(seriesService.addSeries(series))),
        HttpStatus.OK);
  }

  @GetMapping("/series")
  public ResponseEntity<Object> getSeries(@RequestParam(name = "name") String name) {
    Series series = seriesService.getSeries(name);
    if (series != null) {
      return new ResponseEntity<>(series, HttpStatus.OK);
    }
    return new ResponseEntity<>(new ResponseMovieApp(
        Collections.singletonList(messageSource.getMessage("series.not.found", null,
            LocaleContextHolder.getLocale()))), HttpStatus.NOT_FOUND);
  }

}
