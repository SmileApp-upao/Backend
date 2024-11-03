package com.jagija.smileapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteRequestDTO {

    @NotNull(message = "El ID del paciente no puede estar vacío")
    private Integer patientId;

    @NotNull(message = "El ID del dentista no puede estar vacío")
    private Integer dentistId;

    @NotBlank(message = "El motivo no puede estar vacío")
    @Size(max = 255, message = "El motivo no puede tener más de 255 caracteres")
    private String reason;

    @NotNull(message = "La fecha no puede estar vacía")
    private LocalDate date;

    @NotNull(message = "La hora no puede estar vacía")
    private LocalTime hour;

    private List<MultipartFile> files;
}
