package edu.miu.cs.cs489.surgeries.dto.request;

public record DentistRequestDto(
        String dentistNo,
        String firstName,
        String lastName,
        String email,
        String phone,
        String specialization
) {
}
