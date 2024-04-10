package com.pavlob.token;

import com.pavlob.token.model.ClientTokenRequest;
import com.pavlob.token.model.JwtAuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/token")
public interface TokenApi {

    @PostMapping("/client")
    ResponseEntity<JwtAuthenticationResponse> getClientToken(@RequestBody ClientTokenRequest clientTokenRequest);
}
