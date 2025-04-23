package edu.miu.cs.cs489.surgeries.dto.request;

public record AuthenticationRequest(
        String username,
        String password
) {
}
