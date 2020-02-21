package com.mobiquity.movieReviewApp.Entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
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
public class Series extends Content{

  private String timeOnAir;
  private int numberOfSeasons;

  @ManyToMany(mappedBy = "seriesWatchlist")
  private Set<UserProfile> users = new HashSet<>();

}

