package edu.miu.cs.cs489.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.miu.cs.cs489.surgeries.controller.PatientController;
import edu.miu.cs.cs489.surgeries.dto.request.AddressRequestDto;
import edu.miu.cs.cs489.surgeries.dto.request.PatientRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.AddressResponseDto;
import edu.miu.cs.cs489.surgeries.dto.response.PatientResponseDto;
import edu.miu.cs.cs489.surgeries.service.PatientService;

@WebMvcTest(PatientController.class)
public class PatientControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PatientService patientService;


    private final AddressRequestDto addressRequestDto = new AddressRequestDto("1000 N", "Fairfield", "Iowa", "52556");
    private final AddressResponseDto addressResponseDto = new AddressResponseDto(1, "1000 N", "Fairfield", "Iowa", "52556");

    private final PatientRequestDto patientRequestDto = new PatientRequestDto(
        "P123",
        "John",
        "Doe",
        "JD@yahoo.com",
        "1234567890",
        LocalDate.of(1990, 1, 1),addressRequestDto);

    private final PatientResponseDto patientResponseDto = new PatientResponseDto(
        1,
        "P123",
        "John",
        "Doe",
        "JD@yahoo.com",
        "1234567890",
        LocalDate.of(1990, 1, 1),addressResponseDto);



    @Test
    @DisplayName("/POST patient should create a new patient and return it")
    void createPatient_ShouldCreatePatient() throws Exception{
        Mockito.when(patientService.addPatient(patientRequestDto)).thenReturn(Optional.of(patientResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patients")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(patientRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(patientResponseDto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patientNo").value("P123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("JD@yahoo.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dob").value("1990-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.street").value("1000 N"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.city").value("Fairfield"));
    }

    @Test
    @DisplayName("/GET patient/{id} should return a patient by id")
    void getPatientById_ShouldReturnPatient() throws Exception {
        Mockito.when(patientService.findPatientById(1)).thenReturn(Optional.of(patientResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patients/1")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(patientResponseDto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patientNo").value("P123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("JD@yahoo.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dob").value("1990-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.street").value("1000 N"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.city").value("Fairfield"));
    }

    @Test
    @DisplayName("/GET patients should return all patients")
    void getAllPatients_ShouldReturnAllPatients() throws Exception {
        Mockito.when(patientService.getAllPatients()).thenReturn(List.of(patientResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patients")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(patientResponseDto))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].patientNo").value("P123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("JD@yahoo.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phone").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dob").value("1990-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].addressResponseDto.street").value("1000 N"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].addressResponseDto.city").value("Fairfield"));
    }

    @Test
    @DisplayName("/PUT patient/{id} should update a patient and return it")
    void updatePatient_ShouldUpdatePatient() throws Exception {
        Mockito.when(patientService.updatePatient(patientRequestDto, 1)).thenReturn(Optional.of(patientResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/patients/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(patientRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(patientResponseDto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patientNo").value("P123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("JD@yahoo.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dob").value("1990-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.street").value("1000 N"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.city").value("Fairfield"));
    }

    @Test
    @DisplayName("/DELETE patient/{id} should delete a patient by id")
    void deletePatientById_ShouldDeletePatient() throws Exception {
        Mockito.doNothing().when(patientService).deletePatientById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/patients/1")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("/GET patients/sorted should return all patients sorted by last name")
    void getAllPatientsSortedByLastName_ShouldReturnSortedPatients() throws Exception {
        Mockito.when(patientService.getAllPatientsSortedByLastName()).thenReturn(List.of(patientResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patients/sorted")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(patientResponseDto))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].patientNo").value("P123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("JD@yahoo.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phone").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dob").value("1990-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].addressResponseDto.street").value("1000 N"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].addressResponseDto.city").value("Fairfield"));
    }

    @Test
    @DisplayName("/GET patients/search/no/{patientNo} should return a patient by patientNo")
    void getPatientByPatientNo_ShouldReturnPatient() throws Exception {
        Mockito.when(patientService.findPatientByPatientNo("P123")).thenReturn(Optional.of(patientResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patients/search/no/P123")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(patientResponseDto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patientNo").value("P123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("JD@yahoo.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dob").value("1990-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.street").value("1000 N"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.city").value("Fairfield"));
    }
}
