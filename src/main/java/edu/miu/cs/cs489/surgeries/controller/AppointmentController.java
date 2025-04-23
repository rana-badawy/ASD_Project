package edu.miu.cs.cs489.surgeries.controller;

import edu.miu.cs.cs489.surgeries.dto.request.AppointmentRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.AppointmentResponseDto;
import edu.miu.cs.cs489.surgeries.service.AppointmentService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/dentist/{dentistId}")
    public ResponseEntity<?> getAppointmentsByDentistId(@PathVariable Integer dentistId,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int pageSize,
                                                        @RequestParam(defaultValue = "appointmentId") String sortBy,
                                                        @RequestParam(defaultValue = "asc") String sortDirection) {
        Page<AppointmentResponseDto> appointmentResponseDtos = appointmentService.findByDentistId(dentistId,
                page, pageSize, sortBy, sortDirection);

        return new ResponseEntity<>(appointmentResponseDtos, HttpStatus.OK);
    }

    @GetMapping("/surgery/{surgeryId}")
    public ResponseEntity<?> getAppointmentsBySurgeryId(@PathVariable Integer surgeryId,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int pageSize,
                                                        @RequestParam(defaultValue = "appointmentId") String sortBy,
                                                        @RequestParam(defaultValue = "asc") String sortDirection) {

        Page<AppointmentResponseDto> appointmentResponseDtos = appointmentService.findBySurgeryId(surgeryId,
                page, pageSize, sortBy, sortDirection);

        return new ResponseEntity<>(appointmentResponseDtos, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAppointments(@RequestParam(required = false)
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                             LocalDate date) {
        if (date != null) {
            return new ResponseEntity<>(appointmentService.findByAppointmentDate(date), HttpStatus.OK);
        }
        return new ResponseEntity<>(appointmentService.getAllAppointments(), HttpStatus.OK);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getAppointmentsByPatientId(@PathVariable Integer patientId) {
        return new ResponseEntity<>(appointmentService.findByPatientId(patientId), HttpStatus.OK);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<?> getAppointmentById(@PathVariable Integer appointmentId) {
        return new ResponseEntity<>(appointmentService.findAppointmentById(appointmentId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addAppointment(@Valid @RequestBody AppointmentRequestDto appointmentRequestDto) {
        return new ResponseEntity<>(appointmentService.addAppointment(appointmentRequestDto), HttpStatus.OK);
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<?> updateAppointment(@Valid @RequestBody AppointmentRequestDto appointmentRequestDto,
                                               @PathVariable Integer appointmentId) {
        return new ResponseEntity<>(appointmentService.updateAppointment(appointmentRequestDto, appointmentId), HttpStatus.OK);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Integer appointmentId) {
        appointmentService.deleteAppointmentById(appointmentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
