package com.mobiquity.movieReviewApp.repository;

import com.mobiquity.movieReviewApp.model.UserProfile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long> {

  Optional<UserProfile> save(String emailId);
}
