package edu.miu.cs.cs489.surgeries.controller;

import edu.miu.cs.cs489.surgeries.dto.request.SurgeryRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.SurgeryResponseDto;
import edu.miu.cs.cs489.surgeries.service.SurgeryService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/surgeries")
public class SurgeryController {
    @Autowired
    private SurgeryService surgeryService;

    @GetMapping
    public ResponseEntity<List<SurgeryResponseDto>> getSurgeries(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state) {

        if (city != null && state != null) {
            return new ResponseEntity<>(surgeryService.findByCity(city, state), HttpStatus.OK);
        }

        return new ResponseEntity<>(surgeryService.getAllSurgeries(), HttpStatus.OK);
    }

    @GetMapping("/{surgeryId}")
    public ResponseEntity<?> findSurgeryById(@PathVariable Integer surgeryId) {
        return new ResponseEntity<>(surgeryService.findSurgeryById(surgeryId),HttpStatus.OK);
    }

    @GetMapping("/search/no/{surgeryNo}")
    public ResponseEntity<?> findBySurgeryNo(@PathVariable String surgeryNo) {
        return new ResponseEntity<>(surgeryService.findBySurgeryNo(surgeryNo),HttpStatus.OK);
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<?> findSurgeryByName(@PathVariable String name) {
        return new ResponseEntity<>(surgeryService.findSurgeryByName(name),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addSurgery(@Valid @RequestBody SurgeryRequestDto surgeryRequestDto) {
        return new ResponseEntity<>(surgeryService.addSurgery(surgeryRequestDto),HttpStatus.OK);
    }

    @PutMapping("/{surgeryId}")
    public ResponseEntity<?> updateSurgery(@Valid @RequestBody SurgeryRequestDto surgeryRequestDto,
                                           @PathVariable Integer surgeryId) {
        return new ResponseEntity<>(surgeryService.updateSurgery(surgeryRequestDto, surgeryId),HttpStatus.OK);
    }

    @DeleteMapping("/{surgeryId}")
    public ResponseEntity<?> deleteSurgeryById(@PathVariable Integer surgeryId) {
        surgeryService.deleteSurgeryById(surgeryId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
