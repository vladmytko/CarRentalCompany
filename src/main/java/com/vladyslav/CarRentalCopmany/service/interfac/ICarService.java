package com.vladyslav.CarRentalCopmany.service.interfac;

import com.vladyslav.CarRentalCopmany.dto.response.Response;
import com.vladyslav.CarRentalCopmany.dto.requests.CarCreationRequest;
import com.vladyslav.CarRentalCopmany.dto.requests.CarSearchCriteriaRequest;
import com.vladyslav.CarRentalCopmany.dto.requests.CarUpdateRequest;
import com.vladyslav.CarRentalCopmany.entity.Car;
import com.vladyslav.CarRentalCopmany.entity.enums.CarType;

import java.util.List;

public interface ICarService {

    Response addNewCar(CarCreationRequest request);

    List<CarType> getAllCarTypes();

    Response getAllCars();

    Response deleteCar(Long carId);

    Response updateCar(CarUpdateRequest updateRequest);

    Response getCarById(Long carId);

    Response getAllAvailableCars();

    Response getAllRentedCars();

    Response getAllCarsOnMaintenance();

    Response searchCarAdmin(CarSearchCriteriaRequest request);

    Response searchCarUser(CarSearchCriteriaRequest request);
}
