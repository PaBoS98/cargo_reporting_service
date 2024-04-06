package com.pavlob.token.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClientTokenRequest {

    @JsonProperty("clientId")
    private String clientId;
}
