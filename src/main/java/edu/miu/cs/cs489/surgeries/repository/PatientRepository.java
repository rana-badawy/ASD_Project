package edu.miu.cs.cs489.surgeries.repository;

import edu.miu.cs.cs489.surgeries.model.Patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Optional<Patient> findPatientByPatientNo(String patientNo);

    List<Patient> findAllByFirstName(String firstName);

    List<Patient> findAllByLastName(String lastName);

    @Query("select p from Patient p left join fetch p.address order by p.lastName")
    List<Patient> getAllPatientsSortedByLastName();

    @Query("select p from Patient p where lower(p.firstName) = lower(:firstName) and lower(p.lastName) = lower(:lastName)")
    List<Patient> getAllPatientsWithName(String firstName, String lastName);

    @Query("select p from Patient p where lower(p.firstName) like lower(concat('%', :name, '%')) or lower(p.lastName) like lower(concat('%', :name, '%'))")
    List<Patient> getAllPatientsMatchingName(String name);

    @Query("select p from Patient p where timestampdiff(year, p.dob, CURDATE()) BETWEEN :minAge AND :maxAge")
    List<Patient> findPatientsWithAgeRange(Integer minAge, Integer maxAge);
}