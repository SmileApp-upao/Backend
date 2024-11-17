package com.jagija.smileapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRequestDTO {
    @NotBlank(message = "El nombre del documento no puede estar vacío")
    @Size(max = 100, message = "El nombre del documento no puede tener más de 100 caracteres")
    private String name;

    @NotBlank(message = "La descripcion no puede estar vacía")
    @Size(max = 250, message = "La descripcion no puede tener más de 100 caracteres")
    private String description;

    private String docpath;
}
