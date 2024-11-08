package com.jagija.smileapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "historyClinic")
public class HistoryClinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bloodType", nullable = false)
    private String bloodType;

    @Column(name = "rh", nullable = false)
    private String rh;

    @Column(name = "civilState", nullable = false)
    private String civilState;

    @Column(name = "birthPlace", nullable = false)
    private String birthPlace;

    @Column(name = "dir", nullable = false)
    private String dir;

    @Column(name = "studyGrade", nullable = false)
    private String studyGrade;

    @Column(name = "profession", nullable = false)
    private String profession;

    @Column(name = "occupation", nullable = false)
    private String occupation;

    @Column(name = "workCenter", nullable = false)
    private String workCenter;

    @Column(name = "workDir", nullable = false)
    private String workDir;

    @Column(name = "religion", nullable = false)
    private String religion;

    @Column(name = "home", nullable = false)
    private String home;

    @OneToOne
    @JoinColumn(name = "pat_use_id_in" ,referencedColumnName = "user_id")
    private User user;
}
