package com.asdc.dalexchange.repository;

import com.asdc.dalexchange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT COUNT(*) FROM user WHERE joined_at >= CURDATE() - INTERVAL 30 DAY", nativeQuery = true)
    Long countUsersJoinedInLast30Days();

    @Query("SELECT COUNT(u) FROM User u WHERE u.joinedAt >= :startDate")
    Long countUsersJoinedSince(LocalDateTime startDate);

    @Query("SELECT COUNT(u) FROM User u WHERE u.joinedAt >= :startDate AND u.joinedAt < :endDate")
    Long countUsersJoinedBetween(LocalDateTime startDate, LocalDateTime endDate);

}
