package com.jagija.smileapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.DayOfWeek;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClinicRequestDTO {

    @NotNull(message = "La hora de apertura no puede estar vacía")
    private LocalTime openHour;
    @NotNull(message = "El nombre no puede estar vacio")
    private String name;
    @NotNull(message = "La hora de cierre no puede estar vacía")
    private LocalTime closeHour;

    @NotNull(message = "Los dias en que se abre no pueden estar vacios")
    private String openDays;
    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres")
    private String address;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
    private String desc;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Size(max = 15, message = "El teléfono no puede tener más de 15 caracteres")
    private String telf;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    @Size(max = 100, message = "El email no puede tener más de 100 caracteres")
    private String email;

    private String latitude;

    private String longitude;

    private List<Integer> dentistIds;

}
