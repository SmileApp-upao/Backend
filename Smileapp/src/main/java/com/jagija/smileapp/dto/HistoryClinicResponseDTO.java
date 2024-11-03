package com.jagija.smileapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryClinicResponseDTO {
    private Integer id;
    private String bloodType;
    private String rh;
    private String civilState;
    private String birthPlace;
    private String dir;
    private String studyGrade;
    private String profession;
    private String occupation;
    private String workCenter;
    private String workDir;
    private String religion;
    private String home;

}
