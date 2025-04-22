package edu.miu.cs.cs489.surgeries.service;

import edu.miu.cs.cs489.surgeries.dto.request.AddressRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.AddressResponseDto;

import java.util.Optional;

public interface AddressService {
    Optional<AddressResponseDto> getAddressById(Integer addressId);

    Optional<AddressResponseDto> addAddress(AddressRequestDto addressRequestDto);

    Optional<AddressResponseDto> getAddress(String street, String city, String state, String zipCode);

    void deleteAddressById(Integer addressId);
}
