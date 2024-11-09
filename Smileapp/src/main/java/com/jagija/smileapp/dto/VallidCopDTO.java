package com.jagija.smileapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VallidCopDTO {
    @NotBlank(message = "Ingrese el codigo")
    @Size(max = 5 ,message = "El codigo no puede tener mas de 5 digitos")
    String cop;
}
