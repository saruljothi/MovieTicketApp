package com.mobiquity.movieReviewApp.Entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
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
  private String emailId;
  private String password;
  private boolean status;
  @CreationTimestamp
  private LocalDateTime createdOn;
  @UpdateTimestamp
  private LocalDateTime updatedOn;

}
