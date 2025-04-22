package edu.miu.cs.cs489.surgeries.dto.response;

public record AddressResponseDto(
        Integer addressId,
        String street,
        String city,
        String state,
        String zipCode
) {
}
