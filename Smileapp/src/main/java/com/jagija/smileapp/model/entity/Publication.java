package com.jagija.smileapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "publication")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "imagen", nullable = false)
    private String image;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "dentist_id", referencedColumnName = "id"
                , foreignKey = @ForeignKey(name = "FK_publication_dentist"))
    private Dentist dentist;
}
