package edu.miu.cs.cs489.surgeries.repository;

import edu.miu.cs.cs489.surgeries.model.Dentist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DentistRepository extends JpaRepository<Dentist, Integer> {
    Optional<Dentist> findByDentistNo(String dentistNo);

    List<Dentist> findAllBySpecialization(String specialization);

    List<Dentist> findAllByFirstName(String firstName);

    List<Dentist> findAllByLastName(String lastName);

    @Query("select d from Dentist d where lower(d.firstName) = lower(:firstName) and lower(d.lastName) = lower(:lastName)")
    List<Dentist> getAllDentistsWithName(String firstName, String lastName);
}
