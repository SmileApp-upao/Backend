package com.jagija.smileapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryClinicRequestDTO {

    @NotBlank(message = "El tipo de sangre no puede estar vacío")
    @Size(max = 3, message = "El tipo de sangre no puede tener más de 3 caracteres")
    private String bloodType;

    @NotBlank(message = "El factor RH no puede estar vacío")
    @Size(max = 2, message = "El factor RH no puede tener más de 2 caracteres")
    private String rh;

    @NotBlank(message = "El estado civil no puede estar vacío")
    @Size(max = 20, message = "El estado civil no puede tener más de 20 caracteres")
    private String civilState;

    @NotBlank(message = "El lugar de nacimiento no puede estar vacío")
    @Size(max = 50, message = "El lugar de nacimiento no puede tener más de 50 caracteres")
    private String birthPlace;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 100, message = "La dirección no puede tener más de 100 caracteres")
    private String dir;

    @NotBlank(message = "El grado de estudio no puede estar vacío")
    @Size(max = 50, message = "El grado de estudio no puede tener más de 50 caracteres")
    private String studyGrade;

    @NotBlank(message = "La profesión no puede estar vacía")
    @Size(max = 50, message = "La profesión no puede tener más de 50 caracteres")
    private String profession;

    @NotBlank(message = "La ocupación no puede estar vacía")
    @Size(max = 50, message = "La ocupación no puede tener más de 50 caracteres")
    private String occupation;

    @NotBlank(message = "El centro de trabajo no puede estar vacío")
    @Size(max = 100, message = "El centro de trabajo no puede tener más de 100 caracteres")
    private String workCenter;

    @NotBlank(message = "La dirección del trabajo no puede estar vacía")
    @Size(max = 100, message = "La dirección del trabajo no puede tener más de 100 caracteres")
    private String workDir;

    @NotBlank(message = "La religión no puede estar vacía")
    @Size(max = 30, message = "La religión no puede tener más de 30 caracteres")
    private String religion;

    @NotBlank(message = "La dirección de la casa no puede estar vacía")
    @Size(max = 100, message = "La dirección de la casa no puede tener más de 100 caracteres")
    private String home;
}
