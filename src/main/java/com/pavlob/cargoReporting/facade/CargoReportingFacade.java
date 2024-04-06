package com.pavlob.cargoReporting.facade;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface CargoReportingFacade {

    void generateCargoReports (String jwt, int countGeneratedReports) throws JsonProcessingException;
}
