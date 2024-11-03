package com.jagija.smileapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id"
               , foreignKey = @ForeignKey(name = "FK_user_patient"))
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "dentist_id", referencedColumnName = "id"
               , foreignKey = @ForeignKey(name = "FK_user_dentist"))
    private Dentist dentist;
}
