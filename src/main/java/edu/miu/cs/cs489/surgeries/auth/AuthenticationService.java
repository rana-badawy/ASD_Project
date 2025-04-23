package edu.miu.cs.cs489.surgeries.auth;

import edu.miu.cs.cs489.surgeries.config.JwtService;
import edu.miu.cs.cs489.surgeries.dto.request.AuthenticationRequest;
import edu.miu.cs.cs489.surgeries.dto.request.RegisterRequest;
import edu.miu.cs.cs489.surgeries.dto.response.AuthenticationResponse;
import edu.miu.cs.cs489.surgeries.model.User;
import edu.miu.cs.cs489.surgeries.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.username(),
                        authenticationRequest.password()
                )
        );

        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse registerUser(RegisterRequest registerRequest) {
        User user = new User(
                registerRequest.firstName(),
                registerRequest.lastName(),
                registerRequest.username(),
                passwordEncoder.encode(registerRequest.password()),
                registerRequest.role());

        User registeredUser = userRepository.save(user);

        String token = jwtService.generateToken(registeredUser);

        return new AuthenticationResponse(token);
    }
}
