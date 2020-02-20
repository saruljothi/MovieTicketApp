package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.Entity.Movie;
import com.mobiquity.movieReviewApp.Entity.Series;
import com.mobiquity.movieReviewApp.Entity.UserProfile;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class WatchlistServiceImpl implements WatchlistService {

  private UserRepository userRepository;
  private MovieService movieService;
  private SeriesService seriesService;

  public WatchlistServiceImpl(UserRepository userRepository, MovieService movieService, SeriesService seriesService){
    this.userRepository = userRepository;
    this.movieService = movieService;
    this.seriesService = seriesService;
  }

  @Transactional
  @Override
  public String addMovieToUserWatchlist(String emailId, String movieName) {
    Optional<UserProfile> userProfile = userRepository.findByEmailId(emailId);
    Movie movie = movieService.getMovie(movieName);
    if(userProfile.isEmpty()){
      return movie.getName() + " could not be added to watchlist as no such user exists.";
    }
    if(movie == null){
      return movieName + " could not be added to watchlist no such movie exists.";
    }
    if(userProfile.get().getMovieWatchlist().contains(movie)){
      return movie.getName() + " has already been added to user watchlist.";
    }
    userProfile.get().addMovieToWatchlist(movieService.getMovie(movieName));
    return movie.getName() + " has been added to watchlist.";

  }

  @Transactional
  @Override
  public String removeMovieFromAUserWatchlist(String emailId, String movieName) {
    Optional<UserProfile> userProfile = userRepository.findByEmailId(emailId);
    Movie movie = movieService.getMovie(movieName);
    if(userProfile.isEmpty()){
      return movie.getName() + " could not be removed from watchlist as no such user exists.";
    }
    if(movie == null){
      return movieName + " could not be removed from watchlist as no such movie exists.";
    }
    if(!userProfile.get().getMovieWatchlist().contains(movie)){
      return movie.getName() + " is not in the user watchlist.";
    }
    userProfile.get().removeMovieFromWatchlist(movieService.getMovie(movieName));
    return movie.getName() + " has been removed from watchlist.";
  }

  @Transactional
  @Override
  public String addSeriesToUserWatchlist(String emailId, String seriesName) {
    Optional<UserProfile> userProfile = userRepository.findByEmailId(emailId);
    Series series = seriesService.getSeries(seriesName);
    if(userProfile.isEmpty()){
      return series.getName() + " could not be added to watchlist as no such user exists.";
    }
    if(series == null){
      return seriesName + " could not be added to watchlist no such series exists.";
    }
    if(userProfile.get().getSeriesWatchlist().contains(series)){
      return series.getName() + " has already been added to user watchlist.";
    }
    userProfile.get().addSeriesToWatchlist(seriesService.getSeries(seriesName));
    return series.getName() + " has been added to watchlist.";
  }

  @Transactional
  @Override
  public String removeSeriesFromUserWatchlist(String emailId, String seriesName) {
    Optional<UserProfile> userProfile = userRepository.findByEmailId(emailId);
    Series series = seriesService.getSeries(seriesName);
    if(userProfile.isEmpty()){
      return series.getName() + " could not be removed from watchlist as no such user exists.";
    }
    if(series == null){
      return seriesName + " could not be removed from watchlist as no such movie exists.";
    }
    if(!userProfile.get().getSeriesWatchlist().contains(series)){
      return series.getName() + " is not in the user watchlist.";
    }
    userProfile.get().removeSeriesFromWatchlist(seriesService.getSeries(seriesName));
    return series.getName() + " has been removed from watchlist.";
  }

}
