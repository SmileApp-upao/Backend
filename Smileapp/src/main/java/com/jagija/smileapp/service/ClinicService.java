package com.jagija.smileapp.service;

import com.jagija.smileapp.dto.ClinicRequestDTO;
import com.jagija.smileapp.dto.ClinicResponseDTO;
import com.jagija.smileapp.dto.DentistResponseDTO;
import com.jagija.smileapp.model.entity.Clinic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClinicService {
    public List<ClinicResponseDTO> getallClinics();
    public ClinicResponseDTO addClinic(ClinicRequestDTO clinic);
    public ClinicResponseDTO getClinicById(Integer id);
    public ClinicResponseDTO updateInfoClinic(ClinicRequestDTO clinic);
    public boolean hasClinic(Integer Dentistid);
    public ClinicResponseDTO getClinicByDentistId(Integer Dentistid);
}
