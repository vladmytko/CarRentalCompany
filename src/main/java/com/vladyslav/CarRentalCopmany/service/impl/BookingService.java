package com.vladyslav.CarRentalCopmany.service.impl;

import com.vladyslav.CarRentalCopmany.dto.BookingDTO;
import com.vladyslav.CarRentalCopmany.dto.requests.BookingUpdateRequest;
import com.vladyslav.CarRentalCopmany.dto.response.Response;
import com.vladyslav.CarRentalCopmany.entity.Booking;
import com.vladyslav.CarRentalCopmany.entity.Car;
import com.vladyslav.CarRentalCopmany.entity.User;
import com.vladyslav.CarRentalCopmany.entity.enums.AvailabilityStatus;
import com.vladyslav.CarRentalCopmany.exception.OurException;
import com.vladyslav.CarRentalCopmany.repo.BookingRepository;
import com.vladyslav.CarRentalCopmany.repo.CarRepository;
import com.vladyslav.CarRentalCopmany.repo.UserRepository;
import com.vladyslav.CarRentalCopmany.service.interfac.IBookingService;
import com.vladyslav.CarRentalCopmany.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Response saveBooking(Long carId, Long userId, Booking bookingRequest) {
        Response response = new Response();

        try{
            if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
                throw new IllegalArgumentException("Check in date must be after check out date");
            }

            Car car = carRepository.findById(carId).orElseThrow(()-> new OurException("Car Not Found"));
            User user = userRepository.findById(userId).orElseThrow(()-> new OurException("User Not Found"));

            List<Booking> existingBooking = car.getBookings();
            if(!carIsAvailable(bookingRequest, existingBooking)){
                throw new OurException("Car is not available for selected date range");
            }

            car.setCarAvailability(AvailabilityStatus.RENTED);

            bookingRequest.setCar(car);
            bookingRequest.setUser(user);

            String bookingConfirmationCode = Utils.generateRandomConfirmationCode(10);
            bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);

            bookingRepository.save(bookingRequest);

            BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTO(bookingRequest);

            response.setStatusCode(200);
            response.setMessage("Booking is completed");
            response.setBookingConfirmationCode(bookingConfirmationCode);
            response.setBooking(bookingDTO);


        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exception
            response.setStatusCode(500);
            response.setMessage("Error occurred during creating booking " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response deleteBookingByConfirmationCode(String confirmationCode) {
        Response response = new Response();

        try{

            Booking booking = bookingRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(()-> new OurException("Booking does not exist"));
            bookingRepository.deleteByBookingConfirmationCode(confirmationCode);

            Car car = booking.getCar();
            car.setCarAvailability(AvailabilityStatus.AVAILABLE);

            // Set success response
            response.setStatusCode(200);
            response.setMessage("Booking canceled successfully");


        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exception
            response.setStatusCode(500);
            response.setMessage("Error occurred during booking cancellation " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response updateBooking(BookingUpdateRequest request) {
        Response response = new Response();

        try{

            Booking booking = bookingRepository.findById(request.getBookingId()).orElseThrow(()-> new OurException("Booking Does Not Exist"));

            if(request.getCheckInDate() != null) booking.setCheckInDate(request.getCheckInDate());
            if(request.getCheckOutDate() != null) booking.setCheckOutDate(request.getCheckOutDate());
            if(request.getNumOfAdults() != null) booking.setNumOfAdults(request.getNumOfAdults());
            if(request.getNumOfChildren() != null) booking.setNumOfChildren(request.getNumOfChildren());

            Booking updatedBooking = bookingRepository.save(booking);
            BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTO(updatedBooking);

            // Set success response
            response.setStatusCode(200);
            response.setMessage("Booking updated successfully");
            response.setBooking(bookingDTO);

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exception
            response.setStatusCode(500);
            response.setMessage("Error occurred during booking update " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {


        Response response = new Response();

        try{

            Booking booking = bookingRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(()-> new OurException("Booking Not Found"));
            BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTO(booking);

            // Set success response
            response.setStatusCode(200);
            response.setMessage("Booking updated successfully");
            response.setBooking(bookingDTO);

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exception
            response.setStatusCode(500);
            response.setMessage("Error occurred during booking update " + e.getMessage());
        }

        return response;

    }

    @Override
    public Response findBookingByUserId(Long userId) {
        Response response = new Response();

        try{

            List<Booking> bookingList = bookingRepository.findByUserId(userId);

            List<BookingDTO> bookingDTOList = Utils.mapBookingListEntityToBookingListDTO(bookingList);


            // Set success response
            response.setStatusCode(200);
            response.setMessage("Booking retrieved successfully");
            response.setBookingList(bookingDTOList);

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exception
            response.setStatusCode(500);
            response.setMessage("Error occurred during booking retrieving " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response findBookingByCarId(Long carId) {
        Response response = new Response();

        try{

            List<Booking> booking = bookingRepository.findByCarId(carId);
            List<BookingDTO> bookingDTO = Utils.mapBookingListEntityToBookingListDTO(booking);


            // Set success response
            response.setStatusCode(200);
            response.setMessage("Booking retrieved successfully");
            response.setBookingList(bookingDTO);

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exception
            response.setStatusCode(500);
            response.setMessage("Error occurred during booking retrieving " + e.getMessage());
        }

        return response;
    }

    private boolean carIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()) &&
                                bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckInDate())
                );
    }
}
