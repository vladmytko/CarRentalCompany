package com.vladyslav.CarRentalCopmany.service.interfac;

import com.vladyslav.CarRentalCopmany.dto.response.Response;
import com.vladyslav.CarRentalCopmany.dto.requests.VanCreationRequest;
import com.vladyslav.CarRentalCopmany.entity.Van;

import java.util.List;

public interface IVanService {

    Response addVan(VanCreationRequest request);

    Response getVanById(Long vanId);

    List<Van> getAllVans();

    Response deleteVan(Long vanId);

    Response updateVan(VanCreationRequest request);
}
