package com.pavlob.cargoReporting.service.impl;

import com.pavlob.cargoReporting.model.CargoDto;
import com.pavlob.cargoReporting.model.DeliveryStatusDto;
import com.pavlob.cargoReporting.resource.CargoTrackingApiClient;
import com.pavlob.cargoReporting.service.CargoTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoTrackingServiceImpl implements CargoTrackingService {

    @Autowired
    private CargoTrackingApiClient cargoTrackingApiClient;

    @Override
    public List<CargoDto> getAllCargoWhereStatusNot(final DeliveryStatusDto deliveryStatus) {
        return cargoTrackingApiClient.getAllCargoWhereStatusNot(deliveryStatus);
    }
}
