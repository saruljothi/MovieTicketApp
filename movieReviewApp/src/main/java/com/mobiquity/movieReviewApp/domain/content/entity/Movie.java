package com.mobiquity.movieReviewApp.domain.content.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie")
public class Movie extends Content{

  @ManyToMany(mappedBy = "movieWatchlist")
  private Set<UserProfile> users = new HashSet<>();

}
