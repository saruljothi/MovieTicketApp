package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.Entity.Movie;
import java.util.Set;

public interface WatchlistService {

  String addMovieToUsersWatchlist(String userEmailId, String movieName);

  String removeMovieFromAUsersWatchlist(String userEmailId, String movieName);

}
