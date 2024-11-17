package com.jagija.smileapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "dateUpload", nullable = false)
    private LocalDate dateUpload;


    @Column(name = "doc", nullable = false)
    private String docpath;

    @ManyToOne
    @JoinColumn(name = "den_id_in", referencedColumnName = "id")
    private Dentist dentist;
}
