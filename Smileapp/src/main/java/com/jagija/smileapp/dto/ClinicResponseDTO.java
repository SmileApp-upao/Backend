package com.jagija.smileapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClinicResponseDTO {

    private Integer id;
    private LocalTime openHour;
    private LocalTime closeHour;
    private String address;
    private String desc;
    private String telf;
    private String email;
    private List<DentistResponseDTO> dentists;
}
