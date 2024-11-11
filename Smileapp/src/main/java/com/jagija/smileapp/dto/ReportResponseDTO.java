package com.jagija.smileapp.dto;

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
    private String fullName;               // Nombres y apellidos
    private String gender;                 // Sexo
    private LocalDate birthday;            // Fecha de Nacimiento
    private Integer age;                   // Edad
    private String birthPlace;             // Lugar de Nacimiento
    private String race;                   // Raza
    private String originPlace;            // Lugar de Procedencia
    private String dni;                    // DNI
    private String address;                // Domicilio
    private String phone;                  // Teléfono
    private String email;                  // Correo Electrónico
    private String residenceTime;          // Tiempo de residencia en Trujillo
    private String civilState;             // Estado civil
    private String studyGrade;             // Grado de instrucción
    private String profession;             // Profesión
    private String occupation;             // Ocupación
    private String workCenter;             // Centro de estudio o trabajo
    private String workDir;                // Dirección del centro de estudio o trabajo
    private String religion;               // Religión
    private String homeType;               // Vivienda
    private String bloodType;
    private String rh;
    private String home;

    // Datos de emergencia
    private String emergencyContactName;   // Nombre del contacto de emergencia
    private String emergencyContactParent; // Parentesco
    private String emergencyContactDir;    // Domicilio de emergencia
    private String emergencyContactPhone;  // Teléfono de emergencia

    // Información adicional
    private String attendingPhysician;     // Médico tratante
    private String responsiblePerson;      // Nombre del Acompañante o responsable

    // Motivo de consulta
    private String consultationReason;     // Motivo de consulta

    // ID de la historia clínica
    private Integer historyClinicId;
}
