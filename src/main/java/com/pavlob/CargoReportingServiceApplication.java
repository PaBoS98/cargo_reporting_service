package com.pavlob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.pavlob"})
@SpringBootApplication
public class CargoReportingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CargoReportingServiceApplication.class, args);
	}

}
