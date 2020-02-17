package com.mobiquity.movieReviewApp.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedNativeQueries(value= {
    @NamedNativeQuery(name = "UserProfile.updateStatus" , query= "update user_profile set status=true where user_id= ?1"),
    @NamedNativeQuery(name= "UserProfile.deleteByCreatedOnAndStatus" , query = "delete from user_profile where status=false and created_on < ?1"),
    @NamedNativeQuery(name="UserProfile.findPasswordByEmailId",query="select password from user_profile where email_id = ?1"),
    @NamedNativeQuery(name="UserProfile.updatePassword",query="update user_profile set password=?2 where email_id= ?1")
})
@Table(name = "user_profile")
public class UserProfile {


  public UserProfile(long userId, String name, String emailId, String password, boolean status,
      LocalDateTime createdOn, LocalDateTime updatedOn) {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  private String name;

  @Column(unique = true)
  private String emailId;

  private String password;

  @Transient
  private String passwordConfirmation;

  private boolean status;

  @CreationTimestamp
  private LocalDateTime createdOn;

  @UpdateTimestamp
  private LocalDateTime updatedOn;

}