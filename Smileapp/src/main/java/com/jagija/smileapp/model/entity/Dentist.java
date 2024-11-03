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

    @Column(name = "condition", nullable = false)
    private String condition;

    @Column(name = "cicle", nullable = false)
    private Integer cicle;

    @Column(name = "cop", nullable = false)
    private String cop;

    @Column(name = "studyCenter", nullable = false)
    private String studyCenter;

    @OneToMany
    @JoinColumn(name = "publication_id", referencedColumnName = "id"
               ,foreignKey = @ForeignKey(name = "FK_dentist_publications"))
    private List<Publication> publications;

    @OneToMany
    @JoinColumn(name = "document_id", referencedColumnName = "id"
               , foreignKey = @ForeignKey(name = "FK_dentist_documents"))
    private List<Document> documents;
}
