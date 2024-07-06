package com.asdc.dalexchange.repository;


import com.asdc.dalexchange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

<<<<<<< HEAD

    @Query(value = "SELECT COUNT(*) FROM user WHERE joined_at >= CURDATE() - INTERVAL 30 DAY", nativeQuery = true)
    Long countUsersJoinedInLast30Days();

    @Query("SELECT COUNT(u) FROM User u WHERE u.joinedAt >= :startDate")
    Long countUsersJoinedSince(LocalDateTime startDate);

    @Query("SELECT COUNT(u) FROM User u WHERE u.joinedAt >= :startDate AND u.joinedAt < :endDate")
    Long countUsersJoinedBetween(LocalDateTime startDate, LocalDateTime endDate);


}
=======
}
>>>>>>> fe8b9d1bd5f445d24500c65808c8057d8b809556
