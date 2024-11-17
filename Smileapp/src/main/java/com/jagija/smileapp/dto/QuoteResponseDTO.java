package com.jagija.smileapp.dto;

import com.jagija.smileapp.model.entity.QuoteImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuoteResponseDTO {

    private Integer id;
    private Integer patientId;
    private Integer dentistId;

    private LocalDate date;
    private LocalTime hour;

    //Datos Clinica
    private Integer clinicId;
    private String clinicname;
    private String clinicdescription;
    private String clinicdirection;
    //Vista dentista
    private String patientName;
    private List<String> filePaths;
    private String reason;
    //Vista paciente
    private String dentistName;
    private String dentistLastName;

}
