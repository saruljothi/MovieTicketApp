package com.mobiquity.movieReviewApp.domain.content.service;

import com.mobiquity.movieReviewApp.domain.content.entity.Content;
import com.mobiquity.movieReviewApp.domain.content.entity.Movie;
import com.mobiquity.movieReviewApp.domain.content.entity.Series;
import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class WatchlistServiceImpl implements WatchlistService {

  private static final String ADD_NO_SUCH_USER_MESSAGE = " could not be added to watchlist as no such user exists.";
  private static final String REMOVE_NO_SUCH_USER_MESSAGE = " could not be removed from watchlist as no such user exists.";
  private static final String ADD_NO_SUCH_CONTENT_MESSAGE = " could not be added to watchlist as it does not exist.";
  private static final String REMOVE_NO_SUCH_CONTENT_MESSAGE = " could not be removed from watchlist as it does not exist.";
  private static final String HAS_ALREADY_BEEN_ADDED_MESSAGE = " has already been added to user watchlist.";
  private static final String IS_NOT_IN_WATCHLIST_MESSAGE = " is not in the user watchlist.";

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
    Content movie = movieService.getMovie(movieName);

    String check = checkIfUnableToAdd(userProfile, movie, ADD_NO_SUCH_USER_MESSAGE, ADD_NO_SUCH_CONTENT_MESSAGE, HAS_ALREADY_BEEN_ADDED_MESSAGE);
    if(check != null){
      return check;
    }

    userProfile.get().addMovieToWatchlist(movieService.getMovie(movieName));
    return movie.getName() + " has been added to watchlist.";

  }

  @Transactional
  @Override
  public String removeMovieFromAUserWatchlist(String emailId, String movieName) {
    Optional<UserProfile> userProfile = userRepository.findByEmailId(emailId);
    Content movie = movieService.getMovie(movieName);

    String check = checkIfUnableToAdd(userProfile, movie, REMOVE_NO_SUCH_USER_MESSAGE, REMOVE_NO_SUCH_CONTENT_MESSAGE, IS_NOT_IN_WATCHLIST_MESSAGE);
    if(check != null){
      return check;
    }

    userProfile.get().removeMovieFromWatchlist(movieService.getMovie(movieName));
    return movie.getName() + " has been removed from watchlist.";
  }

  @Transactional
  @Override
  public String addSeriesToUserWatchlist(String emailId, String seriesName) {
    Optional<UserProfile> userProfile = userRepository.findByEmailId(emailId);
    Content series = seriesService.getSeries(seriesName);

    String check = checkIfUnableToAdd(userProfile, series, ADD_NO_SUCH_USER_MESSAGE, ADD_NO_SUCH_CONTENT_MESSAGE, HAS_ALREADY_BEEN_ADDED_MESSAGE);
    if(check != null){
      return check;
    }

    userProfile.get().addSeriesToWatchlist(seriesService.getSeries(seriesName));
    return series.getName() + " has been added to watchlist.";
  }

  @Transactional
  @Override
  public String removeSeriesFromUserWatchlist(String emailId, String seriesName) {
    Optional<UserProfile> userProfile = userRepository.findByEmailId(emailId);
    Content series = seriesService.getSeries(seriesName);

    String check = checkIfUnableToAdd(userProfile, series, REMOVE_NO_SUCH_USER_MESSAGE, REMOVE_NO_SUCH_CONTENT_MESSAGE, IS_NOT_IN_WATCHLIST_MESSAGE);
    if(check != null){
      return check;
    }

    userProfile.get().removeSeriesFromWatchlist(seriesService.getSeries(seriesName));
    return series.getName() + " has been removed from watchlist.";
  }

  private String checkIfUnableToAdd(Optional<UserProfile> userProfile, Content content, String noSuchUserMessage, String noSuchContentMessage, String alreadyDoneMessage) {
    if(userProfile.isEmpty()){
      return content.getName() + noSuchUserMessage;
    }
    if(content == null){
      return noSuchContentMessage;
    }
    if(content.getClass().equals(Movie.class)){
      if(!userProfile.get().getMovieWatchlist().contains(content)){
        return content.getName() + alreadyDoneMessage;
      }
    }else if(content.getClass().equals(Series.class)){
      if(!userProfile.get().getSeriesWatchlist().contains(content)){
        return content.getName() + alreadyDoneMessage;
      }
    }
    return null;
  }

}
