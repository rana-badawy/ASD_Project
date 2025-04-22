package edu.miu.cs.cs489.surgeries.mapper;

import edu.miu.cs.cs489.surgeries.dto.request.AddressRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.AddressResponseDto;
import edu.miu.cs.cs489.surgeries.model.Address;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {
    Address addressRequestDtoToAddress(AddressRequestDto addressRequestDto);

    AddressResponseDto addressToAddressResponseDto(Address address);

    Address addressResponseDtoToAddress(AddressResponseDto addressResponseDto);
}
