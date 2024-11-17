package com.jagija.smileapp.dto;

import com.jagija.smileapp.model.entity.Role;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class UserProfileDTO {
    private Integer id;
    private Integer idDentista;
    private Integer idPaciente;
    private String email;
    private Role role;

    private String name;

    private String lastname;

    private String gender;

    private LocalDate birthday;
    private String dni;
    private String image;

    private String phone;
    //Dentista
    private Integer cicle;
    private String condition;
    private String studyCenter;
    private String description;
    //Paciente-info-emergencia
    private String parent;
    private String pname;
    private String pdir;
    private String pphone;


}
