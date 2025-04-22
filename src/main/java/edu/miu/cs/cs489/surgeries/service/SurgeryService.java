package edu.miu.cs.cs489.surgeries.service;

import edu.miu.cs.cs489.surgeries.dto.request.SurgeryRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.SurgeryResponseDto;

import java.util.List;
import java.util.Optional;

public interface SurgeryService {

    List<SurgeryResponseDto> getAllSurgeries();

    List<SurgeryResponseDto> findByCity(String city, String state);

    Optional<SurgeryResponseDto> findSurgeryById(Integer surgeryId);

    Optional<SurgeryResponseDto> findBySurgeryNo(String surgeryNo);

    Optional<SurgeryResponseDto> findSurgeryByName(String name);

    Optional<SurgeryResponseDto> addSurgery(SurgeryRequestDto surgeryRequestDto);

    Optional<SurgeryResponseDto> updateSurgery(SurgeryRequestDto surgeryRequestDto, Integer surgeryId);

    void deleteSurgeryById(Integer surgeryId);
}
