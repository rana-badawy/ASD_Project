package edu.miu.cs.cs489.integration;

import edu.miu.cs.cs489.surgeries.dto.request.AddressRequestDto;
import edu.miu.cs.cs489.surgeries.dto.request.PatientRequestDto;
import edu.miu.cs.cs489.surgeries.model.Role;
import edu.miu.cs.cs489.surgeries.model.User;
import edu.miu.cs.cs489.surgeries.repository.PatientRepository;
import edu.miu.cs.cs489.surgeries.repository.UserRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PatientControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AddressRequestDto addressRequestDto = new AddressRequestDto("1000 N", "Fairfield", "Iowa", "52556");
    private final PatientRequestDto patientRequestDto = new PatientRequestDto(
            "P123", "John", "Doe", "JD@yahoo.com", "1234567890", LocalDate.of(1990, 1, 1), addressRequestDto);

    @BeforeEach
    void setup() {
        User adminUser = new User("Admin", "User", "admin", passwordEncoder.encode("admin"), Role.ADMIN);
        userRepository.save(adminUser);
    }

    @AfterEach
    void tearDown() {
        patientRepository.deleteAll();
        userRepository.deleteAll();
    }

    private String obtainJwtToken(String username, String password) throws Exception {
        String loginRequest = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
        MvcResult result = mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isOk())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        return objectMapper.readTree(responseJson).get("token").asText();
    }

    @Test
    @DisplayName("POST /api/v1/patients - Create Patient (Authenticated)")
    void createPatient_ShouldCreatePatient_WhenAuthenticated() throws Exception {
        String token = obtainJwtToken("admin", "admin");

        mockMvc.perform(post("/api/v1/patients")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patientNo").value("P123"))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    @DisplayName("GET /api/v1/patients - Get All Patients (Authenticated)")
    void getAllPatients_ShouldReturnPatients_WhenAuthenticated() throws Exception {
        String token = obtainJwtToken("admin", "admin");

        mockMvc.perform(post("/api/v1/patients")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequestDto)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/patients")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].patientNo").value("P123"));
    }

    @Test
    @DisplayName("GET /api/v1/patients/{patientId} - Get Patient by ID (Authenticated)")
    void getPatientById_ShouldReturnPatient_WhenAuthenticated() throws Exception {
        String token = obtainJwtToken("admin", "admin");

        MvcResult result = mockMvc.perform(post("/api/v1/patients")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        int patientId = objectMapper.readTree(result.getResponse().getContentAsString()).get("patientId").asInt();

        mockMvc.perform(get("/api/v1/patients/" + patientId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientNo").value("P123"))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    @DisplayName("PUT /api/v1/patients/{id} - Update Patient (Authenticated)")
    void updatePatient_ShouldUpdatePatient_WhenAuthenticated() throws Exception {
        String token = obtainJwtToken("admin", "admin");

        MvcResult result = mockMvc.perform(post("/api/v1/patients")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        int patientId = objectMapper.readTree(result.getResponse().getContentAsString()).get("patientId").asInt();

        PatientRequestDto updatedPatientDto = new PatientRequestDto(
                "P123", "Updated", "Name", "updated@yahoo.com", "1112223333",
                LocalDate.of(1985, 5, 15), addressRequestDto);

        mockMvc.perform(put("/api/v1/patients/" + patientId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPatientDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.email").value("updated@yahoo.com"));
    }

    @Test
    @DisplayName("DELETE /api/v1/patients/{id} - Delete Patient (Authenticated)")
    void deletePatient_ShouldDeletePatient_WhenAuthenticated() throws Exception {
        String token = obtainJwtToken("admin", "admin");

        MvcResult result = mockMvc.perform(post("/api/v1/patients")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        int patientId = objectMapper.readTree(result.getResponse().getContentAsString()).get("patientId").asInt();

        mockMvc.perform(delete("/api/v1/patients/" + patientId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/patients/" + patientId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNotFound());
    }
}