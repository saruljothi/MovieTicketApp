package com.mobiquity.movieReviewApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_profile")
public class UserProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long userId;
  private String name;
  @Column(unique = true)
  private String emailId;
  private String password;
  private boolean status;

}
