package edu.miu.cs.cs489.surgeries.mapper;

import edu.miu.cs.cs489.surgeries.dto.request.SurgeryRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.SurgeryResponseDto;
import edu.miu.cs.cs489.surgeries.model.Surgery;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AddressMapper.class})
public interface SurgeryMapper {
    @Mapping(source = "addressRequestDto", target = "address")
    Surgery surgeryRequestDtoToSurgery(SurgeryRequestDto surgeryRequestDto);

    @Mapping(source = "address", target = "addressResponseDto")
    SurgeryResponseDto surgeryToSurgeryResponseDto(Surgery surgery);

    @Mapping(source = "address", target = "addressResponseDto")
    List<SurgeryResponseDto> surgeriesToSurgeryResponseDtoList(List<Surgery> surgeries);
}