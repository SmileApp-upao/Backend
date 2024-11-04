package com.jagija.smileapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestDTO {

    @NotBlank(message = "El número de teléfono no puede estar vacío")
    @Size(max = 15, message = "El número de teléfono no puede tener más de 15 caracteres")
    private String phone;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String name;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 50, message = "El apellido no puede tener más de 50 caracteres")
    private String lastname;

    @NotBlank(message = "El género no puede estar vacío")
    @Size(max = 10, message = "El género no puede tener más de 10 caracteres")
    private String gender;

    @NotNull(message = "La fecha de nacimiento no puede estar vacía")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate birthday;

    @NotNull(message = "El ID de emergencia no puede estar vacío")
    private Integer emergency;

    @NotNull(message = "El ID del historial clínico no puede estar vacío")
    private Integer historyClinic;

}
