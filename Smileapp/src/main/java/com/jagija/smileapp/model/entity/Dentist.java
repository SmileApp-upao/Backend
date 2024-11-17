package com.jagija.smileapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "dentist")
public class Dentist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "dni",nullable = false)
    private String dni;

    @Column(name = "condition", nullable = false)
    private String condition;

    @Column(name = "cicle", nullable = true)
    private Integer cicle;

    @Column(name = "cop", nullable = true)
    private String cop;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "studyCenter", nullable = false)
    private String studyCenter;

    @Column(name = "img_prof_dent", nullable = true)
    private String image;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
