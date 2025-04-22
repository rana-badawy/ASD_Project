package edu.miu.cs.cs489.surgeries.dto.response;

import edu.miu.cs.cs489.surgeries.model.Dentist;
import edu.miu.cs.cs489.surgeries.model.Patient;
import edu.miu.cs.cs489.surgeries.model.Surgery;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResponseDto(
        Integer appointmentId,
        LocalDate appointmentDate,
        LocalTime appointmentTime,
        SurgeryResponseDto surgeryResponseDto,
        DentistResponseDto dentistResponseDto,
        PatientResponseDto patientResponseDto
) {
}
