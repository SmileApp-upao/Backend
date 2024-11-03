package com.jagija.smileapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyRequestDTO {

    @NotBlank(message = "El apoderado no puede estar vacío")
    @Size(max = 50, message = "El apoderado no puede tener más de 50 caracteres")
    private String parent;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String name;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 50, message = "La dirección no puede tener más de 50 caracteres")
    private String dir;

    @NotBlank(message = "El número de teléfono no puede estar vacío")
    @Size(max = 50, message = "El número de teléfono no puede tener más de 50 caracteres")
    private String phone;
}
