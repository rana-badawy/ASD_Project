package edu.miu.cs.cs489.surgeries.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "surgeries")
public class Surgery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer surgeryId;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Surgery Number is required")
    private String surgeryNo;

    @Column(nullable = false)
    @NotBlank(message = "Surgery Name is required")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Phone is required")
    private String phone;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "addressId", nullable = false)
    @NotNull(message = "Address is required")
    private Address address;

    @OneToMany(mappedBy = "surgery", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Appointment> appointments;


    public Surgery() {}

    public Surgery(String surgeryNo, String name, String phone, Address address) {
        this.surgeryNo = surgeryNo;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public void setSurgeryId(Integer surgeryId) {
        this.surgeryId = surgeryId;
    }

    public Integer getSurgeryId() {
        return surgeryId;
    }

    public String getSurgeryNo() {
        return surgeryNo;
    }

    public void setSurgeryNo(String surgeryNo) {
        this.surgeryNo = surgeryNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
