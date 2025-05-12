package com.vladyslav.CarRentalCompany.service.interfac;

import com.vladyslav.CarRentalCompany.dto.requests.BookingUpdateRequest;
import com.vladyslav.CarRentalCompany.dto.response.Response;
import com.vladyslav.CarRentalCompany.entity.Booking;

public interface IBookingService {

    Response saveBooking(Long carId, Long userId, Booking bookingRequest);

    Response deleteBookingByConfirmationCode(String confirmationCode);

    Response updateBooking(BookingUpdateRequest request);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response findBookingByUserId(Long userId);

    Response findBookingByCarId(Long carId);


}
