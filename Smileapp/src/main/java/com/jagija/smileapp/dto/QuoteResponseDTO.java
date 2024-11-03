package com.jagija.smileapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteResponseDTO {

    private Integer id;

    private Integer patientId;

    private Integer dentistId;

    private String reason;

    private LocalDate date;

    private LocalTime hour;

    private List<String> files;

}
