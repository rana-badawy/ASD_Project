package edu.miu.cs.cs489.surgeries.service;

import edu.miu.cs.cs489.surgeries.dto.request.AppointmentRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.AppointmentResponseDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Page<AppointmentResponseDto> findByDentistId(
            Integer dentistId,
            int page,
            int pageSize,
            String sortDirection,
            String sortBy
    );

    Page<AppointmentResponseDto> findBySurgeryId(
            Integer surgeryId,
            int page,
            int pageSize,
            String sortDirection,
            String sortBy
    );

    List<AppointmentResponseDto> getAllAppointments();

    List<AppointmentResponseDto> findByPatientId(Integer patientId);

    List<AppointmentResponseDto> findByAppointmentDate(LocalDate date);

    Optional<AppointmentResponseDto> findAppointmentById(Integer appointmentId);

    Optional<AppointmentResponseDto> addAppointment(AppointmentRequestDto appointmentRequestDto);

    Optional<AppointmentResponseDto> updateAppointment(AppointmentRequestDto appointmentRequestDto, Integer appointmentId);

    void deleteAppointmentById(Integer appointmentId);
}
