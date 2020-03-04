package com.mobiquity.movieReviewApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.UserException;
import com.mobiquity.movieReviewApp.domain.content.service.AddMovieToWatchListServiceImpl;
import com.mobiquity.movieReviewApp.domain.content.service.ErrorMessages;
import com.mobiquity.movieReviewApp.repository.MovieRepository;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import com.mobiquity.movieReviewApp.security.AuthTest;
import com.mobiquity.movieReviewApp.security.PrinceTest;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
public class AddMovieToWatchListServiceImplTest {

  @InjectMocks
  AddMovieToWatchListServiceImpl addMovieToWatchListService;

  @Mock
  UserRepository userRepository;
  @Mock
  MovieRepository movieRepository;

  @BeforeEach
  public void setUP() {
    SecurityContextHolder.getContext()
        .setAuthentication(new AuthTest(new PrinceTest("abc@gmail.com")));
  }

  @Test
  public void checkIfNoMoviePresentinDb() {
    Mockito.when(movieRepository.findByName("&")).thenReturn(new ArrayList<>());
    Mockito.when(userRepository.findByEmailId("abc@gmail.com")).thenReturn(
        java.util.Optional.of(new UserProfile()));

    UserException u = assertThrows(UserException.class,
        () -> addMovieToWatchListService.addMovieToWatchList("&"));
    assertEquals(u.getLocalizedMessage(), ErrorMessages.NO_MOVIE_PRESENT.toString());
  }
}
