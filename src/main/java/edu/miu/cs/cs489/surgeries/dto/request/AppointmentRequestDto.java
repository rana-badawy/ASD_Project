package edu.miu.cs.cs489.surgeries.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentRequestDto(
        LocalDate appointmentDate,
        LocalTime appointmentTime,
        Integer surgeryId,
        Integer dentistId,
        Integer patientId
) {
}
