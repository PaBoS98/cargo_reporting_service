package com.pavlob.cargoReporting.facade.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pavlob.Constant;
import com.pavlob.cargoReporting.facade.CargoReportingFacade;
import com.pavlob.cargoReporting.model.DeliveryStatusDto;
import com.pavlob.cargoReporting.service.CargoReportingService;
import com.pavlob.cargoReporting.service.CargoTrackingService;
import com.pavlob.util.CargoReportingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.pavlob.Constant.RandomlyCargoOperations.CREATE_NEW_REPORT;
import static com.pavlob.Constant.RandomlyCargoOperations.UPDATE_REPORT;

@Component
public class CargoReportingFacadeImpl implements CargoReportingFacade {

    @Autowired
    private CargoReportingService cargoReportingService;
    @Autowired
    private CargoTrackingService cargoTrackingService;

    @Override
    public void generateCargoReports(final String jwt, final int countGeneratedReports) throws JsonProcessingException {
        final Map<Constant.RandomlyCargoOperations, Integer> operationPercentages = CargoReportingUtil.randomizeOperationPercentagesFromValue(countGeneratedReports);

        if (operationPercentages.get(UPDATE_REPORT) > 0) {
            cargoTrackingService.getAllCargoWhereStatusNot(DeliveryStatusDto.ARRIVED).stream()
                    .limit(operationPercentages.get(UPDATE_REPORT))
                    .map(CargoReportingUtil::randomlyUpdateCargoDto)
                    .forEach(cargoDto -> {
                        try {
                            cargoReportingService.processCargoReport(jwt, cargoDto);
                        } catch (final JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }

        for (int i = 0; i < operationPercentages.get(CREATE_NEW_REPORT); i++) {
            cargoReportingService.processCargoReport(jwt, CargoReportingUtil.createCargoDto());
        }
    }
}
