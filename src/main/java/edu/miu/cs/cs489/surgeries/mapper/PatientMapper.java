package edu.miu.cs.cs489.surgeries.mapper;

import edu.miu.cs.cs489.surgeries.dto.request.PatientRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.PatientResponseDto;
import edu.miu.cs.cs489.surgeries.model.Patient;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AddressMapper.class})
public interface PatientMapper {
    @Mapping(source = "addressRequestDto", target = "address")
    Patient patientRequestDtoToPatient(PatientRequestDto patientRequestDto);

    @Mapping(source = "address", target = "addressResponseDto")
    PatientResponseDto patientToPatientResponseDto(Patient patient);

    @Mapping(source = "address", target = "addressResponseDto")
    List<PatientResponseDto> patientToPatientResponseDtoList(List<Patient> patients);
}
