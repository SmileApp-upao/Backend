package com.jagija.smileapp.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private Integer id;
    private String token;
    private String name;
    private String lastname;
    private String role;
}
