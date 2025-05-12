package com.vladyslav.CarRentalCompany.service.interfac;

import com.vladyslav.CarRentalCompany.dto.response.Response;
import com.vladyslav.CarRentalCompany.dto.requests.VanCreationRequest;
import com.vladyslav.CarRentalCompany.entity.Van;

import java.util.List;

public interface IVanService {

    Response addVan(VanCreationRequest request);

    Response getVanById(Long vanId);

    List<Van> getAllVans();

    Response deleteVan(Long vanId);

    Response updateVan(VanCreationRequest request);
}
