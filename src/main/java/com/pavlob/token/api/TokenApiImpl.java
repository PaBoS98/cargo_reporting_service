package com.pavlob.token.api;

import com.pavlob.cargoReporting.model.CargoDto;
import com.pavlob.cargoReporting.model.DeliveryStatusDto;
import com.pavlob.cargoReporting.resource.CargoTrackingApiClient;
import com.pavlob.config.jwt.JwtTokenProvider;
import com.pavlob.token.model.TokenLevel;
import com.pavlob.token.TokenApi;
import com.pavlob.token.model.ClientTokenRequest;
import com.pavlob.token.model.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TokenApiImpl implements TokenApi {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public ResponseEntity<JwtAuthenticationResponse> getClientToken(final ClientTokenRequest clientTokenRequest) {
        final String clientId = clientTokenRequest.getClientId();

        final JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setJwtToken(tokenProvider.generateClientToken(clientId));
        jwtAuthenticationResponse.setTokenLevel(TokenLevel.CLIENT);

        return ResponseEntity.status(HttpStatus.CREATED).body(jwtAuthenticationResponse);
    }

    @Autowired
    private CargoTrackingApiClient cargoTrackingApiClient;

    @Override
    public List<CargoDto> debug() {
        return cargoTrackingApiClient.getAllCargoWhereStatusNot(DeliveryStatusDto.ARRIVED);
    }
}
