package com.jagija.smileapp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class UserRegistrationDTO {
    @Email(message = "Debe ser una dirección de correo electrónico con formato correcto")
    private String email;

    @NotBlank(message = "La contraseña no puede ir vacia")
    @Size(min = 6 ,message = "La contraseña debe tener minimo 6 caracteres")
    private String password;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String name;

    @NotBlank(message = "El numero no puede estar vacio")
    private String phone;

    @NotBlank(message = "El apellido es obligatorio")
    private String lastname;

    @NotBlank(message = "Ingrese su genero")
    private String gender;

    @NotBlank(message = "Ingrese su dni")
    @Size(max = 8, message = "El dni no puede tener mas de 8 digitos")
    private String dni;

    //@NotNull(message = "El archivo de la imagen no puede estar vacia")
    private MultipartFile image;

    private LocalDate birthday;

    //Dentista
    private String condition; //Profesional || Estudiante
    private String studyCenter;
    @Size(max = 5, message = "El cop no puede tener mas de 5 digitos")
    private String cop;

    @Min(value = 5, message = "Debes cursar en 5to ciclo como minimo")
    private Integer cicle;

}
