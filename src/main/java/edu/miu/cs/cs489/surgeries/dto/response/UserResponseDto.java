package edu.miu.cs.cs489.surgeries.dto.response;

public record UserResponseDto(
        Integer userId,
        String username,
        RoleResponseDto roleResponseDto
) {
}
