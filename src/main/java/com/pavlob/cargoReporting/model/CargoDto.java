package com.pavlob.cargoReporting.model;

import lombok.Data;

import java.util.Date;

@Data
public class CargoDto {

    private String id;

    private String name;

    private String deliveryCity;

    private String cargoLocation;

    private DeliveryStatusDto deliveryStatus;

    private Date createdDate;

    private Date modifiedDate;
}
