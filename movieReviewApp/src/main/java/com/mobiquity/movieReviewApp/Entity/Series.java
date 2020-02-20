package com.mobiquity.movieReviewApp.Entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "series")
public class Series {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String releaseDate;
  private String timeOnAir;
  private String description;
  private int runtime;
  private int numberOfSeasons;

  private String metacriticScore;
  private String rottenTomatoes;
  private String imdbRating;

  @ManyToMany(mappedBy = "seriesWatchlist")
  private Set<UserProfile> users = new HashSet<>();
}

