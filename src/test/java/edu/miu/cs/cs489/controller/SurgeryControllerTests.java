package edu.miu.cs.cs489.controller;

import java.util.List;
import java.util.Optional;

import edu.miu.cs.cs489.surgeries.config.JwtFilter;
import edu.miu.cs.cs489.surgeries.config.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.miu.cs.cs489.surgeries.controller.SurgeryController;
import edu.miu.cs.cs489.surgeries.dto.request.AddressRequestDto;
import edu.miu.cs.cs489.surgeries.dto.request.SurgeryRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.AddressResponseDto;
import edu.miu.cs.cs489.surgeries.dto.response.SurgeryResponseDto;
import edu.miu.cs.cs489.surgeries.service.SurgeryService;

@WebMvcTest(SurgeryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SurgeryControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SurgeryService surgeryService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private JwtFilter jwtFilter;
    
    private final AddressRequestDto addressRequestDto = new AddressRequestDto("1000 N", "Fairfield", "Iowa", "52556");
    private final AddressResponseDto addressResponseDto = new AddressResponseDto(1, "1000 N", "Fairfield", "Iowa", "52556");
    
    private final SurgeryRequestDto surgeryRequestDto = new SurgeryRequestDto("123", "name", "123", addressRequestDto);
    private final SurgeryResponseDto surgeryResponseDto = new SurgeryResponseDto(1, "123", "name", "123", addressResponseDto);


    @Test
    @DisplayName("/POST surgery should create a new surgery and return it")
    void createSurgery_ShouldReturnCreatedSurgery() throws Exception {
        Mockito.when(surgeryService.addSurgery(surgeryRequestDto)).thenReturn(Optional.of(surgeryResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/surgeries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(surgeryRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(surgeryResponseDto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surgeryId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surgeryNo").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.street").value("1000 N"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.city").value("Fairfield"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.state").value("Iowa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.zipCode").value("52556"));
    }

    @Test
    @DisplayName("/PUT surgery should update an existing surgery and return it")
    void updateSurgery_ShouldReturnUpdatedSurgery() throws Exception {
        Mockito.when(surgeryService.updateSurgery(surgeryRequestDto, 1)).thenReturn(Optional.of(surgeryResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/surgeries/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(surgeryRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(surgeryResponseDto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surgeryId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surgeryNo").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.street").value("1000 N"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.city").value("Fairfield"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.state").value("Iowa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.zipCode").value("52556"));

    }

    @Test
    @DisplayName("/DELETE surgery should delete an existing surgery")
    void deleteSurgery_ShouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(surgeryService).deleteSurgeryById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/surgeries/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    @DisplayName("/GET surgery should return an existing surgery")
    void getSurgeryById_ShouldReturnSurgery() throws Exception {
        Mockito.when(surgeryService.findSurgeryById(1)).thenReturn(Optional.of(surgeryResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/surgeries/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(surgeryResponseDto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surgeryId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surgeryNo").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.street").value("1000 N"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.city").value("Fairfield"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.state").value("Iowa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.zipCode").value("52556"));

    }

    @Test
    @DisplayName("/GET surgery should return a list of surgeries")
    void getAllSurgeries_ShouldReturnListOfSurgeries() throws Exception {
        Mockito.when(surgeryService.getAllSurgeries()).thenReturn(List.of(surgeryResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/surgeries"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].surgeryId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].surgeryNo").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phone").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].addressResponseDto.street").value("1000 N"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].addressResponseDto.city").value("Fairfield"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].addressResponseDto.state").value("Iowa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].addressResponseDto.zipCode").value("52556"));
    }

    @Test
    @DisplayName("/GET surgery by surgeryNo should return a surgery")
    void getSurgeryBySurgeryNo_ShouldReturnSurgery() throws Exception {
        Mockito.when(surgeryService.findBySurgeryNo("123")).thenReturn(Optional.of(surgeryResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/surgeries/search/no/123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(surgeryResponseDto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surgeryId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surgeryNo").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.street").value("1000 N"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.city").value("Fairfield"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.state").value("Iowa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.zipCode").value("52556"));
    }

    @Test
    @DisplayName("/GET surgery by name should return a surgery")
    void getSurgeryByName_ShouldReturnSurgery() throws Exception {
        Mockito.when(surgeryService.findSurgeryByName("name")).thenReturn(Optional.of(surgeryResponseDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/surgeries/search/name/name"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(surgeryResponseDto)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surgeryId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surgeryNo").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.street").value("1000 N"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.city").value("Fairfield"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.state").value("Iowa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressResponseDto.zipCode").value("52556"));
    }
}
