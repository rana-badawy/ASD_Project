package edu.miu.cs.cs489.surgeries.dto.request;

import java.time.LocalDate;

public record PatientRequestDto(
        String patientNo,
        String firstName,
        String lastName,
        String email,
        String phone,
        LocalDate dob,
        AddressRequestDto addressRequestDto
) {
}
