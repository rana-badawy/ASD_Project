package edu.miu.cs.cs489.surgeries.controller;

import edu.miu.cs.cs489.surgeries.dto.request.PatientRequestDto;
import edu.miu.cs.cs489.surgeries.service.PatientService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<?> getAllPatients(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge) {

        if (firstName != null && lastName != null) {
            return new ResponseEntity<>(patientService.findAllPatientsWithName(firstName, lastName), HttpStatus.OK);
        } else if (firstName != null) {
            return new ResponseEntity<>(patientService.findAllByFirstName(firstName), HttpStatus.OK);
        } else if (lastName != null) {
            return new ResponseEntity<>(patientService.findAllByLastName(lastName), HttpStatus.OK);
        } else if (minAge != null && maxAge != null) {
            return new ResponseEntity<>(patientService.findPatientsWithAgeRange(minAge, maxAge), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
        }
    }

    @GetMapping("/sorted")
    public ResponseEntity<?> getAllPatientsSortedByLastName() {
        return new ResponseEntity<>(patientService.getAllPatientsSortedByLastName(), HttpStatus.OK);
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<?> getPatientById(@PathVariable Integer patientId) {
        return new ResponseEntity<>(patientService.findPatientById(patientId), HttpStatus.OK);
    }

    @GetMapping("/search/no/{patientNo}")
    public ResponseEntity<?> getAllPatientsWithName(@PathVariable String patientNo) {
        return new ResponseEntity<>(patientService.findPatientByPatientNo(patientNo), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addPatient(@Valid @RequestBody PatientRequestDto patientRequestDto) {
        return new ResponseEntity<>(patientService.addPatient(patientRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<?> updatePatient(@Valid @RequestBody PatientRequestDto patientRequestDto,
                                                            @PathVariable Integer patientId) {
        return new ResponseEntity<>(patientService.updatePatient(patientRequestDto, patientId), HttpStatus.OK);
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<?> deletePatientById(@PathVariable Integer patientId) {
        patientService.deletePatientById(patientId);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
