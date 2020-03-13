package com.mobiquity.movieReviewApp.repository;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long> {

  @Modifying
  void updateStatus(Long userId);

  @Modifying
  void deleteByCreatedOnAndStatus(LocalDateTime localDateTime);

  Optional<UserProfile> findByEmailId(String email);

}
