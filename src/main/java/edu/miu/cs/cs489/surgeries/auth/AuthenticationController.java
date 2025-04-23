package edu.miu.cs.cs489.surgeries.auth;

import edu.miu.cs.cs489.surgeries.dto.request.AuthenticationRequest;
import edu.miu.cs.cs489.surgeries.dto.request.RegisterRequest;
import edu.miu.cs.cs489.surgeries.dto.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse authenticationResponse = authenticationService.registerUser(registerRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticateUser(authenticationRequest);

        return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse);
    }
}
