package com.jagija.smileapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyResponseDTO {

    private Integer id;
    private String parent;
    private String name;
    private String dir;
    private String phone;
}
