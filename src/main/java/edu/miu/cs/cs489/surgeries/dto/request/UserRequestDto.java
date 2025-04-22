package edu.miu.cs.cs489.surgeries.dto.request;

public record UserRequestDto(
        String username,
        String password,
        RoleRequestDto roleRequestDto
) {
}
