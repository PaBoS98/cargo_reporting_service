package com.pavlob.util;

import com.github.javafaker.Faker;
import com.pavlob.Constant;
import com.pavlob.cargoReporting.model.CargoDto;
import com.pavlob.cargoReporting.model.DeliveryStatusDto;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
public class CargoReportingUtil {

    private static final Faker FAKER = new Faker();

    public static Map<Constant.RandomlyCargoOperations, Integer> randomizeOperationPercentagesFromValue(final int countGeneratedReports) {
        final Map<Constant.RandomlyCargoOperations, Integer> result = new HashMap<>();
        final Constant.RandomlyCargoOperations[] operations = Constant.RandomlyCargoOperations.values();
        final Random random = new Random();

        int remaining = countGeneratedReports;
        for (int i = 0; i < operations.length-1; i++) {
            final int randomValue = random.nextInt(remaining + 1);
            result.put(operations[i], randomValue);
            remaining -= randomValue;
        }
        result.put(operations[operations.length - 1], remaining);

        log.debug("Quantity randomized for operations {}", result);
        return result;
    }

    public static CargoDto createCargoDto() {
        final CargoDto cargoDto = new CargoDto();

        cargoDto.setName(FAKER.commerce().productName());
        cargoDto.setDeliveryCity(FAKER.address().city());
        cargoDto.setCargoLocation(FAKER.address().city());
        cargoDto.setDeliveryStatus(DeliveryStatusDto.COLLECTING);

        log.debug("created cargo dto {}", cargoDto);
        return cargoDto;
    }


    public static CargoDto randomlyUpdateCargoDto(final CargoDto cargoDto) {
        log.debug("cargo dto for randomly update {}", cargoDto);
        if (cargoDto.getDeliveryStatus().equals(DeliveryStatusDto.COLLECTING)){
            cargoDto.setDeliveryStatus(DeliveryStatusDto.ON_THE_WAY);
        } else {
            final Random random = new Random();

            // simple solution for adding random in update action
            if (random.nextInt(2) == 0) { // change location
                cargoDto.setCargoLocation(FAKER.address().city());
            } else { // the cargo arrived at its destination
                cargoDto.setDeliveryStatus(DeliveryStatusDto.ARRIVED);
                cargoDto.setCargoLocation(cargoDto.getDeliveryCity());
            }
        }
        log.debug("cargo dto randomly updated {}", cargoDto);
        return cargoDto;
    }
}
