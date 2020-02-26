package com.mobiquity.movieReviewApp.repository;

import com.mobiquity.movieReviewApp.domain.content.entity.Movie;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

  Optional<Movie> findMovieByName(String name);

}
