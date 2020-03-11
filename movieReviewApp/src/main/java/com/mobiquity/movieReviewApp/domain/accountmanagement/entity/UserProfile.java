package com.mobiquity.movieReviewApp.domain.accountmanagement.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import com.mobiquity.movieReviewApp.domain.content.entity.Movie;
import com.mobiquity.movieReviewApp.domain.content.entity.Series;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@NamedNativeQueries(value = {
    @NamedNativeQuery(name = "UserProfile.updateStatus", query = "update user_profile set status=true where user_id= ?1"),
    @NamedNativeQuery(name = "UserProfile.deleteByCreatedOnAndStatus", query = "delete from user_profile where status=false and created_on < ?1"),
    @NamedNativeQuery(name = "UserProfile.findPasswordByEmailId", query = "select password from user_profile where email_id = ?1"),
    @NamedNativeQuery(name = "UserProfile.updatePassword", query = "update user_profile set password=?2 where email_id= ?1")
})
@Table(name = "user_profile")
public class UserProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  private String name;

  @Column(unique = true)
  @NaturalId
  private String emailId;

  private String password;

  private boolean status;

  @CreationTimestamp
  private LocalDateTime createdOn;

  @UpdateTimestamp
  private LocalDateTime updatedOn;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  Set<Movie> movieWatchlist = new HashSet<>();

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  Set<Series> seriesWatchlist = new HashSet<>();

  public void addMovieToWatchlist(Movie movie){
    movieWatchlist.add(movie);
  }

  public void removeMovieFromWatchlist(Movie movie){
    movieWatchlist.remove(movie);
  }

  public void addSeriesToWatchlist(Series series){
    seriesWatchlist.add(series);
  }

  public void removeSeriesFromWatchlist(Series series){
    seriesWatchlist.remove(series);
  }



}
