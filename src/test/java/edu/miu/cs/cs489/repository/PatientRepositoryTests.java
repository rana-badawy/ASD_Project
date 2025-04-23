package edu.miu.cs.cs489.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import edu.miu.cs.cs489.surgeries.model.Address;
import edu.miu.cs.cs489.surgeries.model.Patient;
import edu.miu.cs.cs489.surgeries.repository.AddressRepository;
import edu.miu.cs.cs489.surgeries.repository.PatientRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class PatientRepositoryTests {
    private Address address = new Address("1000 N", "Fairfield", "IA", "52557");
    private Patient patient = new Patient("P123", "John", "Doe", "a@yahoo.com", "1234567890", LocalDate.of(2000, 1, 1), address);


    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Test findPatientByPatientNo")
    public void testFindPatientByPatientNo() {
        addressRepository.saveAndFlush(address);
        patientRepository.saveAndFlush(patient);

        Optional<Patient> foundPatient = patientRepository.findPatientByPatientNo(patient.getPatientNo());

        Assertions.assertThat(foundPatient).isPresent();
        Assertions.assertThat(foundPatient.get().getPatientNo()).isEqualTo(patient.getPatientNo());
    }
    @Test
    @DisplayName("Test findAllByFirstName")
    public void testFindAllByFirstName() {
        addressRepository.saveAndFlush(address);
        patientRepository.saveAndFlush(patient);

        List<Patient> foundPatients = patientRepository.findAllByFirstName(patient.getFirstName());

        Assertions.assertThat(foundPatients).isNotEmpty();
        Assertions.assertThat(foundPatients).contains(patient);
    }
    @Test
    @DisplayName("Test findAllByLastName")
    public void testFindAllByLastName() {
        addressRepository.saveAndFlush(address);
        patientRepository.saveAndFlush(patient);

        List<Patient> foundPatients = patientRepository.findAllByLastName(patient.getLastName());

        Assertions.assertThat(foundPatients).isNotEmpty();
        Assertions.assertThat(foundPatients).contains(patient);
    }
    @Test
    @DisplayName("Test getAllPatientsSortedByLastName")
    public void testGetAllPatientsSortedByLastName() {
        addressRepository.saveAndFlush(address);
        patientRepository.saveAndFlush(patient);

        List<Patient> foundPatients = patientRepository.getAllPatientsSortedByLastName();

        Assertions.assertThat(foundPatients).isNotEmpty();
        Assertions.assertThat(foundPatients).contains(patient);
    }
    @Test
    @DisplayName("Test getAllPatientsWithName")
    public void testGetAllPatientsWithName() {
        addressRepository.saveAndFlush(address);
        patientRepository.saveAndFlush(patient);

        List<Patient> foundPatients = patientRepository.getAllPatientsWithName(patient.getFirstName(), patient.getLastName());

        Assertions.assertThat(foundPatients).isNotEmpty();
        Assertions.assertThat(foundPatients).contains(patient);
    }

    @Test
    @DisplayName("Test getAllPatientsMatchingName")
    public void testGetAllPatientsMatchingName() {
        addressRepository.saveAndFlush(address);
        patientRepository.saveAndFlush(patient);

        List<Patient> foundPatients = patientRepository.getAllPatientsMatchingName("John");

        Assertions.assertThat(foundPatients).isNotEmpty();
        Assertions.assertThat(foundPatients).contains(patient);
    }
    @Test
    @DisplayName("Test findPatientsWithAgeRange")
    public void testFindPatientsWithAgeRange() {
        addressRepository.saveAndFlush(address);
        patientRepository.saveAndFlush(patient);

        List<Patient> foundPatients = patientRepository.findPatientsWithAgeRange(20, 30);

        Assertions.assertThat(foundPatients).isNotEmpty();
        Assertions.assertThat(foundPatients).contains(patient);
        Assertions.assertThat(foundPatients.get(0).getDob()).isEqualTo(patient.getDob());
    }

}
