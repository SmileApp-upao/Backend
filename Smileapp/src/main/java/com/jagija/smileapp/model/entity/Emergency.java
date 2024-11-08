package com.jagija.smileapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "emergencia")
public class Emergency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "parent", nullable = false)
    private String parent;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "dir", nullable = false, length = 60)
    private String dir;

    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToOne
    @JoinColumn(name = "pat_use_id_in" ,referencedColumnName = "user_id")
    private User user;

}
