package edu.miu.cs.cs489.surgeries.mapper;

import edu.miu.cs.cs489.surgeries.dto.request.DentistRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.DentistResponseDto;
import edu.miu.cs.cs489.surgeries.model.Dentist;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DentistMapper {
    Dentist dentistRequestDtoToDentist(DentistRequestDto dentistRequestDto);

    DentistResponseDto dentistToDentistResponseDto(Dentist dentist);

    List<DentistResponseDto> dentistsToDentistResponseDtoList(List<Dentist> dentists);
}
