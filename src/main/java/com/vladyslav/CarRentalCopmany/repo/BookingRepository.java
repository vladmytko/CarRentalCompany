package com.vladyslav.CarRentalCopmany.repo;

import com.vladyslav.CarRentalCopmany.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByCarId(Long carId);

    Optional<Booking> findByBookingConfirmationCode(String confirmationCode);

    List<Booking> findByUserId(Long userId);

    void deleteByBookingConfirmationCode(String confirmationCode);

    List<Booking> findAllByCheckOutDateBefore (LocalDate date);
    List<Booking> findAllByCarIdAndCheckOutDateAfter(Long carId, LocalDate date);


}
