package edu.miu.cs.cs489.surgeries.dto.response;

public record SurgeryResponseDto(
        Integer surgeryId,
        String surgeryNo,
        String name,
        String phone,
        AddressResponseDto addressResponseDto
) {
}
