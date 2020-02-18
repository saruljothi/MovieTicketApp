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

  public WatchlistServiceImpl(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  //TODO change addMovieToUsersWatchlist to get the movie from a MovieRepository which has a method to get a Movie by its title
  @Transactional
  @Override
  public String addMovieToUsersWatchlist(String userEmailId, Movie movie) {
    Optional<UserProfile> userProfile = userRepository.findByEmailId(userEmailId);
    //Optional<UserProfile> userProfile = userRepository.by(userEmailId);
    if(userProfile.isPresent()){
    userProfile.get().addMovieToWatchList(movie);
    return "Movie has been added to watchlist";
  }
    return "Movie could not be added to watchlist as user does not exist";
  }

  //TODO change removeMovieFromAUsersWatchlist to get the movie from a MovieRepository which has a method to get a Movie by its title
  @Transactional
  @Override
  public String removeMovieFromAUsersWatchlist(String userEmailId, Movie movie) {
    Optional<UserProfile> userProfile = userRepository.findByEmailId(userEmailId);
    if(userProfile.isPresent()){
      if(userProfile.get().getMovieWatchList().contains(movie)){
        userProfile.get().removeMovieFromWatchList(movie);
        return "Movie has been removed from watchlist";
      }
      return "There is no such movie in the watchlist";
    }
    return "Movie could not be added to watchlist as user does not exist";
  }

  @Transactional
  @Override
  public Set<Movie> getUserWatchlist(String userEmailId) {
    Optional<UserProfile> userProfile = userRepository.findByEmailId(userEmailId);
    return userProfile.map(UserProfile::getMovieWatchList).orElse(null);
  }
}
