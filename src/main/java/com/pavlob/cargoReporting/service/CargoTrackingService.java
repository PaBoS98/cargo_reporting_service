package com.pavlob.cargoReporting.service;

import com.pavlob.cargoReporting.model.CargoDto;
import com.pavlob.cargoReporting.model.DeliveryStatusDto;

import java.util.List;

public interface CargoTrackingService {

    List<CargoDto> getAllCargoWhereStatusNot(DeliveryStatusDto deliveryStatus);
}
