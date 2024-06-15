package com.asdc.dalexchange.repository;


import com.asdc.dalexchange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT COUNT(*) FROM user WHERE joined_at >= CURDATE() - INTERVAL 30 DAY", nativeQuery = true)
    Long countUsersJoinedInLast30Days();
}
