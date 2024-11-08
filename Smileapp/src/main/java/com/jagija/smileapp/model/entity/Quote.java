package com.jagija.smileapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.*;
import java.util.List;

@Data
@Entity
@Table(name = "quote")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "user_id"
            , foreignKey = @ForeignKey(name = "FK_user_patient"))
    private User patient;

    @ManyToOne
    @JoinColumn(name = "dentist_id", referencedColumnName = "user_id"
            , foreignKey = @ForeignKey(name = "FK_user_dentist"))
    private User dentist;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "hour", nullable = false)
    private LocalTime hour;

    @Lob
    @Column(name = "files")
    private List<String> files;

}
