package com.mobiquity.movieReviewApp.domain.content.controller;

import com.mobiquity.movieReviewApp.domain.accountmanagement.model.ResponseMovieApp;
import com.mobiquity.movieReviewApp.domain.content.service.WatchlistService;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/watchlist")
public class WatchlistController {

  private WatchlistService watchlistService;

  public WatchlistController(WatchlistService watchlistService) {
    this.watchlistService = watchlistService;
  }

  @PostMapping("/addMovie")
  public ResponseEntity<ResponseMovieApp> addMovieToUserWatchlist(
      @RequestParam(name = "userEmail") String emailId,
      @RequestParam(name = "movieName") String movie) {
    return new ResponseEntity<>(
        new ResponseMovieApp(
                Collections.singletonList(watchlistService.addMovieToUserWatchlist(emailId, movie))),
        HttpStatus.OK);
  }

  @PostMapping("/removeMovie")
  public ResponseEntity<ResponseMovieApp> removeMovieToUserWatchlist(
      @RequestParam(name = "userEmail") String emailId,
      @RequestParam(name = "movieName") String movie) {
    return new ResponseEntity<>(
        new ResponseMovieApp(
                Collections.singletonList(watchlistService.removeMovieFromAUserWatchlist(emailId, movie))),
        HttpStatus.OK);
  }

  @PostMapping("/addSeries")
  public ResponseEntity<ResponseMovieApp> addSeriesToUserWatchlist(
      @RequestParam(name = "userEmail") String emailId,
      @RequestParam(name = "seriesName") String series) {
    return new ResponseEntity<>(
        new ResponseMovieApp(
                Collections.singletonList(watchlistService.addSeriesToUserWatchlist(emailId, series))),
        HttpStatus.OK);
  }

  @PostMapping("/removeSeries")
  public ResponseEntity<ResponseMovieApp> removeSeriesFromUserWatchlist(
      @RequestParam(name = "userEmail") String emailId,
      @RequestParam(name = "seriesName") String series) {
    return new ResponseEntity<>(new ResponseMovieApp(
            Collections.singletonList(watchlistService.removeSeriesFromUserWatchlist(emailId, series))),
        HttpStatus.OK);
  }

}
