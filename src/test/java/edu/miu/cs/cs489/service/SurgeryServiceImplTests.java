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
import edu.miu.cs.cs489.surgeries.dto.request.SurgeryRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.AddressResponseDto;
import edu.miu.cs.cs489.surgeries.dto.response.SurgeryResponseDto;
import edu.miu.cs.cs489.surgeries.exception.DuplicateException;
import edu.miu.cs.cs489.surgeries.exception.NotFoundException;
import edu.miu.cs.cs489.surgeries.mapper.SurgeryMapper;
import edu.miu.cs.cs489.surgeries.model.Address;
import edu.miu.cs.cs489.surgeries.model.Surgery;
import edu.miu.cs.cs489.surgeries.repository.AddressRepository;
import edu.miu.cs.cs489.surgeries.repository.SurgeryRepository;
import edu.miu.cs.cs489.surgeries.service.impl.SurgeryServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SurgeryServiceImplTests {
    @Mock
    private SurgeryRepository surgeryRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private SurgeryMapper surgeryMapper;

    @InjectMocks
    private SurgeryServiceImpl surgeryService;


    private final AddressRequestDto addressRequestDto = new AddressRequestDto("1000 N", "Fairfield", "Iowa", "52556");
    private final AddressResponseDto addressResponseDto = new AddressResponseDto(1, "1000 N", "Fairfield", "Iowa", "52556");
    
    private final Address address = new Address("1000 N", "Fairfield", "Iowa", "52556");
    
    private final SurgeryRequestDto surgeryRequestDto = new SurgeryRequestDto("123", "name", "123", addressRequestDto);
    private final SurgeryResponseDto surgeryResponseDto = new SurgeryResponseDto(1, "123", "name", "123", addressResponseDto);



    @Test
    @DisplayName("Test getAllSurgeries()")
    public void testGetAllSurgeries() {
        List<Surgery> surgeries = List.of(new Surgery("123", "name", "123", address));
        List<SurgeryResponseDto> mappedList = List.of(surgeryResponseDto);

        Mockito.when(surgeryRepository.findAll()).thenReturn(surgeries);
        Mockito.when(surgeryMapper.surgeriesToSurgeryResponseDtoList(surgeries)).thenReturn(mappedList);

        List<SurgeryResponseDto> result = surgeryService.getAllSurgeries();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(1);

        Assertions.assertThat(result.get(0).surgeryNo()).isEqualTo("123");
        Assertions.assertThat(result.get(0).name()).isEqualTo("name");
        Assertions.assertThat(result.get(0).phone()).isEqualTo("123");
        Assertions.assertThat(result.get(0).addressResponseDto()).isNotNull();
        Assertions.assertThat(result.get(0).addressResponseDto().street()).isEqualTo("1000 N");
    }

    @Test
    @DisplayName("Test findByCity()")
    public void testFindByCity() {
        List<Surgery> surgeries = List.of(new Surgery("123", "name", "123", address));
        List<SurgeryResponseDto> mappedList = List.of(surgeryResponseDto);

        Mockito.when(surgeryRepository.findByCity("Fairfield", "Iowa")).thenReturn(surgeries);
        Mockito.when(surgeryMapper.surgeriesToSurgeryResponseDtoList(surgeries)).thenReturn(mappedList);

        List<SurgeryResponseDto> result = surgeryService.findByCity("Fairfield", "Iowa");

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(1);

        Assertions.assertThat(result.get(0).surgeryNo()).isEqualTo("123");
        Assertions.assertThat(result.get(0).name()).isEqualTo("name");
        Assertions.assertThat(result.get(0).phone()).isEqualTo("123");
        Assertions.assertThat(result.get(0).addressResponseDto()).isNotNull();
        Assertions.assertThat(result.get(0).addressResponseDto().street()).isEqualTo("1000 N");
        Assertions.assertThat(result.get(0).addressResponseDto().city()).isEqualTo("Fairfield");
        Assertions.assertThat(result.get(0).addressResponseDto().state()).isEqualTo("Iowa");
    }

    @Test
    @DisplayName("Test findSurgeryById()")
    public void testFindSurgeryById() {
        Surgery surgery = new Surgery("123", "name", "123", address);
        Optional<Surgery> optionalSurgery = Optional.of(surgery);

        Mockito.when(surgeryRepository.findById(1)).thenReturn(optionalSurgery);
        Mockito.when(surgeryMapper.surgeryToSurgeryResponseDto(surgery)).thenReturn(surgeryResponseDto);

        Optional<SurgeryResponseDto> result = surgeryService.findSurgeryById(1);

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().surgeryNo()).isEqualTo("123");
        Assertions.assertThat(result.get().name()).isEqualTo("name");
        Assertions.assertThat(result.get().phone()).isEqualTo("123");
        Assertions.assertThat(result.get().addressResponseDto()).isNotNull();
        Assertions.assertThat(result.get().addressResponseDto().street()).isEqualTo("1000 N");
        Assertions.assertThat(result.get().addressResponseDto().city()).isEqualTo("Fairfield");
        Assertions.assertThat(result.get().addressResponseDto().state()).isEqualTo("Iowa");
    }

    @Test
    @DisplayName("Test findSurgeryById() - Not Found")
    public void testFindSurgeryByIdNotFound() {
        Mockito.when(surgeryRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            surgeryService.findSurgeryById(1);
        });

        Assertions.assertThat(exception.getMessage()).isEqualTo("Surgery with id 1 not found");
    }

    @Test
    @DisplayName("Test findBySurgeryNo()")
    public void testFindBySurgeryNo() {
        Surgery surgery = new Surgery("123", "name", "123", address);
        Optional<Surgery> optionalSurgery = Optional.of(surgery);

        Mockito.when(surgeryRepository.findBySurgeryNo("123")).thenReturn(optionalSurgery);
        Mockito.when(surgeryMapper.surgeryToSurgeryResponseDto(surgery)).thenReturn(surgeryResponseDto);

        Optional<SurgeryResponseDto> result = surgeryService.findBySurgeryNo("123");

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().surgeryNo()).isEqualTo("123");
        Assertions.assertThat(result.get().name()).isEqualTo("name");
        Assertions.assertThat(result.get().phone()).isEqualTo("123");
        Assertions.assertThat(result.get().addressResponseDto()).isNotNull();
        Assertions.assertThat(result.get().addressResponseDto().street()).isEqualTo("1000 N");
        Assertions.assertThat(result.get().addressResponseDto().city()).isEqualTo("Fairfield");
        Assertions.assertThat(result.get().addressResponseDto().state()).isEqualTo("Iowa");
    }

    @Test
    @DisplayName("Test findBySurgeryNo() - Not Found")
    public void testFindBySurgeryNoNotFound() {
        Mockito.when(surgeryRepository.findBySurgeryNo("123")).thenReturn(Optional.empty());

        Optional<SurgeryResponseDto> result = surgeryService.findBySurgeryNo("123");

        Assertions.assertThat(result).isNotPresent();
    }
    @Test
    @DisplayName("Test findSurgeryByName()")
    public void testFindSurgeryByName() {
        Surgery surgery = new Surgery("123", "name", "123", address);
        Optional<Surgery> optionalSurgery = Optional.of(surgery);

        Mockito.when(surgeryRepository.findByName("name")).thenReturn(optionalSurgery);
        Mockito.when(surgeryMapper.surgeryToSurgeryResponseDto(surgery)).thenReturn(surgeryResponseDto);

        Optional<SurgeryResponseDto> result = surgeryService.findSurgeryByName("name");

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().surgeryNo()).isEqualTo("123");
        Assertions.assertThat(result.get().name()).isEqualTo("name");
        Assertions.assertThat(result.get().phone()).isEqualTo("123");
        Assertions.assertThat(result.get().addressResponseDto()).isNotNull();
        Assertions.assertThat(result.get().addressResponseDto().street()).isEqualTo("1000 N");
        Assertions.assertThat(result.get().addressResponseDto().city()).isEqualTo("Fairfield");
        Assertions.assertThat(result.get().addressResponseDto().state()).isEqualTo("Iowa");
    }
    @Test
    @DisplayName("Test findSurgeryByName() - Not Found")
    public void testFindSurgeryByNameNotFound() {
        Mockito.when(surgeryRepository.findByName("name")).thenReturn(Optional.empty());

        Optional<SurgeryResponseDto> result = surgeryService.findSurgeryByName("name");

        Assertions.assertThat(result).isNotPresent();
    }
    @Test
    @DisplayName("Test addSurgery()")
    public void testAddSurgery() {
        Surgery surgery = new Surgery("123", "name", "123", address);
        Optional<Surgery> optionalSurgery = Optional.empty();

        Mockito.when(surgeryRepository.findBySurgeryNo("123")).thenReturn(optionalSurgery);
        Mockito.when(surgeryMapper.surgeryRequestDtoToSurgery(surgeryRequestDto)).thenReturn(surgery);
        Mockito.when(surgeryRepository.save(surgery)).thenReturn(surgery);
        Mockito.when(surgeryMapper.surgeryToSurgeryResponseDto(surgery)).thenReturn(surgeryResponseDto);
        Mockito.when(addressRepository.getAddress("1000 N", "Fairfield", "Iowa", "52556")).thenReturn(Optional.of(address));

        Optional<SurgeryResponseDto> result = surgeryService.addSurgery(surgeryRequestDto);

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().surgeryNo()).isEqualTo("123");
        Assertions.assertThat(result.get().name()).isEqualTo("name");
        Assertions.assertThat(result.get().phone()).isEqualTo("123");
        Assertions.assertThat(result.get().addressResponseDto()).isNotNull();
        Assertions.assertThat(result.get().addressResponseDto().street()).isEqualTo("1000 N");
        Assertions.assertThat(result.get().addressResponseDto().city()).isEqualTo("Fairfield");
        Assertions.assertThat(result.get().addressResponseDto().state()).isEqualTo("Iowa");
    }
    @Test
    @DisplayName("Test addSurgery() - Duplicate Surgery")
    public void testAddSurgeryDuplicate() {
        Surgery surgery = new Surgery("123", "name", "123", address);
        Optional<Surgery> optionalSurgery = Optional.of(surgery);

        Mockito.when(surgeryRepository.findBySurgeryNo("123")).thenReturn(optionalSurgery);

        DuplicateException exception = assertThrows(DuplicateException.class, () -> {
            surgeryService.addSurgery(surgeryRequestDto);
        });

        Assertions.assertThat(exception.getMessage()).isEqualTo("Surgery with number 123 already exists");
    }
    @Test
    @DisplayName("Test updateSurgery()")
    public void testUpdateSurgery() {
        Surgery surgery = new Surgery("123", "name", "123", address);
        Optional<Surgery> optionalSurgery = Optional.of(surgery);

        Mockito.when(surgeryRepository.findById(1)).thenReturn(optionalSurgery);
        Mockito.when(surgeryMapper.surgeryRequestDtoToSurgery(surgeryRequestDto)).thenReturn(surgery);
        Mockito.when(surgeryRepository.save(surgery)).thenReturn(surgery);
        Mockito.when(surgeryMapper.surgeryToSurgeryResponseDto(surgery)).thenReturn(surgeryResponseDto);
        Mockito.when(addressRepository.getAddress("1000 N", "Fairfield", "Iowa", "52556")).thenReturn(Optional.of(address));

        Optional<SurgeryResponseDto> result = surgeryService.updateSurgery(surgeryRequestDto, 1);

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().surgeryNo()).isEqualTo("123");
        Assertions.assertThat(result.get().name()).isEqualTo("name");
        Assertions.assertThat(result.get().phone()).isEqualTo("123");
        Assertions.assertThat(result.get().addressResponseDto()).isNotNull();
        Assertions.assertThat(result.get().addressResponseDto().street()).isEqualTo("1000 N");
        Assertions.assertThat(result.get().addressResponseDto().city()).isEqualTo("Fairfield");
        Assertions.assertThat(result.get().addressResponseDto().state()).isEqualTo("Iowa");
    }
    @Test
    @DisplayName("Test updateSurgery() - Not Found")
    public void testUpdateSurgeryNotFound() {
        Mockito.when(surgeryRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            surgeryService.updateSurgery(surgeryRequestDto, 1);
        });

        Assertions.assertThat(exception.getMessage()).isEqualTo("Surgery with id 1 not found");
    }
    @Test
    @DisplayName("Test deleteSurgeryById()")
    public void testDeleteSurgeryById() {
        Surgery surgery = new Surgery("123", "name", "123", address);
        Optional<Surgery> optionalSurgery = Optional.of(surgery);

        Mockito.when(surgeryRepository.findById(1)).thenReturn(optionalSurgery);

        surgeryService.deleteSurgeryById(1);

        Mockito.verify(surgeryRepository, Mockito.times(1)).deleteById(1);
    }
    @Test
    @DisplayName("Test deleteSurgeryById() - Not Found")
    public void testDeleteSurgeryByIdNotFound() {
        Mockito.when(surgeryRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            surgeryService.deleteSurgeryById(1);
        });

        Assertions.assertThat(exception.getMessage()).isEqualTo("Surgery with id 1 not found");
    }
    @Test
    @DisplayName("Test deleteSurgeryById() - EntityNotFoundException")
    public void testDeleteSurgeryByIdEntityNotFoundException() {
        Mockito.when(surgeryRepository.findById(1)).thenThrow(new EntityNotFoundException("Surgery with id 1 not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            surgeryService.deleteSurgeryById(1);
        });

        Assertions.assertThat(exception.getMessage()).isEqualTo("Surgery with id 1 not found");
    }
}
