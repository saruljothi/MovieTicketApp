package com.mobiquity.movieReviewApp.repository;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long> {

  Optional<UserProfile> deleteByUserIdAndStatus(long Id,Boolean status);

  @Modifying
 // @Query(value= "update user_profile set status=true where user_id= :userId" , nativeQuery = true)
  void updateStatus(long userId);

  @Modifying
//  @Query(value="delete from user_profile where status=false and created_on < :localDateTime" , nativeQuery = true)
  void deleteByCreatedOnAndStatus(LocalDateTime localDateTime);

  String findPasswordByEmailId(String emailId);

  @Modifying
  void updatePassword(String emailId,String newPassword);

  Optional<UserProfile> findByEmailId(String email);

}
