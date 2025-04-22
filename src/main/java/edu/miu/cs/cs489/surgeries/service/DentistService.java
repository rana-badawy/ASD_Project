package edu.miu.cs.cs489.surgeries.service;

import edu.miu.cs.cs489.surgeries.dto.request.DentistRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.DentistResponseDto;

import java.util.List;
import java.util.Optional;

public interface DentistService {
    List<DentistResponseDto> getAllDentists();

    List<DentistResponseDto> findAllBySpecialization(String specialization);

    List<DentistResponseDto> findAllByFirstName(String firstName);

    List<DentistResponseDto> findAllByLastName(String lastName);

    List<DentistResponseDto> getAllDentistsWithName(String firstName, String lastName);

    Optional<DentistResponseDto> getDentistById(Integer dentistId);

    Optional<DentistResponseDto> findDentistByDentistNo(String dentistNo);

    Optional<DentistResponseDto> addDentist(DentistRequestDto dentistRequestDto);

    Optional<DentistResponseDto> updateDentist(DentistRequestDto dentistRequestDto, Integer dentistId);

    void deleteDentistById(Integer dentistId);
}