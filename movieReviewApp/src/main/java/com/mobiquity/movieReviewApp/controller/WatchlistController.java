package com.mobiquity.movieReviewApp.controller;

import com.mobiquity.movieReviewApp.model.userManagement.ResponseMovieApp;
import com.mobiquity.movieReviewApp.service.WatchlistService;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/v1/watchlist")
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
            Arrays.asList(watchlistService.addMovieToUserWatchlist(emailId, movie))),
        HttpStatus.OK);
  }

  @PostMapping("/removeMovie")
  public ResponseEntity<ResponseMovieApp> removeMovieToUserWatchlist(
      @RequestParam(name = "userEmail") String emailId,
      @RequestParam(name = "movieName") String movie) {
    return new ResponseEntity<>(
        new ResponseMovieApp(
            Arrays.asList(watchlistService.removeMovieFromAUserWatchlist(emailId, movie))),
        HttpStatus.OK);
  }

  @PostMapping("/addSeries")
  public ResponseEntity<ResponseMovieApp> addSeriesToUserWatchlist(
      @RequestParam(name = "userEmail") String emailId,
      @RequestParam(name = "seriesName") String series) {
    return new ResponseEntity<>(
        new ResponseMovieApp(
            Arrays.asList(watchlistService.addSeriesToUserWatchlist(emailId, series))),
        HttpStatus.OK);
  }

  @PostMapping("/removeSeries")
  public ResponseEntity<ResponseMovieApp> removeSeriesFromUserWatchlist(
      @RequestParam(name = "userEmail") String emailId,
      @RequestParam(name = "seriesName") String series) {
    return new ResponseEntity<>(new ResponseMovieApp(
        Arrays.asList(watchlistService.removeSeriesFromUserWatchlist(emailId, series))),
        HttpStatus.OK);
  }
//
//  @PostMapping("/addToWatchList")
//  public ResponseEntity<ResponseMovieApp> addToWatchList(
//      @RequestParam(name = "movieName") String movieName
//  )
//  {
//
//  }

}
