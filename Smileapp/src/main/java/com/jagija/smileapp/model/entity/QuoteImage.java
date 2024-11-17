package com.jagija.smileapp.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "quote_images")
public class QuoteImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "file_path", nullable = true)
    private String filePath;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quote_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_quote_image_quote"))
    private Quote quote;


}
