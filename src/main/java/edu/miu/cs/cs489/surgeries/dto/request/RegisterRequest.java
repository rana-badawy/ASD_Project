package edu.miu.cs.cs489.surgeries.dto.request;

import edu.miu.cs.cs489.surgeries.model.Role;

public record RegisterRequest (
        String firstName,
        String lastName,
        String username,
        String password,
        Role role
) {
}
