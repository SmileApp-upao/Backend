package com.jagija.smileapp.dto;

import com.jagija.smileapp.model.entity.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserProfileDTO {
    private Integer id;
    private String email;
    private Role role;

    private String name;

    private String lastname;

    private String gender;

    private LocalDate birthday;

    private String phone;
    //Dentista
    private Integer cicle;
    private String condition;
    private String studyCenter;
    //Paciente-info-emergencia
    private String parent;
    private String Pname;
    private String Pdir;
    private String Pphone;


}
