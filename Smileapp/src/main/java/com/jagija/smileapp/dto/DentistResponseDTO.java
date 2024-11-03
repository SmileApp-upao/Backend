package com.jagija.smileapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DentistResponseDTO {

    private Integer id;
    private String phone;
    private String name;
    private String lastname;
    private String gender;
    private LocalDate birthday;
    private String condition;
    private Integer cicle;
    private String cop;
    private String studyCenter;
    private List<PublicationResponseDTO> publications;
    private List<DocumentResponseDTO> documents;
}
