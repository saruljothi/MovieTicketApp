package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.Entity.Movie;
import com.mobiquity.movieReviewApp.Entity.UserProfile;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class WatchlistServiceImpl implements WatchlistService {

  private UserRepository userRepository;
  private MovieService movieService;

  public WatchlistServiceImpl(UserRepository userRepository, MovieService movieService){
    this.userRepository = userRepository;
    this.movieService = movieService;
  }

  @Transactional
  @Override
  public String addMovieToUsersWatchlist(String userEmailId, String movieName) {
    Optional<UserProfile> userProfile = userRepository.findByEmailId(userEmailId);
    Movie movie = movieService.getMovie(movieName);
    if(userProfile.isEmpty()){
      return movie.getName() + " could not be added to watchlist as user does not exist.";
    }
    if(movie == null){
      return movieName + " could not be added to watchlist no such movie exists.";
    }
    if(userProfile.get().getMovieWatchList().contains(movie)){
      return movie.getName() + " has already been added to user watchlist.";
    }
    userProfile.get().addMovieToWatchList(movieService.getMovie(movieName));
    return movie.getName() + " has been added to watchlist.";

  }

  @Transactional
  @Override
  public String removeMovieFromAUsersWatchlist(String userEmailId, String movieName) {
    Optional<UserProfile> userProfile = userRepository.findByEmailId(userEmailId);
    Movie movie = movieService.getMovie(movieName);
    if(userProfile.isEmpty()){
      return movie.getName() + " could not be removed from watchlist as no such exists.";
    }
    if(movie == null){
      return movieName + " could not be removed from watchlist as no such movie exists.";
    }
    if(!userProfile.get().getMovieWatchList().contains(movie)){
      return movie.getName() + " is not in the user watchlist.";
    }
    userProfile.get().removeMovieFromWatchList(movieService.getMovie(movieName));
    return "Movie has been removed from watchlist.";
  }

}
