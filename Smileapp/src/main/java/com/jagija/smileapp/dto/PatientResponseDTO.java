package com.jagija.smileapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDTO {

    private Integer id;
    private String phone;
    private String name;
    private String lastname;
    private String gender;
    private LocalDate birthday;
    private EmergencyResponseDTO emergency;
    private HistoryClinicResponseDTO historyClinic;

}
