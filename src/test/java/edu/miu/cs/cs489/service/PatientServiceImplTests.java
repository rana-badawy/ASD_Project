package edu.miu.cs.cs489.service;

import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.miu.cs.cs489.surgeries.dto.request.AddressRequestDto;
import edu.miu.cs.cs489.surgeries.dto.request.PatientRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.AddressResponseDto;
import edu.miu.cs.cs489.surgeries.dto.response.PatientResponseDto;
import edu.miu.cs.cs489.surgeries.exception.DuplicateException;
import edu.miu.cs.cs489.surgeries.exception.NotFoundException;
import edu.miu.cs.cs489.surgeries.mapper.PatientMapper;
import edu.miu.cs.cs489.surgeries.model.Address;
import edu.miu.cs.cs489.surgeries.model.Patient;
import edu.miu.cs.cs489.surgeries.repository.AddressRepository;
import edu.miu.cs.cs489.surgeries.repository.PatientRepository;
import edu.miu.cs.cs489.surgeries.service.impl.PatientServiceImpl;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceImplTests {
    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientServiceImpl patientService;


    private final AddressRequestDto addressRequestDto = new AddressRequestDto("1000 N", "Fairfield", "Iowa", "52556");
    private final AddressResponseDto addressResponseDto = new AddressResponseDto(1, "1000 N", "Fairfield", "Iowa", "52556");
    
    private final Address address = new Address("1000 N", "Fairfield", "Iowa", "52556");

    private final Patient patient = new Patient("123", "John", "Doe", "a@yahoo.com", "1234567890", LocalDate.of(1990, 1, 1), address);

    private final PatientRequestDto patientRequestDto = new PatientRequestDto("123", "John", "Doe", "a@yahoo.com", "1234567890", LocalDate.of(1990, 1, 1), addressRequestDto);
    private final PatientResponseDto patientResponseDto = new PatientResponseDto(1, "123", "John", "Doe", "a@yahoo.com", "1234567890", LocalDate.of(1990, 1, 1), addressResponseDto);



    @Test
    @DisplayName("Test getAllPatients()")
    public void testGetAllPatients() {
        List<Patient> patients = List.of(patient);
        List<PatientResponseDto> patientResponseDtos = List.of(patientResponseDto);

        Mockito.when(patientRepository.findAll()).thenReturn(patients);
        Mockito.when(patientMapper.patientToPatientResponseDtoList(patients)).thenReturn(patientResponseDtos);

        List<PatientResponseDto> result = patientService.getAllPatients();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).patientId()).isEqualTo(1);
        Assertions.assertThat(result.get(0).patientNo()).isEqualTo("123");
        Assertions.assertThat(result.get(0).firstName()).isEqualTo("John");
        Assertions.assertThat(result.get(0).lastName()).isEqualTo("Doe");
        Assertions.assertThat(result.get(0).email()).isEqualTo("a@yahoo.com");
        Assertions.assertThat(result.get(0).phone()).isEqualTo("1234567890");
        Assertions.assertThat(result.get(0).dob()).isEqualTo(LocalDate.of(1990, 1, 1));
        Assertions.assertThat(result.get(0).addressResponseDto()).isEqualTo(addressResponseDto);
    }

    @Test
    @DisplayName("Test findAllByFirstName()")
    public void testFindAllByFirstName() {
        List<Patient> patients = List.of(patient);
        List<PatientResponseDto> patientResponseDtos = List.of(patientResponseDto);

        Mockito.when(patientRepository.findAllByFirstName("John")).thenReturn(patients);
        Mockito.when(patientMapper.patientToPatientResponseDtoList(patients)).thenReturn(patientResponseDtos);

        List<PatientResponseDto> result = patientService.findAllByFirstName("John");

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).patientId()).isEqualTo(1);
        Assertions.assertThat(result.get(0).patientNo()).isEqualTo("123");
        Assertions.assertThat(result.get(0).firstName()).isEqualTo("John");
        Assertions.assertThat(result.get(0).lastName()).isEqualTo("Doe");
        Assertions.assertThat(result.get(0).email()).isEqualTo("a@yahoo.com");
        Assertions.assertThat(result.get(0).phone()).isEqualTo("1234567890");
        Assertions.assertThat(result.get(0).dob()).isEqualTo(LocalDate.of(1990, 1, 1));
        Assertions.assertThat(result.get(0).addressResponseDto()).isEqualTo(addressResponseDto);
    }

    @Test
    @DisplayName("Test findAllByLastName()")
    public void testFindAllByLastName() {
        List<Patient> patients = List.of(patient);
        List<PatientResponseDto> patientResponseDtos = List.of(patientResponseDto);

        Mockito.when(patientRepository.findAllByLastName("Doe")).thenReturn(patients);
        Mockito.when(patientMapper.patientToPatientResponseDtoList(patients)).thenReturn(patientResponseDtos);

        List<PatientResponseDto> result = patientService.findAllByLastName("Doe");

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).patientId()).isEqualTo(1);
        Assertions.assertThat(result.get(0).patientNo()).isEqualTo("123");
        Assertions.assertThat(result.get(0).firstName()).isEqualTo("John");
        Assertions.assertThat(result.get(0).lastName()).isEqualTo("Doe");
        Assertions.assertThat(result.get(0).email()).isEqualTo("a@yahoo.com");
        Assertions.assertThat(result.get(0).phone()).isEqualTo("1234567890");
        Assertions.assertThat(result.get(0).dob()).isEqualTo(LocalDate.of(1990, 1, 1));
        Assertions.assertThat(result.get(0).addressResponseDto()).isEqualTo(addressResponseDto);
    }

    @Test
    @DisplayName("Test getAllPatientsSortedByLastName()")
    public void testGetAllPatientsSortedByLastName() {
        List<Patient> patients = List.of(patient);
        List<PatientResponseDto> patientResponseDtos = List.of(patientResponseDto);

        Mockito.when(patientRepository.getAllPatientsSortedByLastName()).thenReturn(patients);
        Mockito.when(patientMapper.patientToPatientResponseDtoList(patients)).thenReturn(patientResponseDtos);

        List<PatientResponseDto> result = patientService.getAllPatientsSortedByLastName();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).patientId()).isEqualTo(1);
        Assertions.assertThat(result.get(0).patientNo()).isEqualTo("123");
        Assertions.assertThat(result.get(0).firstName()).isEqualTo("John");
        Assertions.assertThat(result.get(0).lastName()).isEqualTo("Doe");
        Assertions.assertThat(result.get(0).email()).isEqualTo("a@yahoo.com");
        Assertions.assertThat(result.get(0).phone()).isEqualTo("1234567890");
        Assertions.assertThat(result.get(0).dob()).isEqualTo(LocalDate.of(1990, 1, 1));
        Assertions.assertThat(result.get(0).addressResponseDto()).isEqualTo(addressResponseDto);
    }

    @Test
    @DisplayName("Test findAllPatientsWithName()")
    public void testFindAllPatientsWithName() {
        List<Patient> patients = List.of(patient);
        List<PatientResponseDto> patientResponseDtos = List.of(patientResponseDto);

        Mockito.when(patientRepository.getAllPatientsWithName("John", "Doe")).thenReturn(patients);
        Mockito.when(patientMapper.patientToPatientResponseDtoList(patients)).thenReturn(patientResponseDtos);

        List<PatientResponseDto> result = patientService.findAllPatientsWithName("John", "Doe");

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).patientId()).isEqualTo(1);
        Assertions.assertThat(result.get(0).patientNo()).isEqualTo("123");
        Assertions.assertThat(result.get(0).firstName()).isEqualTo("John");
        Assertions.assertThat(result.get(0).lastName()).isEqualTo("Doe");
        Assertions.assertThat(result.get(0).email()).isEqualTo("a@yahoo.com");
        Assertions.assertThat(result.get(0).phone()).isEqualTo("1234567890");
        Assertions.assertThat(result.get(0).dob()).isEqualTo(LocalDate.of(1990, 1, 1));
        Assertions.assertThat(result.get(0).addressResponseDto()).isEqualTo(addressResponseDto);
    }

    @Test
    @DisplayName("Test findAllPatientsMatchingName()")
    public void testFindAllPatientsMatchingName() {
        List<Patient> patients = List.of(patient);
        List<PatientResponseDto> patientResponseDtos = List.of(patientResponseDto);

        Mockito.when(patientRepository.getAllPatientsMatchingName("John")).thenReturn(patients);
        Mockito.when(patientMapper.patientToPatientResponseDtoList(patients)).thenReturn(patientResponseDtos);

        List<PatientResponseDto> result = patientService.findAllPatientsMatchingName("John");

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).patientId()).isEqualTo(1);
        Assertions.assertThat(result.get(0).patientNo()).isEqualTo("123");
        Assertions.assertThat(result.get(0).firstName()).isEqualTo("John");
        Assertions.assertThat(result.get(0).lastName()).isEqualTo("Doe");
        Assertions.assertThat(result.get(0).email()).isEqualTo("a@yahoo.com");
        Assertions.assertThat(result.get(0).phone()).isEqualTo("1234567890");
        Assertions.assertThat(result.get(0).dob()).isEqualTo(LocalDate.of(1990, 1, 1));
        Assertions.assertThat(result.get(0).addressResponseDto()).isEqualTo(addressResponseDto);
    }

    @Test
    @DisplayName("Test findPatientsWithAgeRange()")
    public void testFindPatientsWithAgeRange() {
        List<Patient> patients = List.of(patient);
        List<PatientResponseDto> patientResponseDtos = List.of(patientResponseDto);

        Mockito.when(patientRepository.findPatientsWithAgeRange(20, 30)).thenReturn(patients);
        Mockito.when(patientMapper.patientToPatientResponseDtoList(patients)).thenReturn(patientResponseDtos);

        List<PatientResponseDto> result = patientService.findPatientsWithAgeRange(20, 30);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).patientId()).isEqualTo(1);
        Assertions.assertThat(result.get(0).patientNo()).isEqualTo("123");
        Assertions.assertThat(result.get(0).firstName()).isEqualTo("John");
        Assertions.assertThat(result.get(0).lastName()).isEqualTo("Doe");
        Assertions.assertThat(result.get(0).email()).isEqualTo("a@yahoo.com");
        Assertions.assertThat(result.get(0).phone()).isEqualTo("1234567890");
        Assertions.assertThat(result.get(0).dob()).isEqualTo(LocalDate.of(1990, 1, 1));
        Assertions.assertThat(result.get(0).addressResponseDto()).isEqualTo(addressResponseDto);
    }
    @Test
    @DisplayName("Test findPatientById()")
    public void testFindPatientById() {
        Mockito.when(patientRepository.findById(1)).thenReturn(Optional.of(patient));
        Mockito.when(patientMapper.patientToPatientResponseDto(patient)).thenReturn(patientResponseDto);

        Optional<PatientResponseDto> result = patientService.findPatientById(1);

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().patientId()).isEqualTo(1);
        Assertions.assertThat(result.get().patientNo()).isEqualTo("123");
        Assertions.assertThat(result.get().firstName()).isEqualTo("John");
        Assertions.assertThat(result.get().lastName()).isEqualTo("Doe");
        Assertions.assertThat(result.get().email()).isEqualTo("a@yahoo.com");
        Assertions.assertThat(result.get().phone()).isEqualTo("1234567890");
        Assertions.assertThat(result.get().dob()).isEqualTo(LocalDate.of(1990, 1, 1));
        Assertions.assertThat(result.get().addressResponseDto()).isEqualTo(addressResponseDto);
    }

    @Test
    @DisplayName("Test findPatientById() - Not Found")
    public void testFindPatientByIdNotFound() {
        Mockito.when(patientRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            patientService.findPatientById(1);
        });

        Assertions.assertThat(exception.getMessage()).isEqualTo("Patient with ID 1 does not exist");
    }
    @Test
    @DisplayName("Test findPatientByPatientNo()")
    public void testFindPatientByPatientNo() {
        Mockito.when(patientRepository.findPatientByPatientNo("123")).thenReturn(Optional.of(patient));
        Mockito.when(patientMapper.patientToPatientResponseDto(patient)).thenReturn(patientResponseDto);

        Optional<PatientResponseDto> result = patientService.findPatientByPatientNo("123");

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().patientId()).isEqualTo(1);
        Assertions.assertThat(result.get().patientNo()).isEqualTo("123");
        Assertions.assertThat(result.get().firstName()).isEqualTo("John");
        Assertions.assertThat(result.get().lastName()).isEqualTo("Doe");
        Assertions.assertThat(result.get().email()).isEqualTo("a@yahoo.com");
        Assertions.assertThat(result.get().phone()).isEqualTo("1234567890");
        Assertions.assertThat(result.get().dob()).isEqualTo(LocalDate.of(1990, 1, 1));
        Assertions.assertThat(result.get().addressResponseDto()).isEqualTo(addressResponseDto);
    }
    @Test
    @DisplayName("Test findPatientByPatientNo() - Not Found")
    public void testFindPatientByPatientNoNotFound() {
        Mockito.when(patientRepository.findPatientByPatientNo("123")).thenReturn(Optional.empty());

        Optional<PatientResponseDto> result = patientService.findPatientByPatientNo("123");

        Assertions.assertThat(result).isNotPresent();
    }
    @Test
    @DisplayName("Test addPatient()")
    public void testAddPatient() {
        Mockito.when(patientRepository.findPatientByPatientNo("123")).thenReturn(Optional.empty());
        Mockito.when(addressRepository.save(address)).thenReturn(address);
        Mockito.when(patientMapper.patientRequestDtoToPatient(patientRequestDto)).thenReturn(patient);
        Mockito.when(patientRepository.save(patient)).thenReturn(patient);
        Mockito.when(patientMapper.patientToPatientResponseDto(patient)).thenReturn(patientResponseDto);

        Optional<PatientResponseDto> result = patientService.addPatient(patientRequestDto);

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().patientId()).isEqualTo(1);
        Assertions.assertThat(result.get().patientNo()).isEqualTo("123");
        Assertions.assertThat(result.get().firstName()).isEqualTo("John");
        Assertions.assertThat(result.get().lastName()).isEqualTo("Doe");
        Assertions.assertThat(result.get().email()).isEqualTo("a@yahoo.com");
        Assertions.assertThat(result.get().phone()).isEqualTo("1234567890");
        Assertions.assertThat(result.get().dob()).isEqualTo(LocalDate.of(1990, 1, 1));
        Assertions.assertThat(result.get().addressResponseDto()).isEqualTo(addressResponseDto);
    }
    @Test
    @DisplayName("Test addPatient() - Duplicate PatientNo")
    public void testAddPatientDuplicate() {
        Mockito.when(patientRepository.findPatientByPatientNo("123")).thenReturn(Optional.of(patient));

        DuplicateException exception = assertThrows(DuplicateException.class, () -> {
            patientService.addPatient(patientRequestDto);
        });

        Assertions.assertThat(exception.getMessage()).isEqualTo("Patient with number 123 already exists");
    }
    @Test
    @DisplayName("Test updatePatient()")
    public void testUpdatePatient() {
        Mockito.when(patientRepository.findById(1)).thenReturn(Optional.of(patient));
        Mockito.when(patientMapper.patientRequestDtoToPatient(patientRequestDto)).thenReturn(patient);
        Mockito.when(addressRepository.save(address)).thenReturn(address);
        Mockito.when(patientRepository.save(patient)).thenReturn(patient);
        Mockito.when(patientMapper.patientToPatientResponseDto(patient)).thenReturn(patientResponseDto);

        Optional<PatientResponseDto> result = patientService.updatePatient(patientRequestDto, 1);

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().patientId()).isEqualTo(1);
        Assertions.assertThat(result.get().patientNo()).isEqualTo("123");
        Assertions.assertThat(result.get().firstName()).isEqualTo("John");
        Assertions.assertThat(result.get().lastName()).isEqualTo("Doe");
        Assertions.assertThat(result.get().email()).isEqualTo("a@yahoo.com");
        Assertions.assertThat(result.get().phone()).isEqualTo("1234567890");
        Assertions.assertThat(result.get().dob()).isEqualTo(LocalDate.of(1990, 1, 1));
        Assertions.assertThat(result.get().addressResponseDto()).isEqualTo(addressResponseDto);
    }

    @Test
    @DisplayName("Test updatePatient() - Not Found")
    public void testUpdatePatientNotFound() {
        Mockito.when(patientRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            patientService.updatePatient(patientRequestDto, 1);
        });

        Assertions.assertThat(exception.getMessage()).isEqualTo("Patient with ID 1 does not exist");
    }

    @Test
    @DisplayName("Test deletePatient()")
    public void testDeletePatient() {
        Mockito.when(patientRepository.findById(1)).thenReturn(Optional.of(patient));


        patientService.deletePatientById(1);

        Mockito.verify(patientRepository, Mockito.times(1)).delete(patient);
    }
}


