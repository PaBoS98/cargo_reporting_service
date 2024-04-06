package com.pavlob.cargoReporting.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pavlob.cargoReporting.api.CargoReportingApi;
import com.pavlob.cargoReporting.facade.CargoReportingFacade;
import com.pavlob.token.model.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CargoReportingApiImpl implements CargoReportingApi {

    @Autowired
    private CargoReportingFacade cargoReportingFacade;

    @Override
    public void generateCargoReports(final JwtToken token, final int countGeneratedReports) throws JsonProcessingException {
        final String jwt = token.getJwt();

        cargoReportingFacade.generateCargoReports(jwt, countGeneratedReports);
    }
}
