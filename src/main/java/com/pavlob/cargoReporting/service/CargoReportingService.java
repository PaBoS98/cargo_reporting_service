package com.pavlob.cargoReporting.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pavlob.cargoReporting.model.CargoDto;

public interface CargoReportingService {

    void processCargoReport(String jwt, CargoDto cargoDto) throws JsonProcessingException;
}
