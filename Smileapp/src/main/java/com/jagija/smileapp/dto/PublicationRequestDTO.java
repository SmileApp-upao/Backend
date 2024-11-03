package com.jagija.smileapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicationRequestDTO {

    @NotNull(message = "El archivo de la imagen no puede estar vacia")
    private MultipartFile image;
}
