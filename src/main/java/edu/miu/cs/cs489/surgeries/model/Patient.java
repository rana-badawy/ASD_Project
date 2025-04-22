package edu.miu.cs.cs489.surgeries.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer patientId;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Patient Number is required")
    private String patientNo;

    @Column(nullable = false)
    @NotBlank(message = "First Name is required")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Last Name is required")
    private String lastName;

    @Column(nullable = false)
    @NotBlank(message = "Email is required")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Phone is required")
    private String phone;

    @Column(nullable = false)
    @NotNull(message = "Date of Birth is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate dob;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "addressId", nullable = false)
    @NotNull(message = "Address is required")
    private Address address;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Appointment> appointments;

    public Patient() {}

    public Patient(String patientNo, String firstName, String lastName, String email, String phone, LocalDate dob, Address address) {
        this.patientNo = patientNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.address = address;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public String getPatientNo() {
        return patientNo;
    }

    public void setPatientNo(String patientNo) {
        this.patientNo = patientNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
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
