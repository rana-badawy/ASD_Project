package edu.miu.cs.cs489.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import edu.miu.cs.cs489.surgeries.model.Address;
import edu.miu.cs.cs489.surgeries.model.Surgery;
import edu.miu.cs.cs489.surgeries.repository.AddressRepository;
import edu.miu.cs.cs489.surgeries.repository.SurgeryRepository;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class SurgeryRepositoryTests {
    private Address address = new Address("1000 N", "Fairfield", "IA", "52557");
    private Surgery surgery = new Surgery("S123", "Surgery 1", "1234567890", address);

    @Autowired
    private SurgeryRepository surgeryRepository;

    @Autowired
    private AddressRepository addressRepository;


    @Test
    @DisplayName("Test findBySurgeryNo")
    public void testFindBySurgeryNo() {
        addressRepository.save(address);
        surgeryRepository.saveAndFlush(surgery);

        Optional<Surgery> foundSurgery = surgeryRepository.findBySurgeryNo(surgery.getSurgeryNo());

        Assertions.assertThat(foundSurgery).isPresent();
        Assertions.assertThat(foundSurgery.get().getSurgeryNo()).isEqualTo(surgery.getSurgeryNo());
    }

    @Test
    @DisplayName("Test findByName")
    public void testFindByName() {
        addressRepository.save(address);
        surgeryRepository.saveAndFlush(surgery);

        Optional<Surgery> foundSurgery = surgeryRepository.findByName(surgery.getName());

        Assertions.assertThat(foundSurgery).isPresent();
        Assertions.assertThat(foundSurgery.get().getName()).isEqualTo(surgery.getName());
    }

    @Test
    @DisplayName("Test findByCity")
    public void testFindByCity() {
        addressRepository.save(address);
        surgeryRepository.saveAndFlush(surgery);

        List<Surgery> foundSurgeries = surgeryRepository.findByCity(address.getCity(), address.getState());

        Assertions.assertThat(foundSurgeries).isNotEmpty();
        Assertions.assertThat(foundSurgeries).contains(surgery);
    }
}
