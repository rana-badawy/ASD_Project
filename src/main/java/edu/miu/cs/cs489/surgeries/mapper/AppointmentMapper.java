package edu.miu.cs.cs489.surgeries.mapper;

import edu.miu.cs.cs489.surgeries.dto.request.AppointmentRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.AppointmentResponseDto;
import edu.miu.cs.cs489.surgeries.model.Appointment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {SurgeryMapper.class, DentistMapper.class, PatientMapper.class})
public interface AppointmentMapper {
    Appointment appointmentRequestDtoToAppointment(AppointmentRequestDto appointmentRequestDto);

    @Mappings({
            @Mapping(source = "surgery", target = "surgeryResponseDto"),
            @Mapping(source = "dentist", target = "dentistResponseDto"),
            @Mapping(source = "patient", target = "patientResponseDto")
    })
    AppointmentResponseDto appointmentToAppointmentResponseDto(Appointment appointment);

    @Mappings({
            @Mapping(source = "surgery", target = "surgeryResponseDto"),
            @Mapping(source = "dentist", target = "dentistResponseDto"),
            @Mapping(source = "patient", target = "patientResponseDto")
    })
    List<AppointmentResponseDto> appointmentsToAppointmentResponseDtoList(List<Appointment> appointments);
}
