package edu.miu.cs.cs489.surgeries.service;

import edu.miu.cs.cs489.surgeries.dto.request.PatientRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.PatientResponseDto;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    List<PatientResponseDto> getAllPatients();

    List<PatientResponseDto> findAllByFirstName(String firstName);

    List<PatientResponseDto> findAllByLastName(String lastName);

    List<PatientResponseDto> getAllPatientsSortedByLastName();

    List<PatientResponseDto> findAllPatientsWithName(String firstName, String lastName);

    List<PatientResponseDto> findAllPatientsMatchingName(String name);

    List<PatientResponseDto> findPatientsWithAgeRange(Integer minAge, Integer maxAge);

    Optional<PatientResponseDto> findPatientById(Integer patientId);

    Optional<PatientResponseDto> findPatientByPatientNo(String patientNo);

    Optional<PatientResponseDto> addPatient(PatientRequestDto patientRequestDto);

    Optional<PatientResponseDto> updatePatient(PatientRequestDto patientRequestDto, Integer patientId);

    void deletePatientById(Integer patientId);
}