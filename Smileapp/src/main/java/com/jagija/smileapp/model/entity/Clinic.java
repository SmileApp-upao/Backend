package com.jagija.smileapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "clinic")
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany
    @JoinColumn(name = "dentist_id", referencedColumnName = "id")
    private List<Dentist> dentistas;

    @Column(name = "openHour", nullable = false)
    private LocalTime openHour;

    @Column(name = "closeHour", nullable = false)
    private LocalTime closeHour;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description", nullable = false)
    private String desc;

    @Column(name = "telf", nullable = false)
    private String telf;

    @Column(name = "email", nullable = false)
    private String email;
}
