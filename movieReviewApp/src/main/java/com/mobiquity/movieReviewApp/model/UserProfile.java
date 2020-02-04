package com.mobiquity.movieReviewApp.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//
@NamedNativeQueries(value= {
    @NamedNativeQuery(name = "UserProfile.updateStatus" , query= "update user_profile set status=true where user_id= ?1"),
    @NamedNativeQuery(name= "UserProfile.deleteByCreatedOnAndStatus" , query = "delete from user_profile where status=false and created_on < ?1")
})
public class UserProfile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  private String name;
  @Column(unique = true)
  private String emailId;
  private String password;
  private boolean status;
  private LocalDateTime createdOn;
  private LocalDateTime updatedOn;

}
