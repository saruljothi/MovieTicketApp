package com.mobiquity.movieReviewApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter

@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long userId;
  private String name;
  @Column(unique = true)
  private String emailId;
  private String password;
//  private boolean status;

}
