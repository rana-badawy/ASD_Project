package edu.miu.cs.cs489.surgeries.repository;

import edu.miu.cs.cs489.surgeries.model.Surgery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SurgeryRepository extends JpaRepository<Surgery, Integer> {
    Optional<Surgery> findBySurgeryNo(String surgeryNo);

    Optional<Surgery> findByName(String name);

    @Query("select s from Surgery s where s.address.city = :city and s.address.state = :state")
    List<Surgery> findByCity(String city, String state);
}
