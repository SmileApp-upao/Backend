package com.jagija.smileapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

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

    private LocalDate birthday;

    //Dentista
    private String condition;
    private String studyCenter;
    private String cop;
    private Integer cicle;

}
