package com.pavlob.cargoReporting.resource;

import com.pavlob.cargoReporting.model.CargoDto;
import com.pavlob.cargoReporting.model.DeliveryStatusDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "cargoTracking", url = "${cargo.tracking.service.url}")
public interface CargoTrackingApiClient {

    @GetMapping({"/api/cargoReporting/notInStatus"})
    List<CargoDto> getAllCargoWhereStatusNot(@RequestParam DeliveryStatusDto deliveryStatus);
}
