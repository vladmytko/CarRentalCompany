package com.vladyslav.CarRentalCompany.service.impl;

import com.vladyslav.CarRentalCompany.dto.BookingDTO;
import com.vladyslav.CarRentalCompany.dto.requests.BookingUpdateRequest;
import com.vladyslav.CarRentalCompany.dto.response.Response;
import com.vladyslav.CarRentalCompany.entity.Booking;
import com.vladyslav.CarRentalCompany.entity.Car;
import com.vladyslav.CarRentalCompany.entity.User;
import com.vladyslav.CarRentalCompany.entity.enums.AvailabilityStatus;
import com.vladyslav.CarRentalCompany.exception.OurException;
import com.vladyslav.CarRentalCompany.repo.BookingRepository;
import com.vladyslav.CarRentalCompany.repo.CarRepository;
import com.vladyslav.CarRentalCompany.repo.UserRepository;
import com.vladyslav.CarRentalCompany.service.interfac.IBookingService;
import com.vladyslav.CarRentalCompany.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
                throw new IllegalArgumentException("Check-out date must be after check-in date");
            }

            Car car = carRepository.findById(carId).orElseThrow(()-> new OurException("Car Not Found"));

            User user = userRepository.findById(userId).orElseThrow(()-> new OurException("User Not Found"));

            List<Booking> existingBooking = car.getBookings();
            if(!carIsAvailable(bookingRequest, existingBooking)){
                throw new OurException("Car is not available for selected date range");
            }

            bookingRequest.setCar(car);
            bookingRequest.setUser(user);
            bookingRequest.setTotalNumOfPeople();
            bookingRequest.setTotalPrice();



            Integer totalNumberOfPeople = bookingRequest.getTotalNumOfPeople();

            if(totalNumberOfPeople > car.getNumberOfSeats()){
                throw new OurException("Not enough seats for " + totalNumberOfPeople + " people");
            }

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

            if(request.getCarId() != null) {
                Car newCar = carRepository.findById(request.getCarId()).orElseThrow(() -> new OurException("Car not found"));

                booking.setCar(newCar);
            }

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
                        bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate().plusDays(1)) &&
                                bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckInDate().minusDays(1))
                );
    }

}
