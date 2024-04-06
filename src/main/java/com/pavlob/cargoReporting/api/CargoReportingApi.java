package com.pavlob.cargoReporting.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pavlob.token.model.JwtToken;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/cargoReporting")
public interface CargoReportingApi {

    @PostMapping("/generate")
    @SecurityRequirement(name = "Authorization")
    void generateCargoReports(@AuthenticationPrincipal final JwtToken token, @RequestParam(value = "countGeneratedReports") int countGeneratedReports) throws JsonProcessingException;
}
