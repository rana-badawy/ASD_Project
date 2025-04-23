package edu.miu.cs.cs489.surgeries.service.impl;

import edu.miu.cs.cs489.surgeries.dto.request.AppointmentRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.AppointmentResponseDto;
import edu.miu.cs.cs489.surgeries.exception.NotFoundException;
import edu.miu.cs.cs489.surgeries.mapper.AppointmentMapper;
import edu.miu.cs.cs489.surgeries.model.Appointment;
import edu.miu.cs.cs489.surgeries.model.Dentist;
import edu.miu.cs.cs489.surgeries.model.Patient;
import edu.miu.cs.cs489.surgeries.model.Surgery;
import edu.miu.cs.cs489.surgeries.repository.AppointmentRepository;
import edu.miu.cs.cs489.surgeries.repository.DentistRepository;
import edu.miu.cs.cs489.surgeries.repository.PatientRepository;
import edu.miu.cs.cs489.surgeries.repository.SurgeryRepository;
import edu.miu.cs.cs489.surgeries.service.AppointmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DentistRepository dentistRepository;

    @Autowired
    private SurgeryRepository surgeryRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public Page<AppointmentResponseDto> findByDentistId(Integer dentistId, int page, int pageSize, String sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(sortDirection), sortBy);

        Optional<Dentist> dentist = dentistRepository.findById(dentistId);

        if (dentist.isPresent()) {
            Page<Appointment> appointmentPage = appointmentRepository.findByDentist(dentist.get(), pageable);

            return appointmentPage.map(appointmentMapper::appointmentToAppointmentResponseDto);
        }
        throw new NotFoundException("Dentist with id " + dentistId + " not found");
    }

    @Override
    public Page<AppointmentResponseDto> findBySurgeryId(Integer surgeryId, int page, int pageSize, String sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(sortDirection), sortBy);

        Optional<Surgery> surgery = surgeryRepository.findById(surgeryId);

        if (surgery.isPresent()) {
            Page<Appointment> appointmentPage = appointmentRepository.findBySurgery(surgery.get(), pageable);

            return appointmentPage.map(appointmentMapper::appointmentToAppointmentResponseDto);
        }
        throw new NotFoundException("Surgery with id " + surgeryId + " not found");
    }

    @Override
    public List<AppointmentResponseDto> getAllAppointments() {
        return appointmentMapper.appointmentsToAppointmentResponseDtoList(appointmentRepository.findAll());
    }

    @Override
    public List<AppointmentResponseDto> findByPatientId(Integer patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);

        if (patient.isPresent()) {
            return appointmentMapper.appointmentsToAppointmentResponseDtoList(
                    appointmentRepository.findByPatient(patient.get()));
        }
        throw new NotFoundException("Patient with id " + patientId + " not found");
    }

    @Override
    public List<AppointmentResponseDto> findByAppointmentDate(LocalDate date) {
        return appointmentMapper.appointmentsToAppointmentResponseDtoList(appointmentRepository.findByAppointmentDate(date));
    }

    @Override
    public Optional<AppointmentResponseDto> findAppointmentById(Integer appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);

        if (appointment.isPresent()) {
            return Optional.ofNullable(appointmentMapper.appointmentToAppointmentResponseDto(appointment.get()));
        }

        throw new NotFoundException("Appointment with id " + appointmentId + " not found");
    }

    @Override
    public Optional<AppointmentResponseDto> addAppointment(AppointmentRequestDto appointmentRequestDto) {
        Appointment appointment = appointmentMapper.appointmentRequestDtoToAppointment(appointmentRequestDto);

        Optional<Patient> patient = patientRepository.findById(appointmentRequestDto.patientId());

        if (patient.isPresent()) {
            appointment.setPatient(patient.get());
        }
        else {
            throw new NotFoundException("Patient with ID " + appointmentRequestDto.patientId() + " not found");
        }

        Optional<Surgery> surgery = surgeryRepository.findById(appointmentRequestDto.surgeryId());

        if (surgery.isPresent()) {
            appointment.setSurgery(surgery.get());
        }
        else {
            throw new NotFoundException("Surgery with ID " + appointmentRequestDto.surgeryId() + " not found");
        }

        Optional<Dentist> dentist = dentistRepository.findById(appointmentRequestDto.dentistId());

        if (dentist.isPresent()) {
            appointment.setDentist(dentist.get());
        }
        else {
            throw new NotFoundException("Dentist with ID " + appointmentRequestDto.dentistId() + " not found");
        }

        Appointment savedAppointment = appointmentRepository.save(appointment);

        AppointmentResponseDto appointmentResponseDto = appointmentMapper.appointmentToAppointmentResponseDto(savedAppointment);

        return Optional.of(appointmentResponseDto);
    }

    @Override
    public Optional<AppointmentResponseDto> updateAppointment(AppointmentRequestDto appointmentRequestDto, Integer appointmentId) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentId);

        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentMapper.appointmentRequestDtoToAppointment(appointmentRequestDto);

            Optional<Patient> patient = patientRepository.findById(appointmentRequestDto.patientId());

            if (patient.isPresent()) {
                if (patient.get().getPatientId().equals(appointmentOptional.get().getPatient().getPatientId())) {
                    appointment.setPatient(patient.get());
                }
                else {
                    throw new NotFoundException("Can not change patient. Delete appointment and create a new one.");
                }
            }
            else {
                throw new NotFoundException("Patient with ID " + appointmentRequestDto.patientId() + " not found");
            }

            Optional<Surgery> surgery = surgeryRepository.findById(appointmentRequestDto.surgeryId());

            if (surgery.isPresent()) {
                appointment.setSurgery(surgery.get());
            }
            else {
                throw new NotFoundException("Surgery with ID " + appointmentRequestDto.surgeryId() + " not found");
            }

            Optional<Dentist> dentist = dentistRepository.findById(appointmentRequestDto.dentistId());

            if (dentist.isPresent()) {
                appointment.setDentist(dentist.get());
            }
            else {
                throw new NotFoundException("Dentist with ID " + appointmentRequestDto.dentistId() + " not found");
            }

            appointment.setAppointmentId(appointmentId);

            Appointment savedAppointment = appointmentRepository.save(appointment);

            AppointmentResponseDto appointmentResponseDto = appointmentMapper.appointmentToAppointmentResponseDto(savedAppointment);

            return Optional.of(appointmentResponseDto);
        }
        throw new NotFoundException("Appointment with id " + appointmentId + " not found");
    }

    @Override
    public void deleteAppointmentById(Integer appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);

        if (appointment.isPresent()) {
            appointmentRepository.delete(appointment.get());
        }
        else {
            throw new NotFoundException("Appointment with id " + appointmentId + " not found");
        }
    }
}
