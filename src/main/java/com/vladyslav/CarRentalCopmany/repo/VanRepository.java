package com.vladyslav.CarRentalCopmany.repo;

import com.vladyslav.CarRentalCopmany.entity.Van;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VanRepository extends JpaRepository<Van, Long> {
    List<Van> findByLoadCapacityGreaterThan(double minCapacity);
}
