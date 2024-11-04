package com.jagija.smileapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "dateUpload", nullable = false)
    private LocalDate dateUpload;

    @Lob
    @Column(name = "doc", nullable = false)
    private byte[] doc;
}
