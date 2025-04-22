package edu.miu.cs.cs489.surgeries.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer appointmentId;

    @Column(nullable = false)
    @NotNull(message = "Appointment Date is required")
    private LocalDate appointmentDate;

    @Column(nullable = false)
    @NotNull(message = "Appointment Time is required")
    private LocalTime appointmentTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="surgeryId", nullable = false)
    @NotNull(message = "Surgery is required")
    private Surgery surgery;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="dentistId", nullable = false)
    @NotNull(message = "Dentist is required")
    private Dentist dentist;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="patientId", nullable = false)
    @NotNull(message = "Patient is required")
    private Patient patient;

    public Appointment() {}

    public Appointment(LocalDate appointmentDate, LocalTime appointmentTime, Surgery surgery, Dentist dentist, Patient patient) {
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.surgery = surgery;
        this.dentist = dentist;
        this.patient = patient;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Surgery getSurgery() {
        return surgery;
    }

    public void setSurgery(Surgery surgery) {
        this.surgery = surgery;
    }

    public Dentist getDentist() {
        return dentist;
    }

    public void setDentist(Dentist dentist) {
        this.dentist = dentist;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                ", surgery=" + surgery +
                ", dentist=" + dentist +
                ", patient=" + patient +
                '}';
    }
}
