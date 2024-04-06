package com.pavlob.token.model;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {

    private String jwtToken;
    private TokenLevel tokenLevel;
}
