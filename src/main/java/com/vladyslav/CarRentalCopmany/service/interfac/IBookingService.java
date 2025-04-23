package com.vladyslav.CarRentalCopmany.service.interfac;

import com.vladyslav.CarRentalCopmany.dto.requests.BookingUpdateRequest;
import com.vladyslav.CarRentalCopmany.dto.response.Response;
import com.vladyslav.CarRentalCopmany.entity.Booking;

public interface IBookingService {

    Response saveBooking(Long carId, Long userId, Booking bookingRequest);

    Response deleteBookingByConfirmationCode(String confirmationCode);

    Response updateBooking(BookingUpdateRequest request);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response findBookingByUserId(Long userId);

    Response findBookingByCarId(Long carId);


}
