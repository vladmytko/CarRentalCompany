package com.vladyslav.CarRentalCompany.service.impl;

import com.vladyslav.CarRentalCompany.dto.CarDTO;
import com.vladyslav.CarRentalCompany.dto.requests.CarCreationRequest;
import com.vladyslav.CarRentalCompany.dto.requests.CarSearchRequest;
import com.vladyslav.CarRentalCompany.dto.requests.CarUpdateRequest;
import com.vladyslav.CarRentalCompany.dto.response.Response;
import com.vladyslav.CarRentalCompany.entity.Car;
import com.vladyslav.CarRentalCompany.entity.enums.AvailabilityStatus;
import com.vladyslav.CarRentalCompany.entity.enums.CarType;
import com.vladyslav.CarRentalCompany.exception.OurException;
import com.vladyslav.CarRentalCompany.repo.BookingRepository;
import com.vladyslav.CarRentalCompany.repo.CarRepository;
import com.vladyslav.CarRentalCompany.service.AwsS3Service;
import com.vladyslav.CarRentalCompany.service.interfac.ICarService;
import com.vladyslav.CarRentalCompany.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService implements ICarService {


   @Autowired
   private CarRepository carRepository;

   @Autowired
   private BookingRepository bookingRepository;

   @Autowired
   private AwsS3Service awsS3Service;

    @Override
    public Response addNewCar(CarCreationRequest request) {
        Response response = new Response();

        try{

            // Check if car exist
            if(carRepository.existsByRegistrationNumber(request.getRegistrationNumber())){
                throw new OurException(request.getRegistrationNumber() + " already exists");
            }

            String imageUrl = awsS3Service.saveImageToS3(request.getPhoto());

            Car car = new Car();
            car.setMake(request.getMake());
            car.setModel(request.getModel());
            car.setRegistrationNumber(request.getRegistrationNumber());
            car.setNumberOfSeats(request.getNumberOfSeats());
            car.setCarPrice(request.getPrice());
            car.setCarPhotoUrl(imageUrl);
            car.setCarType(request.getCarType());
            car.setCarAvailability(AvailabilityStatus.AVAILABLE);

            Car savedCar = carRepository.save(car);

            CarDTO carDTO = Utils.mapCarEntityToCarDTO(savedCar);

            // Set success response
            response.setStatusCode(201);
            response.setMessage("Car registered successfully");
            response.setCar(carDTO);

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exception
            response.setStatusCode(500);
            response.setMessage("Error occurred during adding new car " + e.getMessage());
        }

        return response;
    }

    @Override
    public List<CarType> getAllCarTypes() {
        return carRepository.findDistinctCarType();
    }

    @Override
    public Response getAllCars() {
        Response response = new Response();

        try{
            List<Car> carList = carRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<CarDTO> carDTOList = Utils.mapCarListEntityToCarListDTO(carList);

            // Set success response
            response.setStatusCode(201);
            response.setMessage("All cars retrieved successfully");
            response.setCarList(carDTOList);

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exception
            response.setStatusCode(500);
            response.setMessage("Error occurred during adding new car " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response deleteCar(Long carId) {
        Response response = new Response();

        try{

            if (carId == null) {
                throw new OurException("Car ID must not be null");
            }

            carRepository.findById(carId).orElseThrow(()->new OurException("Car Not Found"));
            carRepository.deleteById(carId);
            // Set success response
            response.setStatusCode(201);
            response.setMessage("Deletion successfully");

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exception
            response.setStatusCode(500);
            response.setMessage("Error occurred during car deletion " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response updateCar(CarUpdateRequest updateRequest) {
        Response response = new Response();

        try{
            String imageUrl = null;

            Car car = carRepository.findById(updateRequest.getCarId()).orElseThrow(()-> new OurException("Car not Found"));

            if(updateRequest.getPhoto() != null && !updateRequest.getPhoto().isEmpty()){
                imageUrl = awsS3Service.saveImageToS3(updateRequest.getPhoto());
                car.setCarPhotoUrl(imageUrl);
            }

            if(updateRequest.getMake() != null) car.setMake(updateRequest.getMake());
            if(updateRequest.getModel() != null) car.setModel(updateRequest.getModel());
            if(updateRequest.getRegistrationNumber() != null) car.setRegistrationNumber(updateRequest.getRegistrationNumber());
            if(updateRequest.getCarType() != null) car.setCarType(updateRequest.getCarType());
            if(updateRequest.getNumberOfSeats() != null) car.setNumberOfSeats(updateRequest.getNumberOfSeats());
            if(updateRequest.getCarPrice() != null) car.setCarPrice(updateRequest.getCarPrice());
            if(updateRequest.getCarAvailability() != null) car.setCarAvailability(updateRequest.getCarAvailability());

            Car updatedCar = carRepository.save(car);
            CarDTO carDTO = Utils.mapCarEntityToCarDTO(updatedCar);

            // Set success response
            response.setStatusCode(201);
            response.setMessage("Car updated successfully");
            response.setCar(carDTO);

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exception
            response.setStatusCode(500);
            response.setMessage("Error occurred during car deletion " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getCarById(Long carId) {

        Response response = new Response();

        try{
            Car car = carRepository.findById(carId).orElseThrow(()-> new OurException("Car Not Found"));
            CarDTO carDTO = Utils.mapCarEntityToCarDTOPlusBooking(car);

            // Set success response
            response.setStatusCode(201);
            response.setMessage("Car updated successfully");
            response.setCar(carDTO);

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exception
            response.setStatusCode(500);
            response.setMessage("Error occurred during finding car by ID " + e.getMessage());
        }

        return response;
    }


    @Override
    public Response getAllAvailableCars() {
        Response response = new Response();

        try{

            List<Car> carList = carRepository.findCarByAvailability(AvailabilityStatus.AVAILABLE);

            List<CarDTO> carDTOList = Utils.mapCarListEntityToCarListDTO(carList);

            // Set success response
            response.setStatusCode(200);
            response.setMessage("Information retrieved successfully");
            response.setCarList(carDTOList);

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exception
            response.setStatusCode(500);
            response.setMessage("Error occurred during car deletion " + e.getMessage());
        }

        return response;
    }




    @Override
    public Response getAllRentedCars() {
        Response response = new Response();

        try{
            List<Car> carList = carRepository.findCarByAvailability(AvailabilityStatus.RENTED);

            List<CarDTO> carDTOList = Utils.mapCarListEntityToCarListDTOPlusBookingDTO(carList);

            // Set success response
            response.setStatusCode(200);
            response.setMessage("Information retrieved successfully");
            response.setCarList(carDTOList);

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exception
            response.setStatusCode(500);
            response.setMessage("Error occurred during car deletion " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAllCarsOnMaintenance() {
        Response response = new Response();

        try{
            List<Car> carList = carRepository.findCarByAvailability(AvailabilityStatus.MAINTENANCE);

            List<CarDTO> carDTOList = Utils.mapCarListEntityToCarListDTOPlusBookingDTO(carList);

            // Set success response
            response.setStatusCode(200);
            response.setMessage("Information retrieved successfully");
            response.setCarList(carDTOList);

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exception
            response.setStatusCode(500);
            response.setMessage("Error occurred during car deletion " + e.getMessage());
        }

        return response;
    }


    @Override
    public Response searchCarAdmin(CarSearchRequest request) {

        Response response = new Response();

        try{

//            List<Car> carList = carRepository.searchCarAdmin(
//                    request.getCheckInDate(),
//                    request.getCheckOutDate(),
//                    request.getMake(),
//                    request.getModel(),
//                    request.getCarType(),
//                    request.getNumberOfSeats(),
//                    request.getCarPrice(),
//                    request.getStatus()
//            );
//            List<CarDTO> carDTOList = Utils.mapCarListEntityToCarListDTO(carList);
//
//            // Set success response
//            response.setStatusCode(200);
            response.setMessage("Information retrieved successfully");
            //response.setCarList(carDTOList);

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exception
            response.setStatusCode(500);
            response.setMessage("Error occurred during car search " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response searchCarUser(CarSearchRequest request) {
        Response response = new Response();

        try{
            List<Car> carList = carRepository.searchCar(
                    request.getCheckInDate(),
                    request.getCheckOutDate(),
                    request.getPickupLocation(),
                    request.getReturnLocation(),
                    request.getPickupTime(),
                    request.getReturnTime()
            );

            List<CarDTO> carDTOList = Utils.mapCarListEntityToCarListDTO(carList);

            // Set success response
            response.setStatusCode(200);
            response.setMessage("Information retrieved successfully");
            response.setCarList(carDTOList);

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected exception
            response.setStatusCode(500);
            response.setMessage("Error occurred during car search " + e.getMessage());
        }

        return response;
    }
}
