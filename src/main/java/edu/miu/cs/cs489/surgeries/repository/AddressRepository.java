package edu.miu.cs.cs489.surgeries.repository;

import edu.miu.cs.cs489.surgeries.model.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query("select a from Address a where a.street = :street and a.city = :city and a.state = :state and a.zipCode = :zipCode")
    Optional<Address> getAddress(String street, String city, String state, String zipCode);
}
