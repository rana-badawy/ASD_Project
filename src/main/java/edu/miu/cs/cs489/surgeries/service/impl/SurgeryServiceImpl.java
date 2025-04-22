package edu.miu.cs.cs489.surgeries.service.impl;

import edu.miu.cs.cs489.surgeries.dto.request.SurgeryRequestDto;
import edu.miu.cs.cs489.surgeries.dto.response.SurgeryResponseDto;
import edu.miu.cs.cs489.surgeries.exception.DuplicateException;
import edu.miu.cs.cs489.surgeries.exception.NotFoundException;
import edu.miu.cs.cs489.surgeries.mapper.SurgeryMapper;
import edu.miu.cs.cs489.surgeries.model.Address;
import edu.miu.cs.cs489.surgeries.model.Surgery;
import edu.miu.cs.cs489.surgeries.repository.AddressRepository;
import edu.miu.cs.cs489.surgeries.repository.SurgeryRepository;
import edu.miu.cs.cs489.surgeries.service.SurgeryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurgeryServiceImpl implements SurgeryService {
    @Autowired
    private SurgeryRepository surgeryRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SurgeryMapper surgeryMapper;

    @Override
    public List<SurgeryResponseDto> getAllSurgeries() {
        return surgeryMapper.surgeriesToSurgeryResponseDtoList(surgeryRepository.findAll());
    }

    @Override
    public List<SurgeryResponseDto> findByCity(String city, String state) {
        return surgeryMapper.surgeriesToSurgeryResponseDtoList(surgeryRepository.findByCity(city, state));
    }

    @Override
    public Optional<SurgeryResponseDto> findSurgeryById(Integer surgeryId) {
        Optional<Surgery> surgery = surgeryRepository.findById(surgeryId);

        if (surgery.isPresent()) {
            return Optional.ofNullable(surgeryMapper.surgeryToSurgeryResponseDto(surgery.get()));
        }
        throw new NotFoundException("Surgery with id " + surgeryId + " not found");
    }

    @Override
    public Optional<SurgeryResponseDto> findBySurgeryNo(String surgeryNo) {
        Optional<Surgery> surgery = surgeryRepository.findBySurgeryNo(surgeryNo);

        if (surgery.isPresent()) {
            return Optional.ofNullable(surgeryMapper.surgeryToSurgeryResponseDto(surgery.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<SurgeryResponseDto> findSurgeryByName(String name) {
        Optional<Surgery> surgery = surgeryRepository.findByName(name);

        if (surgery.isPresent()) {
            return Optional.ofNullable(surgeryMapper.surgeryToSurgeryResponseDto(surgery.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<SurgeryResponseDto> addSurgery(SurgeryRequestDto surgeryRequestDto) {
        Optional<Surgery> surgeryOptional = surgeryRepository.findBySurgeryNo(surgeryRequestDto.surgeryNo());

        if (surgeryOptional.isPresent()) {
            throw new DuplicateException("Surgery with number " + surgeryRequestDto.surgeryNo() + " already exists");
        }

        Surgery surgery = surgeryMapper.surgeryRequestDtoToSurgery(surgeryRequestDto);

        if (surgery.getAddress() != null) {
            Address address = surgery.getAddress();
            Optional<Address> optionalAddress = addressRepository.getAddress(
                    address.getStreet(),
                    address.getCity(),
                    address.getState(),
                    address.getZipCode());

            if (optionalAddress.isPresent()) {
                surgery.getAddress().setAddressId(optionalAddress.get().getAddressId());
            } else {
                Address savedAddress = addressRepository.save(surgery.getAddress());
                surgery.getAddress().setAddressId(savedAddress.getAddressId());
            }
        }

        Surgery savedSurgery = surgeryRepository.save(surgery);

        SurgeryResponseDto surgeryResponseDto = surgeryMapper.surgeryToSurgeryResponseDto(savedSurgery);

        return Optional.ofNullable(surgeryResponseDto);
    }

    @Override
    public Optional<SurgeryResponseDto> updateSurgery(SurgeryRequestDto surgeryRequestDto, Integer surgeryId) {
        Optional<Surgery> surgeryOptional = surgeryRepository.findById(surgeryId);

        if (!surgeryOptional.isPresent()) {
            throw new NotFoundException("Surgery with id " + surgeryId + " not found");
        }

        Surgery mappedSurgery = surgeryMapper.surgeryRequestDtoToSurgery(surgeryRequestDto);

        mappedSurgery.setSurgeryId(surgeryId);

        if (mappedSurgery.getAddress() != null) {
            Address address = mappedSurgery.getAddress();

            Optional<Address> optionalAddress = addressRepository.getAddress(
                    address.getStreet(),
                    address.getCity(),
                    address.getState(),
                    address.getZipCode());

            if (optionalAddress.isPresent()) {
                mappedSurgery.getAddress().setAddressId(optionalAddress.get().getAddressId());
            }
            else {
                Address savedAddress = addressRepository.save(mappedSurgery.getAddress());
                mappedSurgery.getAddress().setAddressId(savedAddress.getAddressId());
            }
        }

        Surgery savedSurgery = surgeryRepository.save(mappedSurgery);

        SurgeryResponseDto surgeryResponseDto = surgeryMapper.surgeryToSurgeryResponseDto(savedSurgery);

        return Optional.ofNullable(surgeryResponseDto);
    }

    @Override
    public void deleteSurgeryById(Integer surgeryId) {
        Optional<Surgery> surgery = surgeryRepository.findById(surgeryId);

        if (surgery.isPresent())
            surgeryRepository.deleteById(surgeryId);
        else
            throw new NotFoundException("Surgery with id " + surgeryId + " not found");
    }
}
