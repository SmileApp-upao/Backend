package com.jagija.smileapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "patient")
public class Patient {

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

    @OneToOne
    @JoinColumn(name = "emergency_id", referencedColumnName = "id"
                ,foreignKey = @ForeignKey(name = "FK_patient_emergency"))
    private Emergency emergency;

    @OneToOne
    @JoinColumn(name = "historyclinic_id", referencedColumnName = "id"
                ,foreignKey = @ForeignKey(name = "FK_patient_historyclinic"))
    private HistoryClinic historyClinic;
}
