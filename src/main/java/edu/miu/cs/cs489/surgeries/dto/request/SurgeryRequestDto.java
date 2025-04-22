package edu.miu.cs.cs489.surgeries.dto.request;


public record SurgeryRequestDto(
        String surgeryNo,
        String name,
        String phone,
        AddressRequestDto addressRequestDto
) {
}
