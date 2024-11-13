package com.jagija.smileapp.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseDTO {
    private String name;
    private String lastname;
    private String fullName;
    private String gender;
    private LocalDate birthday;
    private Integer age;
    private String birthPlace;
    private String originPlace;
    private String dni;
    private String phone;
    private String email;
    private String residentime;
    private String civilState;
    private String studyGrade;
    private String profession;
    private String occupation;
    private String workCenter;
    private String workDir;
    private String religion;
    private String dir;
    private String bloodType;
    private String rh;
    private String home;
    private String raze;

    // Datos de emergencia
    private String emergencyContactName;
    private String emergencyContactParent;
    private String emergencyContactDir;
    private String emergencyContactPhone;

    // Información adicional
    private String attendingPhysician;
    private String responsiblePerson;

    // Motivo de consulta
    private String consultationReason;

    //Dentista encargado
    private String dentistFullName;

    // ID de la historia clínica
    private Integer historyClinicId;
}
