package com.jagija.smileapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DentistRequestDTO {

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

    @NotBlank(message = "La condición no puede estar vacía")
    @Size(max = 100, message = "La condición no puede tener más de 100 caracteres")
    private String condition;

    @NotNull(message = "El ciclo no puede estar vacío")
    private Integer cicle;

    @NotBlank(message = "El COP no puede estar vacío")
    @Size(max = 20, message = "El COP no puede tener más de 20 caracteres")
    private String cop;

    @NotBlank(message = "El centro de estudios no puede estar vacío")
    @Size(max = 100, message = "El centro de estudios no puede tener más de 100 caracteres")
    private String studyCenter;

    private List<Integer> publicationIds;
    private List<Integer> documentIds;

}
