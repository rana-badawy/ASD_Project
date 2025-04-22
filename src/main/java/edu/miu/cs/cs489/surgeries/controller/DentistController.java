package edu.miu.cs.cs489.surgeries.controller;

import edu.miu.cs.cs489.surgeries.dto.request.DentistRequestDto;
import edu.miu.cs.cs489.surgeries.service.DentistService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dentists")
public class DentistController {
    @Autowired
    private DentistService dentistService;

    @GetMapping
    public ResponseEntity<?> getDentists(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String specialization) {

        if (firstName != null && lastName != null) {
            return new ResponseEntity<>(dentistService.getAllDentistsWithName(firstName, lastName), HttpStatus.OK);
        } else if (firstName != null) {
            return new ResponseEntity<>(dentistService.findAllByFirstName(firstName), HttpStatus.OK);
        } else if (lastName != null) {
            return new ResponseEntity<>(dentistService.findAllByLastName(lastName), HttpStatus.OK);
        } else if (specialization != null) {
            return new ResponseEntity<>(dentistService.findAllBySpecialization(specialization), HttpStatus.OK);
        }

        return new ResponseEntity<>(dentistService.getAllDentists(), HttpStatus.OK);
    }

    @GetMapping("/{dentistId}")
    public ResponseEntity<?> getDentistById(@PathVariable Integer dentistId) {
        return new ResponseEntity<>(dentistService.getDentistById(dentistId), HttpStatus.OK);
    }

    @GetMapping("/search/no/{dentistNo}")
    public ResponseEntity<?> findDentistByDentistNo(@PathVariable String dentistNo) {
        return new ResponseEntity<>(dentistService.findDentistByDentistNo(dentistNo), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addDentist(@Valid @RequestBody DentistRequestDto dentistRequestDto) {
        return new ResponseEntity<>(dentistService.addDentist(dentistRequestDto), HttpStatus.OK);
    }

    @PutMapping("/{dentistId}")
    public ResponseEntity<?> updateDentist(@Valid @RequestBody DentistRequestDto dentistRequestDto,
                                           @PathVariable Integer dentistId) {
        return new ResponseEntity<>(dentistService.updateDentist(dentistRequestDto, dentistId), HttpStatus.OK);
    }

    @DeleteMapping("/{dentistId}")
    public ResponseEntity<?> deleteDentistById(@PathVariable Integer dentistId) {
        dentistService.deleteDentistById(dentistId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
