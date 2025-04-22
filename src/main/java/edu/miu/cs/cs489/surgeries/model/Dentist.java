package edu.miu.cs.cs489.surgeries.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "dentists")
public class Dentist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dentistId;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Dentist Number is required")
    private String dentistNo;

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
    @NotBlank(message = "Specialization is required")
    private String specialization;

    @OneToMany(mappedBy = "dentist", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Appointment> appointments;

    public Dentist() {}

    public Dentist(String dentistNo, String firstName, String lastName, String email, String phone, String specialization) {
        this.dentistNo = dentistNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.specialization = specialization;
    }

    public void setDentistId(Integer dentistId) {
        this.dentistId = dentistId;
    }

    public Integer getDentistId() {
        return dentistId;
    }

    public String getDentistNo() {
        return dentistNo;
    }

    public void setDentistNo(String dentistNo) {
        this.dentistNo = dentistNo;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
