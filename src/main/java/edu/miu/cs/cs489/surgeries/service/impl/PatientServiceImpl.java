package edu.miu.cs.cs489.surgeries.service.impl;

import edu.miu.cs.cs489.surgeries.dto.request.PatientRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.PatientResponseDto;
import edu.miu.cs.cs489.surgeries.exception.DuplicateException;
import edu.miu.cs.cs489.surgeries.exception.NotFoundException;
import edu.miu.cs.cs489.surgeries.mapper.PatientMapper;
import edu.miu.cs.cs489.surgeries.model.Address;
import edu.miu.cs.cs489.surgeries.model.Patient;
import edu.miu.cs.cs489.surgeries.repository.AddressRepository;
import edu.miu.cs.cs489.surgeries.repository.PatientRepository;
import edu.miu.cs.cs489.surgeries.service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<PatientResponseDto> getAllPatients() {
        return patientMapper.patientToPatientResponseDtoList(patientRepository.findAll());
    }

    @Override
    public List<PatientResponseDto> findAllByFirstName(String firstName) {
        return patientMapper.patientToPatientResponseDtoList(patientRepository.findAllByFirstName(firstName));
    }

    @Override
    public List<PatientResponseDto> findAllByLastName(String lastName) {
        return patientMapper.patientToPatientResponseDtoList(patientRepository.findAllByLastName(lastName));
    }

    @Override
    public List<PatientResponseDto> getAllPatientsSortedByLastName() {
        return patientMapper.patientToPatientResponseDtoList(patientRepository.getAllPatientsSortedByLastName());
    }

    @Override
    public List<PatientResponseDto> findAllPatientsWithName(String firstName, String lastName) {
        return patientMapper.patientToPatientResponseDtoList(patientRepository.getAllPatientsWithName(firstName, lastName));
    }

    @Override
    public List<PatientResponseDto> findAllPatientsMatchingName(String name) {
        return patientMapper.patientToPatientResponseDtoList(patientRepository.getAllPatientsMatchingName(name));
    }

    @Override
    public List<PatientResponseDto> findPatientsWithAgeRange(Integer minAge, Integer maxAge) {
        return patientMapper.patientToPatientResponseDtoList(patientRepository.findPatientsWithAgeRange(minAge, maxAge));
    }

    @Override
    public Optional<PatientResponseDto> findPatientById(Integer patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);

        if (patient.isPresent()) {
            return Optional.ofNullable(patientMapper.patientToPatientResponseDto(patient.get()));
        }
        throw new NotFoundException("Patient with ID " + patientId + " does not exist");
    }

    @Override
    public Optional<PatientResponseDto> findPatientByPatientNo(String patientNo) {
        Optional<Patient> patient = patientRepository.findPatientByPatientNo(patientNo);

        if (patient.isPresent()) {
            return Optional.ofNullable(patientMapper.patientToPatientResponseDto(patient.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<PatientResponseDto> addPatient(PatientRequestDto patientRequestDto) {
        Optional<Patient> optionalPatient = patientRepository.findPatientByPatientNo(patientRequestDto.patientNo());

        if (optionalPatient.isPresent()) {
            throw new DuplicateException(
                    "Patient with number " + patientRequestDto.patientNo() + " already exists");
        }

        Patient patient = patientMapper.patientRequestDtoToPatient(patientRequestDto);

        if (patient.getAddress() != null) {
            Address address = patient.getAddress();
            Optional<Address> optionalAddress = addressRepository.getAddress(
                    address.getStreet(),
                    address.getCity(),
                    address.getState(),
                    address.getZipCode());

            if (optionalAddress.isPresent()) {
                patient.getAddress().setAddressId(optionalAddress.get().getAddressId());
            }
            else {
                Address savedAddress = addressRepository.save(patient.getAddress());
                patient.getAddress().setAddressId(savedAddress.getAddressId());
            }
        }

        Patient savedPatient = patientRepository.save(patient);

        PatientResponseDto patientResponseDto = patientMapper.patientToPatientResponseDto(savedPatient);

        return Optional.ofNullable(patientResponseDto);
    }

    @Override
    public Optional<PatientResponseDto> updatePatient(PatientRequestDto patientRequestDto, Integer patientId) {
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);

        if (optionalPatient.isPresent()) {
            Patient mappedPatient = patientMapper.patientRequestDtoToPatient(patientRequestDto);

            mappedPatient.setPatientId(patientId);

            if (mappedPatient.getAddress() != null) {
                Address address = mappedPatient.getAddress();

                Optional<Address> optionalAddress = addressRepository.getAddress(
                        address.getStreet(),
                        address.getCity(),
                        address.getState(),
                        address.getZipCode());

                if (optionalAddress.isPresent()) {
                    mappedPatient.getAddress().setAddressId(optionalAddress.get().getAddressId());
                }
                else {
                    Address savedAddress = addressRepository.save(mappedPatient.getAddress());
                    mappedPatient.getAddress().setAddressId(savedAddress.getAddressId());
                }
            }

            Patient savedPatient = patientRepository.save(mappedPatient);

            PatientResponseDto patientResponseDto = patientMapper.patientToPatientResponseDto(savedPatient);

            return Optional.ofNullable(patientResponseDto);
        }

        throw new NotFoundException("Patient with ID " + patientId + " does not exist");
    }

    @Override
    public void deletePatientById(Integer patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);

        if (patient.isPresent()) {
            patientRepository.delete(patient.get());
        }
    }
}
