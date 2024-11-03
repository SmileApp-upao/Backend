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

    @NotNull(message = "La fecha de carga no puede estar vacía")
    private LocalDate dateUpload;

    @NotNull(message = "El archivo del documento no puede estar vacío")
    private byte[] doc;
}
