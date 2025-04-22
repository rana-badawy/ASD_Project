package edu.miu.cs.cs489.surgeries.dto.request;

public record AddressRequestDto(
        String street,
        String city,
        String state,
        String zipCode
) {
}
