package com.mobiquity.movieReviewApp.repository;

import com.mobiquity.movieReviewApp.Entity.Series;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {

  Optional<Series> findSeriesByName(String name);

}
