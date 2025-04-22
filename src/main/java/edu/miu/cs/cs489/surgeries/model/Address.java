package edu.miu.cs.cs489.surgeries.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    @Column(nullable = false)
    @NotBlank(message = "Street is required")
    private String street;

    @Column(nullable = false)
    @NotBlank(message = "City is required")
    private String city;

    @Column(nullable = false)
    @NotBlank(message = "State is required")
    private String state;

    @Column(nullable = false, length = 16)
    @NotNull(message = "Zip Code is required")
    private String zipCode;

    public Address() {}

    public Address(String street, String city, String state, String zipCode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Address{");
        sb.append("addressId=").append(addressId);
        sb.append(", street='").append(street).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", zipCode='").append(zipCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
