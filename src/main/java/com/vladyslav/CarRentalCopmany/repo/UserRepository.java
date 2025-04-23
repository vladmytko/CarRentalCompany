package com.vladyslav.CarRentalCopmany.repo;

import com.vladyslav.CarRentalCopmany.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.name = :name")
    Optional<User> findUserByName(@Param("name") String name);
}
